package pl.comp.exceptions;

import static pl.comp.GameOfLifeBoard.getLocaleMessage;

public class InvalidNeighborListException extends RuntimeException {
    public InvalidNeighborListException() {
        super(getLocaleMessage("invalidNeighborListException"));
    }

    public InvalidNeighborListException(String message) {
        super(message);
    }
}
