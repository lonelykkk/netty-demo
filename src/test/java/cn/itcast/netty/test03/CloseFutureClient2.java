package cn.itcast.netty.test03;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 *  P68-netty入门-ChannelFuture-处理关闭
 *  P69-netty入门-ChannelFuture-处理关闭
 */
@Slf4j
public class CloseFutureClient2 {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        // 2. 带有 Future，Promise 的类型都是和 异步方法配套使用，用来正确的处理结果
        ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                // 1.连接到服务器
                // 异步非阻塞,main 发起了调用，真正执行connect 的是 nio 线程
                .connect(new InetSocketAddress("localhost", 8080));

        Channel channel = channelFuture.sync().channel();
        log.debug("{}", channel);
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if ("q".equals(line)) {
                    channel.close();  //close异步操作,不是阻塞的
                    break;
                }
                channel.writeAndFlush(line);
            }
        },"input").start();

        // 获取 CloseFuture 对象， 1) 同步处理关闭， 2) 异步处理关闭
        ChannelFuture closeFuture = channel.closeFuture();
        // 1.
        /*System.out.println("等待关闭...");
        closeFuture.sync();
        log.debug("处理关闭后的操作");*/
        // 2.
        closeFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                log.debug("处理关闭后的操作");
                group.shutdownGracefully();
            }
        });
    }
}
