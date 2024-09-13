package com.plagiarismcheck.main;

import com.plagiarismcheck.main.core.PlagiarismChecker;
import com.plagiarismcheck.main.util.FileUtil;

/**
 * 主类，包含程序的入口点。
 * 负责处理命令行参数并协调整个查重过程。
 * @author Lenovo
 */
public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java com.plagiarism.main.Main <originalFile> <plagiarizedFile> <outputFile>");
            return;
        }

        String originalFile = args[0];
        String plagiarizedFile = args[1];
        String outputFile = args[2];

        try {
            String originalText = FileUtil.readFile(originalFile);
            String plagiarizedText = FileUtil.readFile(plagiarizedFile);

            double similarity = PlagiarismChecker.checkPlagiarism(originalText, plagiarizedText);

            FileUtil.writeResult(outputFile, similarity);

            System.out.println("Original text: " + originalText.substring(0, Math.min(100, originalText.length())) + "...");
            System.out.println("Plagiarized text: " + plagiarizedText.substring(0, Math.min(100, plagiarizedText.length())) + "...");
            System.out.println("Similarity: " + String.format("%.2f", similarity));
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}