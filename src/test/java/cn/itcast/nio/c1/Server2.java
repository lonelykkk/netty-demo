package cn.itcast.nio.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static cn.itcast.advance.c1.ByteBufferUtil.debugRead;

/**
 * Selector
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/11/28 16:29
 * @Version V1.0
 */
@Slf4j
public class Server2 {
    /**
     * * connect - 客户端连接成功时触发
     * * accept - 服务器端成功接受连接时触发
     * * read - 数据可读入时触发，有因为接收能力弱，数据暂不能读入的情况
     * * write - 数据可写出时触发，有因为发送能力弱，数据暂不能写出的情况
     */
    public static void main(String[] args) throws IOException {
        // 1. 创建Selector，管理多个 channel
        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        // 2. 建立 selector 和 channel 的联系
        //SelectionKey 就是事件发生后，通过他可以知道事件和那个channel的事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
        //key只关注accept事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
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
                log.debug("register key:{}",key);

                //如果事件不处理，会重新将这个事件交给selector，从而导致死循环
                /*ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                SocketChannel sc = channel.accept();
                log.debug("{}", sc);*/
                key.cancel();
            }
        }
    }
}
