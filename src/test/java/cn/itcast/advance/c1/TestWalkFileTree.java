package cn.itcast.advance.c1;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 遍历文件夹所有文件
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/11/28 15:59
 * @Version V1.0
 */

public class TestWalkFileTree {
    public static void main(String[] args) throws IOException {
        // m1();

        // m2();
    }

    /**
     * 遍历文件夹下有多少个jar包
     * @throws IOException
     */
    private static void m2() throws IOException {
        Path path = Paths.get("C:\\Program Files\\Java\\jre1.8.0_351");
        AtomicInteger fileCount = new AtomicInteger();
        Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                if (file.toFile().getName().endsWith(".jar")) {
                    System.out.println(file);
                    fileCount.incrementAndGet();
                }
                return super.visitFile(file, attrs);
            }
        });
        System.out.println(fileCount); // 724
    }
    /**
     * 遍历文件夹所有文件
     * @throws IOException
     */
    private static void m1() throws IOException {
        Path path = Paths.get("C:\\Program Files\\Java\\jre1.8.0_351");
        AtomicInteger dirCount = new AtomicInteger();
        AtomicInteger fileCount = new AtomicInteger();
        Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException {
                System.out.println(dir);
                dirCount.incrementAndGet();
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                System.out.println(file);
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }
        });
        System.out.println(dirCount); // 133
        System.out.println(fileCount); // 1479
    }
}
