package com.plagiarismcheck.main.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 哈希工具类，负责计算字符串的哈希值。
 */
public class HashUtil {
    private static final MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

    /**
     * 计算字符串的哈希值
     * @param word 输入字符串
     * @return 哈希值（BigInteger）
     */
    public static BigInteger hash(String word) {
        byte[] digest;
        synchronized (md) {
            digest = md.digest(word.getBytes(StandardCharsets.UTF_8));
        }
        return new BigInteger(1, digest);
    }
}