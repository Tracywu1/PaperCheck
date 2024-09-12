package com.plagiarismcheck.main.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 常量类，存储全局使用的常量值。
 */
public class Constants {
    // SimHash的位数，决定了精度
    public static final int HASH_BITS = 64; // 增加位数以提高精度

    // 停用词列表，用于过滤掉不重要的常用词
    public static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
            "的", "是", "在", "我", "有", "和", "就", "不", "人", "都", "一", "一个", "上", "也", "很", "到", "说", "要", "去", "你",
            "会", "着", "没有", "看", "好", "自己", "这", "了", "被", "那", "怎么", "这个", "还", "可以", "什么", "给", "我们",
            "a", "an", "the", "in", "on", "at", "of", "for", "to", "with", "by", "from", "up", "about", "into",
            "over", "after", "beneath", "under", "above"
            // 可以继续添加更多停用词
    ));
}