package cn.itcast.nio.c1;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static cn.itcast.advance.c1.ByteBufferUtil.debugAll;
/**
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/11/28 10:32
 * @Version V1.0
 */
public class TestByteBufferString {

    public static void main(String[] args) {
        //1.字符串转为ByteBuffer
        ByteBuffer buffer1 = ByteBuffer.allocate(16);
        buffer1.put("hello".getBytes());
        debugAll(buffer1);

        //2. Charset
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("hello");
        debugAll(buffer2);

        // 3. wrap
        ByteBuffer buffer3 = ByteBuffer.wrap("hello".getBytes());
        debugAll(buffer3);

        //ByteBuffer为字符串转
        String str1 = StandardCharsets.UTF_8.decode(buffer2).toString(); //decode只能转读模式
        System.out.println(str1);
    }
}
