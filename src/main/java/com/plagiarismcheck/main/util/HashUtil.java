package com.plagiarismcheck.main.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 哈希工具类，负责计算字符串的哈希值。
 */
public class HashUtil {

    /**
     * 计算字符串的哈希值
     * @param word 输入字符串
     * @return 哈希值（BigInteger）
     * @throws NoSuchAlgorithmException 如果MD5算法不可用
     */
    public static BigInteger hash(String word) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(word.getBytes());
        return new BigInteger(1, messageDigest);
    }
}