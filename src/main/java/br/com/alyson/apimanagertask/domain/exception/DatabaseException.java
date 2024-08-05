package br.com.alyson.apimanagertask.domain.exception;

public class DatabaseException extends BusinessException {

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
