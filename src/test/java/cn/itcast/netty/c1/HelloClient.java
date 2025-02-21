package cn.itcast.netty.c1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/12/2 9:17
 * @Version V1.0
 */
public class HelloClient {

    public static void main(String[] args) throws InterruptedException {
        // 1. 创建启动类
        new Bootstrap()
                // 2. 添加 EventLoop
                .group(new NioEventLoopGroup())
                // 3. 选择客户端 channel 实现
                .channel(NioSocketChannel.class)
                // 4. 添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    //在连接建立后被调用
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder()); //将客户端的字符串转换为编码发送到服务器端
                    }
                })
                // 5. 连接服务器
                .connect(new InetSocketAddress("localhost", 8087))
                .sync()
                .channel()
                // 6. 向服务器发送数据
                .writeAndFlush("hello world");
    }
}
