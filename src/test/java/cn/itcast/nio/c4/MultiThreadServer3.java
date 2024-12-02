package cn.itcast.nio.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static cn.itcast.nio.c2.ByteBufferUtil.debugAll;

/**
 * 多个worker，轮询分配worker
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/11/29 15:51
 * @Version V1.0
 */
@Slf4j
public class MultiThreadServer3 {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector boss = Selector.open();
        SelectionKey bossKey = ssc.register(boss, 0, null);
        bossKey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));
        // 1. 创建固定数量的worker 并初始化
        Worker[] workers = new Worker[2];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Worker("worker-" + i);
        }

        AtomicInteger index = new AtomicInteger();
        while (true) {
            boss.select();
            Iterator<SelectionKey> iter = boss.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    log.debug("connect...{}",sc.getRemoteAddress());
                    // 2. 关联selector
                    log.debug("before register...{}",sc.getRemoteAddress());

                    //round robin 轮询算法分配work
                    workers[index.getAndIncrement() % workers.length].register(sc); // 初始化selector，启动worker-0
                    log.debug("after register...{}",sc.getRemoteAddress());
                }
            }
        }
    }

    static class Worker implements Runnable{
        private Thread thread;
        private Selector selector;
        private String name;
        private volatile boolean start = false;
        private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();

        public Worker(String name) {
            this.name = name;
        }

        /**
         * 初始化线程和selector
         */
        public void register(SocketChannel sc) throws IOException {
            if (!start) {
                selector = Selector.open();
                thread = new Thread(this,name);
                thread.start();
                start = true;
            }
            selector.wakeup(); //唤醒selector
            sc.register(this.selector, SelectionKey.OP_READ,null);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    selector.select(); //worker-0 阻塞 wakeup也可以实现
                    Runnable task = queue.poll();
                    if (task != null) {
                        task.run(); //执行了 sc.register(this.selector, SelectionKey.OP_READ);
                    }
                    Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel socketChannel = (SocketChannel) key.channel();
                            log.debug("read...{}",socketChannel.getRemoteAddress());
                            socketChannel.read(buffer);
                            buffer.flip();
                            debugAll(buffer);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }
    }
}
