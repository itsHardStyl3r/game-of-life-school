import java.util.Arrays;
import java.util.Random;

public class GameOfLifeBoard {
    private final int rows;
    private final int cols;
    private final Random random = new Random();
    private final boolean[][] board;
    private final GameOfLifeSimulator simulator;

    /**
     * Konstruktor klasy, który przyjmuje wymiary planszy i losowo inicjalizuje stany komórek.
     *
     * @param rows      Liczba wierszy planszy.
     * @param cols      Liczba kolumn planszy.
     * @param simulator Obiekt symulatora, który definiuje logikę ewolucji planszy w czasie.
     */
    public GameOfLifeBoard(int rows, int cols, GameOfLifeSimulator simulator) {
        this.rows = rows;
        this.cols = cols;
        this.simulator = simulator;
        this.board = new boolean[rows][cols];
        initializeBoard();
    }

    /**
     * Inicjalizuje planszę losowymi wartościami (0 lub 1).
     */
    private void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = random.nextBoolean(); // 0 - martwa, 1 - żywa
            }
        }
    }

    /**
     * Zwraca kopię planszy, aby nie narażać jej na modyfikacje z zewnątrz.
     *
     * @return Kopia planszy jako tablica dwuwymiarowa.
     */
    public boolean[][] getBoard() {
        boolean[][] boardCopy = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            boardCopy[i] = Arrays.copyOf(board[i], cols);
        }
        return boardCopy;
    }

    /**
     * Ustawia planszę na podaną tablicę, przyjmuje kopię wejściowej tablicy, aby uniknąć bezpośrednich modyfikacji.
     *
     * @param newBoard Nowa plansza jako tablica dwuwymiarowa.
     */
    public void setBoard(boolean[][] newBoard) {
        for (int i = 0; i < rows; i++) {
            board[i] = Arrays.copyOf(newBoard[i], cols);
        }
    }

    public void doSimulationStep() {
        simulator.doStep(this);
    }
}