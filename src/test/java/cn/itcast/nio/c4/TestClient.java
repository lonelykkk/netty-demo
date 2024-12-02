package cn.itcast.nio.c4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * 客户端-多线程优化
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/11/28 16:40
 * @Version V1.0
 */
public class TestClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));
        sc.write(Charset.defaultCharset().encode("0123456789abcdef"));
        System.in.read();

    }
}
