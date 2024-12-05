package cn.itcast.netty.test04;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * P74-netty入门-netty-promise
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/12/5 16:32
 * @Version V1.0
 */
@Slf4j
public class TestNettyPromise {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1. 准备 EventLoop对象
        EventLoop eventLoop = new NioEventLoopGroup().next();
        // 2. 可以主动创建 promise, 结果容器
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);

        new Thread(() -> {
            // 3. 任意一个线程执行计算，计算完毕后向promise填充结果
            log.debug("开始计算...");

            try {
                int i = 1 / 0;
                TimeUnit.SECONDS.sleep(1);
                promise.setSuccess(80);
            } catch (Exception e) {
                e.printStackTrace();
                promise.setFailure(e);
            }

        }).start();

        // 4.接收结果的线程
        log.debug("等待结果");
        log.debug("结果是：{}",promise.get());
    }
}
