package cn.itcast.netty.test02;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/** P59-netty入门-eventLoop-普通-定时任务
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/12/3 8:44
 * @Version V1.0
 */
@Slf4j
public class TestEventLoop {
    public static void main(String[] args) {
        // 1. 创建事件循环组
        EventLoopGroup group = new NioEventLoopGroup(2); //io 事件，普通任务，定时任务
        // EventLoopGroup group = new DefaultEventLoopGroup(); // 普通任务，定时任务，不能处理io事件
        // 2. 获取下一个事件循环对象
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());

        // 3. 执行普通任务
        group.next().submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("ok");
        });

        // 4. 执行定时任务
        group.next().scheduleAtFixedRate(() -> {
            System.out.println("ok~");
        },1,1,TimeUnit.SECONDS);

        log.debug("main");
    }
}
