package cn.itcast.netty;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static cn.itcast.advance.c1.ByteBufferUtil.debugRead;

/**
 * nio非阻塞模式
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/11/28 16:29
 * @Version V1.0
 */
@Slf4j
public class Server1 {
    public static void main(String[] args) throws IOException {
        //使用 nio 来理解阻塞模式
        // 0. ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);

        // 1. 创建了服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false); //模式切换为非阻塞模式

        // 2. 绑定监听端口
        ssc.bind(new InetSocketAddress(8080));

        // 3. 连接集合
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            // 4. accept 建立与客户端连接, SocketChannel 用来与客户端之间通信
            log.debug("连接中...");
            SocketChannel sc = ssc.accept(); //非阻塞方法，如果没有连接建立，线程还是会继续执行，但是sc是null
            if (sc != null) {
                log.debug("connected... {}", sc);
                sc.configureBlocking(false); //切换为非阻塞模式
                channels.add(sc);
            }


            for (SocketChannel channel : channels) {
                // 5. 接收客户端发送的数据
                log.debug("before read...{}", channel);
                channel.read(buffer); //此时read是非阻塞方法,线程还是会继续执行,如果没有读到数据，read会返回0
                buffer.flip();
                debugRead(buffer);
                buffer.clear();
                log.debug("after read...{}", channel);
            }
        }
    }
}
