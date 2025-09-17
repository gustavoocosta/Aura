package com.gustavo.musicapp.exception;

public class DuplicateMusicException extends RuntimeException {
    public DuplicateMusicException(String message) {
        super(message);
    }

    public DuplicateMusicException(String message, Throwable cause) {
        super(message, cause);
    }
}
