import org.apache.commons.collections4.list.FixedSizeList;

import java.util.*;

/**
 * Klasa reprezentująca planszę do gry w życie.
 */
public class GameOfLifeBoard {
    private final GameOfLifeCell[][] board;
    private List<GameOfLifeRow> rows;
    private List<GameOfLifeColumn> columns;
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
        this.board = new GameOfLifeCell[this.rowsCount][this.colsCount];

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
            for (int j = 0; j < colsCount; j++) {
                board[i][j] = allCells.removeFirst();
            }
            rows.add(new GameOfLifeRow(Arrays.asList(board[i])));
        }

        for (int j = 0; j < colsCount; j++) {
            List<GameOfLifeCell> column = new ArrayList<>(rowsCount);
            for (int i = 0; i < rowsCount; i++) {
                column.add(board[i][j]);
            }
            columns.add(new GameOfLifeColumn(column));
        }
        rows = FixedSizeList.fixedSizeList(rows);
        columns = FixedSizeList.fixedSizeList(columns);
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
                        neighbors.add(board[ni][nj]);
                    }
                }
                board[i][j].setNeighbors(neighbors);
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
        return board[x][y].getCellValue();
    }

    /**
     * Zwraca komórkę na danej pozycji.
     *
     * @param x Indeks wiersza.
     * @param y Indeks kolumny.
     * @return GameOfLifeCell
     */
    public GameOfLifeCell getCell(int x, int y) {
        return board[x][y];
    }

    /**
     * Ustawia nowy stan komórki na danej pozycji.
     *
     * @param x     Indeks wiersza.
     * @param y     Indeks kolumny.
     * @param state Nowy stan komórki (true — żywa, false — martwa).
     */
    public void set(int x, int y, boolean state) {
        board[x][y].updateState(state);
    }

    /**
     * Zwraca listę obiektów {@link GameOfLifeRow}.
     *
     * @return Kopia listy rzędów planszy.
     */
    public List<GameOfLifeRow> getRows() {
        return rows;
    }

    /**
     * Zwraca listę obiektów {@link GameOfLifeColumn}.
     *
     * @return Kopia listy kolumn planszy.
     */
    public List<GameOfLifeColumn> getColumns() {
        return columns;
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
