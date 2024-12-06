package cn.itcast.netty.test05;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

import static cn.itcast.netty.test04.TestByteBuf.log;

/**
 * P88-netty入门-byteBuf-零拷贝-composite
 */
public class TestCompositeByteBuf {
    public static void main(String[] args) {
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.buffer();
        buf1.writeBytes(new byte[]{1, 2, 3, 4, 5});

        ByteBuf buf2 = ByteBufAllocator.DEFAULT.buffer();
        buf2.writeBytes(new byte[]{6, 7, 8, 9, 10});

        /*ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        //这样会进行两次数据复制，如果数据量大的情况下很不友好，不推荐
        buffer.writeBytes(buf1).writeBytes(buf2);
        log(buffer);*/

        CompositeByteBuf buffer = ByteBufAllocator.DEFAULT.compositeBuffer();
        buffer.addComponents(true, buf1, buf2);
        log(buffer);

    }
}
