package com.plagiarismcheck.main.core;

import com.plagiarismcheck.main.config.Constants;

/**
 * PlagiarismChecker 类用于检测两段文本之间的抄袭程度。
 * 它通过计算两段文本的相似哈希值（SimHash）之间的汉明距离来评估抄袭程度。
 */
public class PlagiarismChecker {

    /**
     * 检查两段文本之间的抄袭程度。
     *
     * @param originalText 原始文本。
     * @param plagiarizedText 被检查抄袭的文本。
     * @return 返回一个0到1之间的浮点数，表示抄袭程度，越接近1表示抄袭越严重。
     * @throws Exception 如果在计算过程中发生错误。
     */
    public static double checkPlagiarism(String originalText, String plagiarizedText) throws Exception {
        // 处理空文本的情况
        if (originalText == null || originalText.trim().isEmpty() ||
                plagiarizedText == null || plagiarizedText.trim().isEmpty()) {
            return 0.0;
        }

        // 为原始文本和被检查抄袭的文本分别计算SimHash值
        String simHash1 = SimHashCalculator.getSimHash(originalText);
        String simHash2 = SimHashCalculator.getSimHash(plagiarizedText);

        // 计算两个SimHash之间的汉明距离
        int hammingDistance = getHammingDistance(simHash1, simHash2);

        // 通过汉明距离和哈希的总位数来计算抄袭程度的百分比
        // 返回的值范围从0（完全不同）到1（完全相同）
        return 1 - (double) hammingDistance / Constants.HASH_BITS;
    }

    /**
     * 计算两个字符串之间的汉明距离。
     * 汉明距离是指两个等长字符串对应位置上的不同字符的个数。
     *
     * @param simHash1 第一个字符串（通常是SimHash值）。
     * @param simHash2 第二个字符串（通常是另一个SimHash值）。
     * @return 返回两个字符串之间的汉明距离。
     */
    private static int getHammingDistance(String simHash1, String simHash2) {
        int distance = 0;
        // 遍历两个字符串的每一个字符
        for (int i = 0; i < simHash1.length(); i++) {
            // 如果对应位置的字符不同，则汉明距离加1
            if (simHash1.charAt(i) != simHash2.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }
}