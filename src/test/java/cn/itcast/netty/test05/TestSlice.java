package cn.itcast.netty.test05;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static cn.itcast.netty.test04.TestByteBuf.log;

/**
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/12/6 16:27
 * @Version V1.0
 */
public class TestSlice {
    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
        buf.writeBytes(new byte[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'});
        log(buf);

        // 在切片过程中没有发生数据复制
        ByteBuf f1 = buf.slice(0, 5);
        ByteBuf f2 = buf.slice(5, 5);
        log(f1);
        log(f2);

        f1.setByte(0, 'b');
        log(f1);
        log(buf);
    }
}
