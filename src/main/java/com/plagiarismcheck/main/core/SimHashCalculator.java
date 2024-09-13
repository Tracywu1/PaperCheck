package com.plagiarismcheck.main.core;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.plagiarismcheck.main.config.Constants;
import com.plagiarismcheck.main.util.HashUtil;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

/**
 * SimHashCalculator 类用于计算给定文本的 SimHash 值。
 * SimHash 是一种用于快速估计两个文本相似度的算法，通过计算文本的哈希值来实现。
 *
 * @author Lenovo
 */
public class SimHashCalculator {
    private static volatile JiebaSegmenter SEGMENTER;

    private static JiebaSegmenter getSegmenter() {
        if (SEGMENTER == null) {
            synchronized (SimHashCalculator.class) {
                if (SEGMENTER == null) {
                    SEGMENTER = new JiebaSegmenter();
                }
            }
        }
        return SEGMENTER;
    }

    /**
     * 计算给定文本的 SimHash 值。
     *
     * @param text 输入的文本字符串
     * @return 文本的 SimHash 值，以二进制字符串形式表示
     */
    public static String getSimHash(String text) {

        if (text == null || text.trim().isEmpty()) {
            // 对于空文本，返回全0的SimHash
            return String.join("", Collections.nCopies(Constants.HASH_BITS, "0"));
        }


        // 使用jieba分词对文本进行分词处理
        List<String> words = getSegmenter().sentenceProcess(text);


        // 使用数组来存储词频，避免使用流操作
        long[] v = new long[Constants.HASH_BITS];
        for (String word : words) {
            if (!Constants.STOP_WORDS.contains(word)) {
                BigInteger hash = HashUtil.hash(word);
                for (int i = 0; i < Constants.HASH_BITS; i++) {
                    if (hash.testBit(Constants.HASH_BITS - 1 - i)) {
                        v[i]++;
                    } else {
                        v[i]--;
                    }
                }
            }
        }

        // 使用StringBuilder构建最终的SimHash
        StringBuilder simHashBuilder = new StringBuilder(Constants.HASH_BITS);
        for (long bit : v) {
            simHashBuilder.append(bit >= 0 ? "1" : "0");
        }

        return simHashBuilder.toString();
    }
}