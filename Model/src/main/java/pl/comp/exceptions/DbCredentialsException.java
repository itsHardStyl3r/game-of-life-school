package pl.comp.exceptions;

import static pl.comp.GameOfLifeBoard.getLocaleMessage;

public class DbCredentialsException extends RuntimeException {
    public DbCredentialsException() {
        super(getLocaleMessage("credentialsError"));
    }

    public DbCredentialsException(String message) {
        super(message);
    }

    public DbCredentialsException(Throwable cause) {
        super(cause);
    }

    public DbCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}
