import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Klasa reprezentująca planszę do gry w życie.
 */
public class GameOfLifeBoard {
    private final List<List<GameOfLifeCell>> board;
    private final List<GameOfLifeRow> rows;
    private final List<GameOfLifeColumn> columns;
    private final int rowsCount;
    private final int colsCount;
    private final GameOfLifeSimulator simulator;

    /**
     * Konstruktor klasy {@link GameOfLifeBoard}.
     * Inicjalizuje planszę z zadanymi wymiarami i symulatorem.
     *
     * @param rowsCount Liczba wierszy planszy.
     * @param colsCount Liczba kolumn planszy.
     * @param simulator Symulator gry.
     */
    public GameOfLifeBoard(int rowsCount, int colsCount, GameOfLifeSimulator simulator) {
        Objects.requireNonNull(simulator);
        this.rowsCount = Math.max(rowsCount, 1);
        this.colsCount = Math.max(colsCount, 1);

        this.board = new ArrayList<>(this.rowsCount);
        for (int i = 0; i < this.rowsCount; i++) {
            board.add(new ArrayList<>(this.colsCount));
        }

        this.rows = new ArrayList<>(this.rowsCount);
        this.columns = new ArrayList<>(this.colsCount);
        this.simulator = simulator;
        initializeBoard();
    }

    /**
     * Inicjalizuje planszę losowymi wartościami komórek
     * Korzysta z `Collections.shuffle()` do losowego ustawienia stanu komórek
     */
    private void initializeBoard() {
        List<GameOfLifeCell> allCells = new ArrayList<>(rowsCount * colsCount);

        // Określamy liczbę żywych i martwych komórek
        int aliveCount = rowsCount * colsCount / 2;
        int deadCount = (rowsCount * colsCount) - aliveCount;

        // Dodajemy komórki żywe
        for (int i = 0; i < aliveCount; i++) {
            allCells.add(new GameOfLifeCell(true));
        }

        // Dodajemy komórki martwe
        for (int i = 0; i < deadCount; i++) {
            allCells.add(new GameOfLifeCell(false));
        }

        // Losowe przetasowanie komórek na planszy
        Collections.shuffle(allCells);

        for (int i = 0; i < rowsCount; i++) {
            List<GameOfLifeCell> row = new ArrayList<>(colsCount);
            for (int j = 0; j < colsCount; j++) {
                row.add(allCells.removeFirst());
            }
            board.set(i, row);
            rows.add(new GameOfLifeRow(row));
        }

        for (int j = 0; j < colsCount; j++) {
            List<GameOfLifeCell> column = new ArrayList<>(rowsCount);
            for (int i = 0; i < rowsCount; i++) {
                column.add(board.get(i).get(j));
            }
            columns.add(new GameOfLifeColumn(column));
        }

        linkNeighbors();
    }

    /**
     * Łączy sąsiadujące komórki z każdą komórką w planszy, umożliwiając im dostęp do swoich sąsiadów.
     */
    private void linkNeighbors() {
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < colsCount; j++) {
                List<GameOfLifeCell> neighbors = new ArrayList<>(8);
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        if (x == 0 && y == 0) {
                            continue;
                        }
                        int ni = (i + x + rowsCount) % rowsCount;
                        int nj = (j + y + colsCount) % colsCount;
                        neighbors.add(board.get(ni).get(nj));
                    }
                }
                board.get(i).get(j).setNeighbors(neighbors);
            }
        }
    }

    /**
     * Zwraca wartość stanu komórki na danej pozycji.
     *
     * @param x Indeks wiersza.
     * @param y Indeks kolumny.
     * @return True, jeśli komórka jest żywa; false, jeśli jest martwa.
     */
    public boolean get(int x, int y) {
        return board.get(x).get(y).getCellValue();
    }

    /**
     * Zwraca komórkę na danej pozycji.
     *
     * @param x Indeks wiersza.
     * @param y Indeks kolumny.
     * @return GameOfLifeCell
     */
    public GameOfLifeCell getCell(int x, int y) {
        return board.get(x).get(y);
    }

    /**
     * Ustawia nowy stan komórki na danej pozycji.
     *
     * @param x     Indeks wiersza.
     * @param y     Indeks kolumny.
     * @param state Nowy stan komórki (true — żywa, false — martwa).
     */
    public void set(int x, int y, boolean state) {
        board.get(x).get(y).updateState(state);
        rows.set(x, new GameOfLifeRow(board.get(x)));
        List<GameOfLifeCell> column = new ArrayList<>(rowsCount);
        for (int i = 0; i < rowsCount; i++) {
            column.add(board.get(i).get(y));
        }
        columns.set(y, new GameOfLifeColumn(column));
    }

    /**
     * Zwraca kopię listy obiektów {@link GameOfLifeRow}.
     *
     * @return Kopia listy rzędów planszy.
     */
    public List<GameOfLifeRow> getRows() {
        return Collections.unmodifiableList(rows);
    }

    /**
     * Zwraca kopię listy obiektów {@link GameOfLifeColumn}.
     *
     * @return Kopia listy kolumn planszy.
     */
    public List<GameOfLifeColumn> getColumns() {
        return Collections.unmodifiableList(columns);
    }

    /**
     * Wykonuje krok symulacji w dostępnej symulacji.
     */
    public void doSimulationStep() {
        simulator.doStep(this);
    }

    /**
     * Zwraca kolumnę o indeksie i jako obiekt GameOfLifeColumn.
     *
     * @param i Indeks kolumny.
     * @return Obiekt GameOfLifeColumn reprezentujący kolumnę o podanym indeksie.
     */
    public GameOfLifeColumn getColumn(int i) {
        return columns.get(i);
    }

    /**
     * Zwraca wiersz o indeksie i jako obiekt GameOfLifeRow.
     *
     * @param i Indeks wiersza.
     * @return Obiekt GameOfLifeRow reprezentujący wiersz o podanym indeksie.
     */
    public GameOfLifeRow getRow(int i) {
        return rows.get(i);
    }
}
