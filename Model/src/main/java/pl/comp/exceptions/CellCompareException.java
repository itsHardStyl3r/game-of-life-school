package pl.comp.exceptions;

import static pl.comp.GameOfLifeBoard.getLocaleMessage;

public class CellCompareException extends RuntimeException {
    public CellCompareException() {
        super(getLocaleMessage("cellCompareException"));
    }

    public CellCompareException(String message) {
        super(message);
    }

    public CellCompareException(Throwable cause) {
        super(cause);
    }

    public CellCompareException(String message, Throwable cause) {
        super(message, cause);
    }
}
