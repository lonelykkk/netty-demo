package cn.itcast.netty.c1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/12/2 8:54
 * @Version V1.0
 */
public class HelloServer {
    public static void main(String[] args) {
        // 1.启动器，负责组装 netty 组件，启动服务器
        new ServerBootstrap()
                // 2. BossEventLoop, WorkerEventLoop(selector,thread), group组
                .group(new NioEventLoopGroup())
                // 3. 选择服务器的 ServerSocketChannel 实现
                .channel(NioServerSocketChannel.class) //OIO BIO
                // 4. boss负责处理连接, worker(child)负责处理读写，决定了 worker(child) 能执行哪些操作(handler)
                .childHandler(
                        // 5. channel 代表和客户端进行数据读写的通道 Initializer 初始化
                    new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // 6. 添加具体 handler
                        ch.pipeline().addLast(new StringDecoder()); // 将 ByteBuf 转换为字符串
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){ // 自定义 handler
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                // 打印上一步转换好的字符串
                                System.out.println(msg);
                            }
                        });
                    }
                })
                .bind(8087);
    }
}
