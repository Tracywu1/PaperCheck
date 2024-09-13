package com.plagiarismcheck.main.core;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.plagiarismcheck.main.exception.PlagiarismException;

import java.util.HashSet;
import java.util.Set;

/**
 * PlagiarismChecker 类用于检测两段文本之间的抄袭程度。
 * 它通过计算两段文本的相似哈希值（SimHash）之间的汉明距离来评估抄袭程度。
 */
public class PlagiarismChecker {
    private static volatile JiebaSegmenter SEGMENTER;
    private static final int SHORT_TEXT_THRESHOLD = 50; // 定义短文本的阈值

    // 使用延迟初始化和双重检查锁定模式来初始化JiebaSegmenter，这样可以避免在不需要时进行昂贵的初始化操作。
    private static JiebaSegmenter getSegmenter() {
        if (SEGMENTER == null) {
            synchronized (PlagiarismChecker.class) {
                if (SEGMENTER == null) {
                    SEGMENTER = new JiebaSegmenter();
                }
            }
        }
        return SEGMENTER;
    }

    /**
     * 检查两段文本之间的抄袭程度。
     *
     * @param originalText    原始文本。
     * @param plagiarizedText 被检查抄袭的文本。
     * @return 返回一个0到1之间的浮点数，表示抄袭程度，越接近1表示抄袭越严重。
     */
    public static double checkPlagiarism(String originalText, String plagiarizedText) throws PlagiarismException.EmptyTextException {
        // 处理空文本的情况
        if (originalText == null || originalText.trim().isEmpty() ||
                plagiarizedText == null || plagiarizedText.trim().isEmpty()) {
            throw new PlagiarismException.EmptyTextException("Original text cannot be empty");
        }

        // 如果两个文本都是短文本，使用词级别的相似度计算
        if (originalText.length() < SHORT_TEXT_THRESHOLD && plagiarizedText.length() < SHORT_TEXT_THRESHOLD) {
            return calculateShortTextSimilarity(originalText, plagiarizedText);
        }

        // 为原始文本和被检查抄袭的文本分别计算SimHash值
        String simHash1 = SimHashCalculator.getSimHash(originalText);
        String simHash2 = SimHashCalculator.getSimHash(plagiarizedText);

        // 计算两个SimHash之间的汉明距离
        int hammingDistance = getHammingDistance(simHash1, simHash2);

        // 调整相似度计算公式
        return 1.0 / (1.0 + Math.log(1 + hammingDistance));
    }

    // 优化短文本相似度计算
    private static double calculateShortTextSimilarity(String text1, String text2) {
        Set<String> words1 = new HashSet<>(getSegmenter().sentenceProcess(text1));
        Set<String> words2 = new HashSet<>(getSegmenter().sentenceProcess(text2));

        Set<String> union = new HashSet<>(words1);
        union.addAll(words2);

        Set<String> intersection = new HashSet<>(words1);
        intersection.retainAll(words2);

        return (double) intersection.size() / union.size();
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