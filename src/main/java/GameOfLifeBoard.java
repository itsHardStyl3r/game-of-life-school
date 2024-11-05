import java.util.Random;

public class GameOfLifeBoard {
    private final GameOfLifeCell[][] board;
    private final GameOfLifeRow[] rows;
    private final GameOfLifeColumn[] columns;
    private final int rowsCount;
    private final int colsCount;
    private final GameOfLifeSimulator simulator;

    private boolean filled = false;

    public GameOfLifeBoard(int rowsCount, int colsCount, GameOfLifeSimulator simulator) {
        this.rowsCount = rowsCount;
        this.colsCount = colsCount;
        this.board = new GameOfLifeCell[rowsCount][colsCount];
        this.rows = new GameOfLifeRow[rowsCount];
        this.columns = new GameOfLifeColumn[colsCount];
        this.simulator = simulator;

        initializeBoard(null);
    }

    public GameOfLifeBoard(boolean[][] initial, GameOfLifeSimulator simulator) {
        this.rowsCount = initial.length;
        this.colsCount = initial[0].length;
        this.board = new GameOfLifeCell[rowsCount][colsCount];
        this.rows = new GameOfLifeRow[rowsCount];
        this.columns = new GameOfLifeColumn[colsCount];
        this.simulator = simulator;

        this.filled = true;
        initializeBoard(initial);
    }

    /**
     * Inicjalizuje planszę odpowiednio do initial lub losowo.
     */
    private void initializeBoard(boolean[][] initial) {
        Random random = new Random();
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < colsCount; j++) {
                board[i][j] = new GameOfLifeCell(filled ? initial[i][j] : random.nextBoolean());
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

    public boolean get(int x, int y) {
        return board[x][y].isAlive();
    }

    public void set(int x, int y, boolean isAlive) {
        board[x][y].setAlive(isAlive);
        rows[x] = new GameOfLifeRow(board[x]);
        GameOfLifeCell[] column = new GameOfLifeCell[rowsCount];
        for (int i = 0; i < rowsCount; i++) {
            column[i] = board[i][y];
        }
        columns[y] = new GameOfLifeColumn(column);
    }

    public GameOfLifeRow[] getRows() {
        return rows;
    }

    public GameOfLifeColumn[] getColumns() {
        return columns;
    }

    /**
     * Wykonuje krok symulacji w dostępnym symulatorze.
     */
    public void doSimulationStep() {
        simulator.doStep(this);
    }
}