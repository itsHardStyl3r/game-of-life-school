import java.util.List;

/**
 * Abstrakcyjna klasa reprezentująca grupę komórek w grze w życie.
 * Klasa `GameOfLifeGroup` przechowuje listę `cells`, która reprezentuje
 * grupę komórek. Zawiera metody do liczenia liczby żywych oraz martwych
 * komórek w tej grupie.
 */
public abstract class GameOfLifeGroup {
    protected final List<GameOfLifeCell> cells;

    public GameOfLifeGroup(List<GameOfLifeCell> cells) {
        this.cells = cells;
    }

    /**
     * Liczy liczbę żywych komórek w grupie.
     * Przechodzi przez wszystkie komórki w liście `cells` i zlicza te,
     * które są żywe (posiadają wartość true).
     *
     * @return liczba żywych komórek w grupie
     */
    public int countAliveCells() {
        int aliveCount = 0;
        for (GameOfLifeCell cell : cells) {
            if (cell.getCellValue()) {
                aliveCount++;
            }
        }
        return aliveCount;
    }

    /**
     * Liczy liczbę martwych komórek w grupie.
     * Metoda wykorzystuje `countAliveCells()` i odejmuje jego wynik
     * od całkowitej liczby komórek, co daje liczbę komórek martwych.
     *
     * @return liczba martwych komórek w grupie
     */
    public int countDeadCells() {
        return cells.size() - countAliveCells();
    }
}
