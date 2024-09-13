package com.plagiarismcheck.main.core;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.plagiarismcheck.main.config.Constants;
import com.plagiarismcheck.main.util.HashUtil;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * SimHashCalculator 类用于计算给定文本的 SimHash 值。
 * SimHash 是一种用于快速估计两个文本相似度的算法，通过计算文本的哈希值来实现。
 * @author Lenovo
 */
public class SimHashCalculator {

    // 使用静态JiebaSegmenter实例进行分词，避免每次调用都创建新实例
    private static final JiebaSegmenter SEGMENTER = new JiebaSegmenter();

    /**
     * 计算给定文本的 SimHash 值。
     *
     * @param text 输入的文本字符串
     * @return 文本的 SimHash 值，以二进制字符串形式表示
     * @throws NoSuchAlgorithmException 如果哈希计算过程中需要但找不到指定的哈希算法，则抛出此异常（虽然在此实现中可能未直接使用）
     */
    public static String getSimHash(String text) throws NoSuchAlgorithmException {
        if (text == null || text.trim().isEmpty()) {
            // 对于空文本，返回全0的SimHash
            return String.join("", Collections.nCopies(Constants.HASH_BITS, "0"));
        }

        // 使用jieba分词对文本进行分词处理
        List<String> words = SEGMENTER.sentenceProcess(text);

        // 过滤掉停止词，并统计剩余词汇的词频
        Map<String, Long> wordFrequency = words.stream()
                .filter(word -> !Constants.STOP_WORDS.contains(word)) // 过滤停止词
                .collect(Collectors.groupingBy(w -> w, Collectors.counting())); // 统计词频

        // 如果过滤后没有剩余的词，返回全0的SimHash
        if (wordFrequency.isEmpty()) {
            return String.join("", Collections.nCopies(Constants.HASH_BITS, "0"));
        }

        // 初始化用于存储每个哈希位的权重和的数组
        int[] v = new int[Constants.HASH_BITS];

        // 遍历词频映射，根据每个词的哈希值更新数组v
        for (Map.Entry<String, Long> entry : wordFrequency.entrySet()) {
            BigInteger hash = HashUtil.hash(entry.getKey());
            long weight = entry.getValue();

            // 遍历哈希值的每一位
            for (int i = 0; i < Constants.HASH_BITS; i++) {
                if (hash.testBit(Constants.HASH_BITS - 1 - i)) {
                    v[i] += weight;
                } else {
                    v[i] -= weight;
                }
            }
        }

        // 将数组v中的每个元素转换为二进制字符串，并拼接成最终的SimHash值
        StringBuilder simHashBuilder = new StringBuilder(Constants.HASH_BITS);
        for (int i = 0; i < Constants.HASH_BITS; i++) {
            if (v[i] >= 0) {
                simHashBuilder.append("1");
            } else {
                simHashBuilder.append("0");
            }
        }
        return simHashBuilder.toString();
    }
}