package pl.comp.exceptions;

import static pl.comp.GameOfLifeBoard.getLocaleMessage;

public class UnknownGameException extends RuntimeException {
    public UnknownGameException() {
        super(getLocaleMessage("gameNameEmpty"));
    }

    public UnknownGameException(String message) {
        super(message);
    }

    public UnknownGameException(Throwable cause) {
        super(cause);
    }

    public UnknownGameException(String message, Throwable cause) {
        super(message, cause);
    }
}
