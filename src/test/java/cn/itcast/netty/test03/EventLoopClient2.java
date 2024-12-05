package cn.itcast.netty.test03;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * P65-netty入门-ChannelFuture-连接问题
 */
@Slf4j
public class EventLoopClient2 {
    public static void main(String[] args) throws InterruptedException {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                // 1.连接到服务器
                // 异步非阻塞,main 发起了调用，真正执行connect 的是 nio 线程
                .connect(new InetSocketAddress("localhost", 8080));
        // 可能要1s后才能完成建立连接，但由于主线程是非阻塞的，所以还连接上就执行完了

        channelFuture.sync();
        // 无阻塞的向下执行
        Channel channel = channelFuture.channel();
        channel.writeAndFlush("hello world");
    }
}
