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
 * nio阻塞模式
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/11/28 16:29
 * @Version V1.0
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        //使用 nio 来理解阻塞模式
        // 0. ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);

        // 1. 创建了服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();

        // 2. 绑定监听端口
        ssc.bind(new InetSocketAddress(8080));

        // 3. 连接集合
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            // 4. accept 建立与客户端连接, SocketChannel 用来与客户端之间通信
            SocketChannel sc = ssc.accept();
            channels.add(sc);

            for (SocketChannel channel : channels) {
                // 5. 接收客户端发送的数据
                log.debug("连接中...");
                channel.read(buffer);
                log.debug("connected... {}", sc);
                buffer.flip();
                debugRead(buffer);
                buffer.clear();
                log.debug("after read...{}", channel);
            }
        }
    }
}
