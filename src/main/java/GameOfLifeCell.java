import java.util.Arrays;

/**
 * Reprezentuje pojedynczą komórkę w grze w życie.
 * Komórka posiada swój stan (żywa lub martwa) oraz referencje do swoich sąsiadów.
 * Na podstawie stanu sąsiadów komórka decyduje o swoim stanie w kolejnym kroku symulacji.
 */
public class GameOfLifeCell {
    private boolean value;
    private GameOfLifeCell[] neighbors;

    /**
     * Tworzy nową komórkę z określonym stanem.
     *
     * @param value Stan komórki. True oznacza żywą, false — martwą.
     */
    public GameOfLifeCell(boolean value) {
        this.value = value;
        this.neighbors = new GameOfLifeCell[8];
    }

    /**
     * Zwraca aktualny stan komórki.
     *
     * @return True, jeśli komórka jest żywa, false przeciwnie.
     */
    public boolean getCellValue() {
        return value;
    }

    /**
     * Oblicza stan komórki w następnym kroku symulacji na podstawie stanu jej sąsiadów.
     */
    public boolean nextState() {
        int aliveNeighbors = 0;
        for (GameOfLifeCell neighbor : neighbors) {
            if (neighbor != null && neighbor.getCellValue()) {
                aliveNeighbors++;
            }
        }
        if (value) {
            return aliveNeighbors == 2 || aliveNeighbors == 3;
        } else {
            return aliveNeighbors == 3;
        }
    }

    /**
     * Zmienia stan komórki.
     *
     * @param newState Stan komórki. True oznacza żywą, false — martwą.
     */
    public void updateState(boolean newState) {
        this.value = newState;
    }

    /**
     * Ustawia sąsiadów komórki. Metoda przyjmuje tablicę dokładnie 8 "komórek sąsiadów".
     *
     * @param neighbors Tablica 8 sąsiednich komórek typu GameOfLifeCell.
     */
    public void setNeighbors(GameOfLifeCell[] neighbors) {
        if (neighbors.length == 8) {
            this.neighbors = Arrays.copyOf(neighbors, 8);
        }
    }
}
