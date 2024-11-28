package cn.itcast.advance.c1;

import java.nio.ByteBuffer;

import static cn.itcast.advance.c1.ByteBufferUtil.debugAll;


/**
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/11/28 9:48
 * @Version V1.0
 */
public class TestByteBufferReadWrite {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        buffer.put((byte) 0x61);
        debugAll(buffer);
        buffer.put(new byte[]{0x62, 0x63, 0x64});
        debugAll(buffer);

        //System.out.println(buffer.get());
        buffer.flip();
        System.out.println("读取到的字节为:"+buffer.get());
        debugAll(buffer);
        buffer.compact();
        debugAll(buffer);

        buffer.put(new byte[]{0x65, 0x6f});
        debugAll(buffer);
    }
}
