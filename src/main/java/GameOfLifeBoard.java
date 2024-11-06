import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

/**
 * Klasa reprezentująca planszę do gry w życie.
 */
public class GameOfLifeBoard {
    private final GameOfLifeCell[][] board;
    private final GameOfLifeRow[] rows;
    private final GameOfLifeColumn[] columns;
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
        this.rows = new GameOfLifeRow[this.rowsCount];
        this.columns = new GameOfLifeColumn[this.colsCount];
        this.simulator = simulator;
        initializeBoard();
    }

    /**
     * Inicjalizuje planszę losowymi wartościami komórek oraz
     * tworzy obiekty {@link GameOfLifeRow} i {@link GameOfLifeColumn} dla każdego wiersza i kolumny.
     */
    private void initializeBoard() {
        Random random = new Random();
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < colsCount; j++) {
                board[i][j] = new GameOfLifeCell(random.nextBoolean());
            }
            rows[i] = new GameOfLifeRow(board[i]);
        }
        for (int j = 0; j < colsCount; j++) {
            GameOfLifeCell[] column = new GameOfLifeCell[rowsCount];
            for (int i = 0; i < rowsCount; i++) {
                column[i] = board[i][j];
            }
            columns[j] = new GameOfLifeColumn(column);
        }
        linkNeighbors();
    }

    /**
     * Łączy sąsiadujące komórki z każdą komórką w planszy, umożliwiając im dostęp do swoich sąsiadów.
     */
    private void linkNeighbors() {
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < colsCount; j++) {
                GameOfLifeCell[] neighbors = new GameOfLifeCell[8];
                int idx = 0;
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        if (x == 0 && y == 0) {
                            continue;
                        }
                        int ni = (i + x + rowsCount) % rowsCount;
                        int nj = (j + y + colsCount) % colsCount;
                        neighbors[idx++] = board[ni][nj];
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
     * @param x Indeks wiersza.
     * @param y Indeks kolumny.
     * @param state Nowy stan komórki (true — żywa, false — martwa).
     */
    public void set(int x, int y, boolean state) {
        board[x][y].updateState(state);
        rows[x] = new GameOfLifeRow(board[x]);
        GameOfLifeCell[] column = new GameOfLifeCell[rowsCount];
        for (int i = 0; i < rowsCount; i++) {
            column[i] = board[i][y];
        }
        columns[y] = new GameOfLifeColumn(column);
    }

    /**
     * Zwraca kopię tablicy obiektów {@link GameOfLifeRow}.
     *
     * @return Kopia tablicy rzędów planszy.
     */
    public GameOfLifeRow[] getRows() {
        return Arrays.copyOf(rows, rows.length);
    }

    /**
     * Zwraca kopię tablicy obiektów {@link GameOfLifeColumn}.
     *
     * @return Kopia tablicy kolumn planszy.
     */
    public GameOfLifeColumn[] getColumns() {
        return Arrays.copyOf(columns, columns.length);
    }

    /**
     * Wykonuje krok symulacji w dostępnej symulacji.
     */
    public void doSimulationStep() {
        simulator.doStep(this);
    }
}
