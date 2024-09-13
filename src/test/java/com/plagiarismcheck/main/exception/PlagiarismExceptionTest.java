package com.plagiarismcheck.main.exception;

import com.plagiarismcheck.main.core.PlagiarismChecker;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlagiarismExceptionTest {

    @Test
    public void testEmptyTextException() {
        // 设计目标：当输入文本为空时，抛出 EmptyTextException
        // 应用位置：PlagiarismChecker.checkPlagiarism() 方法开始处
        Exception exception = assertThrows(PlagiarismException.EmptyTextException.class, () -> PlagiarismChecker.checkPlagiarism("", "Some text"));
        assertEquals("Original text cannot be empty", exception.getMessage());
    }

}
