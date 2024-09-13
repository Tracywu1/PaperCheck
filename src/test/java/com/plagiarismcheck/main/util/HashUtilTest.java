package com.plagiarismcheck.main.util;

import org.junit.Test;

import static org.junit.Assert.*;

import java.math.BigInteger;

public class HashUtilTest {

    @Test
    public void testHashWithNormalString() {
        // 测试普通字符串的哈希计算
        String input = "test string";
        BigInteger hash = HashUtil.hash(input);
        assertNotNull("哈希值不应为空", hash);
        assertTrue("哈希值应大于0", hash.compareTo(BigInteger.ZERO) > 0);
    }

    @Test
    public void testHashConsistency() {
        // 测试哈希计算的一致性
        String input = "consistency test string";
        BigInteger hash1 = HashUtil.hash(input);
        BigInteger hash2 = HashUtil.hash(input);
        assertEquals("相同输入应产生相同的哈希值", hash1, hash2);
    }

    @Test
    public void testHashWithEmptyString() {
        // 测试空字符串的哈希计算
        String input = "";
        BigInteger hash = HashUtil.hash(input);
        assertNotNull("空字符串的哈希值不应为空", hash);
    }

    @Test
    public void testHashWithLongString() {
        // 测试长字符串的哈希计算
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("long string ");
        }
        String input = sb.toString();
        BigInteger hash = HashUtil.hash(input);
        assertNotNull("长字符串的哈希值不应为空", hash);
    }
}