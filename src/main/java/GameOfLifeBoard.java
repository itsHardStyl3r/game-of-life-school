import java.util.Random;

public class GameOfLifeBoard {
    private final int rows;
    private final int cols;
    private final GameOfLifeCell[][] board;
    private final PlainGameOfLifeSimulator simulator;

    /**
     * Konstruktor klasy, który przyjmuje wymiary planszy i losowo inicjalizuje stany komórek.
     *
     * @param rows      Liczba wierszy planszy.
     * @param cols      Liczba kolumn planszy.
     */
    public GameOfLifeBoard(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.simulator = new PlainGameOfLifeSimulator();
        this.board = new GameOfLifeCell[rows][cols];
        initializeBoard();
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
     * @param row Rząd komórki
     * @param col Kolumna komórki
     * @param isAlive  True — jeżeli komórka ma żyć, false, jeżeli nie
     */
    public void set(int row, int col, boolean isAlive) {
        board[row][col] = new GameOfLifeCell(isAlive);
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
        simulator.doStep(this);    }
}