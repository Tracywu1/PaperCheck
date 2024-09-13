package com.plagiarismcheck.main.util;

import java.io.*;

/**
 * 文件工具类，负责文件的读写操作。
 */
public class FileUtil {

    /**
     * 读取文件内容
     * @param filePath 文件路径
     * @return 文件内容字符串
     * @throws IOException 如果文件读取出错
     */
    public static String readFile(String filePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line);
                contentBuilder.append(System.lineSeparator());
            }
        }
        // 移除最后一个换行符
        if (contentBuilder.length() > 0) {
            contentBuilder.setLength(contentBuilder.length() - System.lineSeparator().length());
        }
        return contentBuilder.toString();
    }

    /**
     * 将相似度结果写入文件
     * @param filePath 输出文件路径
     * @param similarity 计算得到的相似度
     * @throws IOException 如果文件写入出错
     */
    public static void writeResult(String filePath, double similarity) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(String.format("%.2f", similarity));
        }
    }

}