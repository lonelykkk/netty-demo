package cn.itcast.netty.test04;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

/**
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/12/6 10:47
 * @Version V1.0
 */
public class TestByteBuf {

    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        log(buf);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            sb.append("a");
        }
        buf.writeBytes(sb.toString().getBytes());
        log(buf);
    }

    public static void log(ByteBuf buffer) {
        int length = buffer.readableBytes();
        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuilder buf = new StringBuilder(rows * 80 * 2)
                .append("read index:").append(buffer.readerIndex())
                .append(" write index:").append(buffer.writerIndex())
                .append(" capacity:").append(buffer.capacity())
                .append(NEWLINE);
        appendPrettyHexDump(buf, buffer);
        System.out.println(buf.toString());
    }
}
