package cn.itcast.netty.test04;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * P76-netty入门-inbound-handler 入栈处理器
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/12/5 16:51
 * @Version V1.0
 */
@Slf4j
public class TestPipeline {

    public static void main(String[] args) {
        new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // 1. 通过Channel拿到pipeline
                        ChannelPipeline pipeline = ch.pipeline();
                        // 2. 添加处理器 head->h1->h2->h3->h4->h5->h6->tail
                        pipeline.addLast("h1",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.debug("1");
                                ByteBuf buf = (ByteBuf) msg;
                                String name = buf.toString(Charset.defaultCharset());
                                super.channelRead(ctx, name);
                            }
                        });
                        pipeline.addLast("h2",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object name) throws Exception {
                                log.debug("2");
                                Student student = new Student((String) name);
                                // 2选一
                                super.channelRead(ctx, student); //将数据传给下一个handler，不调用相当于断开了
                                // ctx.fireChannelRead(student);
                            }
                        });
                        pipeline.addLast("h3",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.debug("3,结果：{},class:{}", msg,msg.getClass());
                                super.channelRead(ctx, msg);
                                ch.writeAndFlush(ctx.alloc().buffer().writeBytes("server".getBytes()));
                            }
                        });

                        //出站
                        pipeline.addLast("h4", new ChannelOutboundHandlerAdapter() {
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.debug("4");
                                super.write(ctx, msg, promise);
                            }
                        });
                        pipeline.addLast("h5", new ChannelOutboundHandlerAdapter() {
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.debug("5");
                                super.write(ctx, msg, promise);
                            }
                        });
                        pipeline.addLast("h6", new ChannelOutboundHandlerAdapter() {
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.debug("6");
                                super.write(ctx, msg, promise);
                            }
                        });
                    }
                })
                .bind(8080);
    }

    @Data
    static class Student {
        private String name;

        public Student(String name) {
            this.name = name;
        }
    }
}
