package cn.itcast.nio.c1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * 客户端
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/11/28 16:40
 * @Version V1.0
 */
public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));
        System.out.println("waiting...");

    }
}
