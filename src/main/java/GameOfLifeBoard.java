import java.util.Random;

public class GameOfLifeBoard {
    private final int rows;
    private final int cols;
    private final GameOfLifeCell[][] board;
    private final PlainGameOfLifeSimulator simulator;

    /**
     * Konstruktor klasy, który przyjmuje wymiary planszy i losowo inicjalizuje stany komórek.
     *
     * @param rows Liczba wierszy planszy.
     * @param cols Liczba kolumn planszy.
     */
    public GameOfLifeBoard(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.simulator = new PlainGameOfLifeSimulator();
        this.board = new GameOfLifeCell[rows][cols];
        initializeBoard();
        setNeighborsForCells();
    }

    /**
     * Inicjalizuje planszę losowymi wartościami (0 lub 1).
     */
    private void initializeBoard() {
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = new GameOfLifeCell(random.nextBoolean());
            }
        }
    }

    private void setNeighborsForCells() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                GameOfLifeCell[] neighbors = new GameOfLifeCell[8];
                int index = 0;

                int[][] neighborPositions = {
                        {i - 1, j - 1}, {i - 1, j}, {i - 1, j + 1},
                        {i, j - 1},                 {i, j + 1},
                        {i + 1, j - 1}, {i + 1, j}, {i + 1, j + 1}
                };

                for (int[] pos : neighborPositions) {
                    int x = pos[0];
                    int y = pos[1];
                    if (x >= 0 && x < rows && y >= 0 && y < cols) {
                        neighbors[index++] = board[x][y];
                    }
                }

                board[i][j].setNeighbors(neighbors);
            }
        }
    }

    /**
     * Ustawia stan komórki w danym miejscu.
     *
     * @param row Rząd komórki
     * @param col Kolumna komórki
     */
    public GameOfLifeCell get(int row, int col) {
        return board[row][col];
    }

    /**
     * Ustawia stan komórki w danym miejscu.
     *
     * @param row     Rząd komórki
     * @param col     Kolumna komórki
     * @param isAlive True — jeżeli komórka ma żyć, false, jeżeli nie
     */
    public void set(int row, int col, boolean isAlive) {
        board[row][col] = new GameOfLifeCell(isAlive);
        setNeighborsForCells();
    }

    public GameOfLifeRow getRow(int y) {
        GameOfLifeCell[] rowCells = new GameOfLifeCell[cols];
        System.arraycopy(board[y], 0, rowCells, 0, cols);
        return new GameOfLifeRow(rowCells);
    }

    public GameOfLifeColumn getCol(int x) {
        GameOfLifeCell[] colCells = new GameOfLifeCell[rows];
        for (int i = 0; i < rows; i++) {
            colCells[i] = board[i][x];
        }
        return new GameOfLifeColumn(colCells);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    /**
     * Wykonuje krok symulacji w dostępnym symulatorze.
     */
    public void doSimulationStep() {
        simulator.doStep(this);
    }
}