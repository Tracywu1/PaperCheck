package com.plagiarismcheck.main.exception;

public class PlagiarismException extends Exception {

    private PlagiarismException(String message) {
        super(message);
    }

    public static class EmptyTextException extends PlagiarismException {
        public EmptyTextException(String message) {
            super(message);
        }
    }
}