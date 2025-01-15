package pl.comp.exceptions;

import static pl.comp.GameOfLifeBoard.getLocaleMessage;

public class DaoWriteException extends RuntimeException {
    public DaoWriteException() {
        super(getLocaleMessage("daoException"));
    }

    public DaoWriteException(String message) {
        super(message);
    }

    public DaoWriteException(Throwable cause) {
        super(cause);
    }

    public DaoWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
