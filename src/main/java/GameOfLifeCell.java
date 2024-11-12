import org.apache.commons.collections4.list.FixedSizeList;

import java.util.List;

/**
 * Reprezentuje pojedynczą komórkę w grze w życie.
 * Komórka posiada swój stan (żywa lub martwa) oraz referencje do swoich sąsiadów.
 * Na podstawie stanu sąsiadów komórka decyduje o swoim stanie w kolejnym kroku symulacji.
 */
public class GameOfLifeCell {
    private boolean value;
    private List<GameOfLifeCell> neighbors;

    /**
     * Tworzy nową komórkę z określonym stanem.
     *
     * @param value Stan komórki. True oznacza żywą, false — martwą.
     */
    public GameOfLifeCell(boolean value) {
        this.value = value;
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
     *
     * @return True, jeśli żywa, false, jeśli martwa.
     */
    public boolean nextState() {
        int aliveNeighbors = countLivingNeighbors();
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
     * Zlicza liczbę żywych sąsiadów wokół tej komórki.
     * Metoda przechodzi przez listę `neighbors` i zwiększa
     * licznik `liveNeighbors` dla każdego niepustego sąsiada, który jest żywy.
     *
     * @return liczba żywych sąsiadów wokół tej komórki
     */
    private int countLivingNeighbors() {
        int liveNeighbors = 0;
        for (GameOfLifeCell neighbor : neighbors) {
            if (neighbor.getCellValue()) {
                liveNeighbors++;
            }
        }
        return liveNeighbors;
    }

    public List<GameOfLifeCell> getNeighbors() {
        return List.copyOf(neighbors);
    }

    /**
     * Ustawia sąsiadów komórki. Metoda przyjmuje listę dokładnie 8 "komórek sąsiadów".
     *
     * @param neighbors Lista 8 sąsiednich komórek typu GameOfLifeCell.
     * @throws IllegalArgumentException Gdy rozmiar listy sąsiadów nie wynosi 8.
     */
    public void setNeighbors(List<GameOfLifeCell> neighbors) throws IllegalArgumentException {
        if (neighbors.size() != 8) {
            throw new IllegalArgumentException("Rozmiar sąsiadów musi wynosić 8.");
        }
        this.neighbors = FixedSizeList.fixedSizeList(neighbors);
    }
}
