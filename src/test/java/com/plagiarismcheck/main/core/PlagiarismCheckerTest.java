package com.plagiarismcheck.main.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlagiarismCheckerTest {

    // 测试检查抄袭的功能
    @Test
    public void testCheckPlagiarism() throws Exception {
        // 定义原始文本和涉嫌抄袭的文本
        String originalText = "今天是星期天，天气晴，今天晚上我要去看电影。";
        String plagiarizedText = "今天是周天，天气晴朗，我晚上要去看电影。";

        // 调用PlagiarismChecker类的checkPlagiarism方法检查两段文本的相似度
        double similarity = PlagiarismChecker.checkPlagiarism(originalText, plagiarizedText);
        System.out.println(similarity);

        // 验证返回的相似度是否在0到1之间
        assertTrue(similarity >= 0 && similarity <= 1);
        // 预期这两段文本的相似度应该很高，因此相似度应大于0.5
        assertTrue(similarity > 0.5);
    }

    // 测试检查两段完全相同文本的抄袭程度
    @Test
    public void testCheckPlagiarismWithIdenticalTexts() throws Exception {
        // 定义一段文本
        String text = "This is a test text.";

        // 调用PlagiarismChecker类的checkPlagiarism方法检查文本与其自身的相似度
        double similarity = PlagiarismChecker.checkPlagiarism(text, text);

        // 验证当两段文本完全相同时，相似度应为1.0
        // 使用一个小的误差范围（0.001）来允许浮点运算的微小差异
        assertEquals(1.0, similarity, 0.001);
    }

    // 测试部分内容重叠的文本
    @Test
    public void testPartiallySimularTexts() throws Exception {
        // 定义原始文本和涉嫌抄袭的文本
        String originalText = "这是一个测试文本，用于检查相似度。";
        String plagiarizedText = "这是一个测试文本，用于其他目的。";

        // 调用PlagiarismChecker类的checkPlagiarism方法检查两段文本的相似度
        double similarity = PlagiarismChecker.checkPlagiarism(originalText, plagiarizedText);
        System.out.println(similarity);

        assertTrue(similarity > 0.5 && similarity < 1.0);
    }

    // 测试完全不同的文本
    @Test
    public void testCompletelyDifferentTexts() throws Exception {
        // 定义原始文本和涉嫌抄袭的文本
        String originalText = "今天晚上吃鸡，你来吃吗？";
        String plagiarizedText = "如何科学评价毛泽东思想？";

        // 调用PlagiarismChecker类的checkPlagiarism方法检查两段文本的相似度
        double similarity = PlagiarismChecker.checkPlagiarism(originalText, plagiarizedText);
        System.out.println(similarity);

        assertTrue(similarity < 0.6);
    }

    // 测试空文本和一个非空文本
    @Test
    public void testEmptyText() throws Exception {
        // 定义原始文本和涉嫌抄袭的文本
        String originalText = "";
        String plagiarizedText = "这是一个非空文本。";

        // 调用PlagiarismChecker类的checkPlagiarism方法检查两段文本的相似度
        double similarity = PlagiarismChecker.checkPlagiarism(originalText, plagiarizedText);
        System.out.println(similarity);

        assertEquals(0.0, similarity, 0.01);
    }

    // 测试包含大量停用词的文本
    @Test
    public void testTextWithManyStopWords() throws Exception {
        // 定义原始文本和涉嫌抄袭的文本
        String originalText = "我是一个的很好的人，我有很多的朋友。";
        String plagiarizedText = "我是一个的很好的学生，我有很多的书本。";

        // 调用PlagiarismChecker类的checkPlagiarism方法检查两段文本的相似度
        double similarity = PlagiarismChecker.checkPlagiarism(originalText, plagiarizedText);
        System.out.println(similarity);

        assertTrue(similarity < 0.8);
    }

    // 测试包含特殊字符的文本
    @Test
    public void testSpecialCharacters() throws Exception {
        // 定义原始文本和涉嫌抄袭的文本
        String originalText = "这是一个包含特殊字符的文本：!@#$%^&*()_+";
        String plagiarizedText = "这是另一个包含特殊字符的文本：!@#$%^&*()_+";

        // 调用PlagiarismChecker类的checkPlagiarism方法检查两段文本的相似度
        double similarity = PlagiarismChecker.checkPlagiarism(originalText, plagiarizedText);
        System.out.println(similarity);

        assertTrue(similarity > 0.8);
    }

    // 测试长文本
    @Test
    public void testCheckPlagiarismWithLongTexts() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("这是一个很长的文本，用于测试系统处理大量文本的能力。");
        }
        String longText = sb.toString();
        double similarity = PlagiarismChecker.checkPlagiarism(longText, longText);
        assertEquals("相同的长文本相似度应为1", 1.0, similarity, 0.001);
    }
}