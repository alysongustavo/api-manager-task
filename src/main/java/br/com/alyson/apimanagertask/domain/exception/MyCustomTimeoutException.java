package br.com.alyson.apimanagertask.domain.exception;

public class MyCustomTimeoutException extends RuntimeException {

    public MyCustomTimeoutException(String message) {
        super(message);
    }

    public MyCustomTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
