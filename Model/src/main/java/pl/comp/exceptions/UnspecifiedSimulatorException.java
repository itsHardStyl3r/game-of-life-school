package pl.comp.exceptions;

import static pl.comp.GameOfLifeBoard.getLocaleMessage;

public class UnspecifiedSimulatorException extends RuntimeException {
    public UnspecifiedSimulatorException() {
        super(getLocaleMessage("unspecifiedSimulatorException"));
    }

    public UnspecifiedSimulatorException(String message) {
        super(message);
    }
}
