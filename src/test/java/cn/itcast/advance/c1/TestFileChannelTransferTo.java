package cn.itcast.advance.c1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * 传输数据，将一个数据传输到另一个数据
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/11/28 15:38
 * @Version V1.0
 */
public class TestFileChannelTransferTo {
    public static void main(String[] args) {
        //传输数据，将一个数据传输到另一个数据
        try (FileChannel from = new FileInputStream("data.txt").getChannel();
             FileChannel to = new FileOutputStream("to.txt").getChannel();
             ) {
            //效率高，底层会利用操作系统的零拷贝进行优化
            from.transferTo(0, from.size(), to);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
