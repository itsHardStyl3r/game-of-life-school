package pl.comp.exceptions;

import static pl.comp.GameOfLifeBoard.getLocaleMessage;

public class DaoReadException extends RuntimeException {
    public DaoReadException() {
        super(getLocaleMessage("daoException"));
    }

    public DaoReadException(String message) {
        super(message);
    }

    public DaoReadException(Throwable cause) {
        super(cause);
    }

    public DaoReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
