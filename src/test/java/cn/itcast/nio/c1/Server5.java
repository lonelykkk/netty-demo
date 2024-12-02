package cn.itcast.nio.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

import static cn.itcast.advance.c1.ByteBufferUtil.debugAll;
import static cn.itcast.advance.c1.ByteBufferUtil.debugRead;

/**
 * Selector 消息边界问题
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/11/28 16:29
 * @Version V1.0
 */
@Slf4j
public class Server5 {
    private static void split(ByteBuffer source) {
        source.flip(); //切换到读模式
        int oldLimit = source.limit();
        for (int i = 0; i < source.limit(); i++) {
            //找到一条完整消息
            if (source.get(i) == '\n') {
                //把这条完整消息存入新的ByteBuffer
                int len = i + 1 - source.position(); //i + 1 - source.position()完整消息的长度
                ByteBuffer target = ByteBuffer.allocate(len);
                // 从source 读，向 target 写
                for (int j = 0; j < len; j++) {
                    target.put(source.get());
                }
                debugAll(target);
            }

        }
        source.compact();
    }
    public static void main(String[] args) throws IOException {
        // 1. 创建Selector，管理多个 channel
        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        // 2. 建立 selector 和 channel 的联系
        //SelectionKey 就是事件发生后，通过他可以知道事件和那个channel的事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
        //key只关注accept事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT); //设置sscKey只关注accept事件
        log.debug("register key:{}",sscKey);

        ssc.bind(new InetSocketAddress(8080));
        while (true) {
            // 3. select方法,没有事件发生，线程阻塞，有事件，线程才会恢复运行
            // select方法在时间未处理时，不会阻塞,事件发生后要么处理要么取消，不能置之不理
            selector.select();

            // 4. 处理事件，selectedKeys内部包含了所有发生的事件
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                //处理key的时候一定要从selectedKeys 删除
                iter.remove();

                log.debug("register key:{}",key);

                //如果事件不处理，会重新将这个事件交给selector，从而导致死循环

                // 5. 区分事件类型
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    ByteBuffer buffer = ByteBuffer.allocate(16);
                    // 将一个 ByteBuffer 作为附件关联到 SelectionKey 上
                    SelectionKey scKey = sc.register(selector, 0, buffer);
                    scKey.interestOps(SelectionKey.OP_READ); //设置只关注读事件
                    log.debug("{}", sc);
                    log.debug("scKey:{}",scKey);
                } else if (key.isReadable()) {  // 如果是read
                    try {
                        SocketChannel channel = (SocketChannel) key.channel(); //拿到触发时间的channel
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        int read = channel.read(buffer);// 如果是正常断开，read返回值是-1
                        if (read == -1) {
                            key.cancel();
                        } else {
                            split(buffer);
                            if (buffer.position() == buffer.limit()) {
                                //当容量不够是，创建一个新的buffer，为旧的buffer的两倍,并将旧的buffer拷贝到新的buffer
                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                                buffer.flip();
                                newBuffer.put(buffer);
                                key.attach(newBuffer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        key.cancel(); //因为客户端断开了，所以要取消处理该事件
                    }


                }

               // key.cancel();
            }
        }
    }
}
