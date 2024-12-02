package cn.itcast.nio.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/11/26 9:20
 * @Version V1.0
 */
@Slf4j
public class TestByteBuffer {
    public static void main(String[] args) {
        //FileChannel 读写通道
        // 1.输入输出流获取 2.RandomAccessFile
        try (FileChannel channel = new FileInputStream("data.txt").getChannel()) {
            // 准备缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while (true) {
                // 从channel读取数据，向buffer写入
                int read = channel.read(buffer);
                log.debug("读取到的字节：{}", read);
                if (read == -1) {
                    break;
                }
                //打印buffer的内容
                buffer.flip(); //切换至读模式
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    log.debug("实际的字节：{}", (char) b);
                }
                ///切换至写模式
                buffer.clear();
            }
        } catch (IOException e) {
        }
    }
}
