package com.plagiarismcheck.main.util;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.*;

import java.io.*;

public class FileUtilTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testReadFileWithNormalContent() throws Exception {
        // 测试读取普通内容的文件
        File file = tempFolder.newFile("normal.txt");
        String content = "This is a test file content.";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        }
        String readContent = FileUtil.readFile(file.getPath());
        assertEquals("读取的内容应与写入的内容相同", content, readContent);
    }

    @Test
    public void testReadFileWithEmptyContent() throws Exception {
        // 测试读取空文件
        File file = tempFolder.newFile("empty.txt");
        String readContent = FileUtil.readFile(file.getPath());
        assertEquals("空文件读取的内容应为空字符串", "", readContent);
    }

    @Test(expected = IOException.class)
    public void testReadNonexistentFile() throws Exception {
        // 测试读取不存在的文件
        FileUtil.readFile("nonexistent.txt");
    }

    @Test
    public void testWriteResultWithNormalValue() throws Exception {
        // 测试写入正常的相似度值
        File file = tempFolder.newFile("result.txt");
        double similarity = 0.75;
        FileUtil.writeResult(file.getPath(), similarity);
        String content = FileUtil.readFile(file.getPath());
        assertEquals("写入的内容应为相似度的字符串表示", "0.75", content);
    }

    @Test
    public void testWriteResultWithZeroValue() throws Exception {
        // 测试写入零相似度值
        File file = tempFolder.newFile("zero_result.txt");
        double similarity = 0.0;
        FileUtil.writeResult(file.getPath(), similarity);
        String content = FileUtil.readFile(file.getPath());
        assertEquals("写入的内容应为0.00", "0.00", content);
    }
}