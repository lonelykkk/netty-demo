package cn.itcast.nio.c1;

import java.nio.ByteBuffer;

/**
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/11/28 10:21
 * @Version V1.0
 */
public class TestByteBufferRead {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a', 'b', 'c', 'd'});
        buffer.flip();

        // rewind 从头开始读
        /*buffer.get(new byte[4]);
        debugAll(buffer);
        buffer.rewind();
        System.out.println((char) buffer.get());*/

        // mark & reset
        // mark 做一个标记，记录 position 位置，reset 是将 position 重置到 mark 的位置
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        buffer.mark(); //做标记，索引2的位置
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        buffer.reset(); //将position重置到上一次mark标记的位置，即重置到2
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
    }

}
