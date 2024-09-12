package com.plagiarismcheck.main.core;

import com.plagiarismcheck.main.core.SimHashCalculator;
import org.junit.Test;
import static org.junit.Assert.*;

public class SimHashCalculatorTest {

    @Test
    public void testGetSimHashWithNormalText() throws Exception {
        // 测试普通文本的SimHash计算
        String text = "This is a test text for SimHash calculation.";
        String simHash = SimHashCalculator.getSimHash(text);
        assertNotNull("SimHash不应为空", simHash);
        assertEquals("SimHash长度应为64位", 64, simHash.length());
        assertTrue("SimHash应只包含0和1", simHash.matches("[01]+"));
    }

    @Test
    public void testGetSimHashWithEmptyString() throws Exception {
        // 测试空字符串的SimHash计算
        String text = "";
        String simHash = SimHashCalculator.getSimHash(text);
        assertNotNull("空字符串的SimHash不应为空", simHash);
        assertEquals("空字符串的SimHash长度应为64位", 64, simHash.length());
    }

    @Test
    public void testGetSimHashWithChineseText() throws Exception {
        // 测试中文文本的SimHash计算
        String text = "这是一段用于测试的中文文本。";
        String simHash = SimHashCalculator.getSimHash(text);
        assertNotNull("中文文本的SimHash不应为空", simHash);
        assertEquals("中文文本的SimHash长度应为64位", 64, simHash.length());
    }

    @Test
    public void testGetSimHashConsistency() throws Exception {
        // 测试SimHash计算的一致性
        String text = "Consistency test text";
        String simHash1 = SimHashCalculator.getSimHash(text);
        String simHash2 = SimHashCalculator.getSimHash(text);
        assertEquals("相同文本的SimHash应该相同", simHash1, simHash2);
    }

    @Test
    public void testGetSimHashWithSpecialCharacters() throws Exception {
        // 测试包含特殊字符的文本的SimHash计算
        String text = "Special chars: !@#$%^&*()_+{}|:\"<>?[];',./";
        String simHash = SimHashCalculator.getSimHash(text);
        assertNotNull("包含特殊字符的文本的SimHash不应为空", simHash);
        assertEquals("包含特殊字符的文本的SimHash长度应为64位", 64, simHash.length());
    }
}