package cn.itcast.advance.c1;

import java.nio.ByteBuffer;

/**
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/11/28 10:08
 * @Version V1.0
 */
public class TestByteBufferAllocate {
    public static void main(String[] args) {
        System.out.println(ByteBuffer.allocate(16).getClass());
        System.out.println(ByteBuffer.allocateDirect(16).getClass());
        /**
         * class java.nio.HeapByteBuffer - java 堆内存，读写效率较低,受到垃圾回收的影响
         * class java.nio.DirectByteBuffer - 直接内存，读写效率高 （少一次拷贝）,不会受到gc的影响，分配内存的效率低
         */
    }
}
