package cn.itcast.netty.test04;


import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/12/5 15:55
 * @Version V1.0
 */
@Slf4j
public class TestNettyFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        EventLoop eventLoop = group.next();
        Future<Integer> future = eventLoop.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("执行计算");
                TimeUnit.SECONDS.sleep(1);
                return 70;
            }
        });
       /* //同步
        log.debug("等待结果");
        log.debug("结果是：{}", future.get());*/

        // 异步
        future.addListener(new GenericFutureListener<Future<? super Integer>>() {
            @Override
            public void operationComplete(Future<? super Integer> future) throws Exception {
                log.debug("接收结果：{}", future.getNow());
            }
        });
        log.debug("main");
    }
}
