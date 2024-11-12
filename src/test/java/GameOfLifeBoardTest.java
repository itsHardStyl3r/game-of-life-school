import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeBoardTest {
    private GameOfLifeBoard board;

    /**
     * 5x5 - plansza testowa
     */
    @BeforeEach
    public void setUp() {
        PlainGameOfLifeSimulator simulator = new PlainGameOfLifeSimulator();
        board = new GameOfLifeBoard(5, 5, simulator);
    }

    /**
     * Sprawdza czy 1 umiera bez sąsiadów
     */
    @Test
    public void testDoStep_OneDies() {
        boolean[][] initialBoard = {
                {false, false, false, false, false},
                {false, true, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
        };

        // Ustawiamy planszę
        for (int i = 0; i < initialBoard.length; i++) {
            for (int j = 0; j < initialBoard[i].length; j++) {
                board.set(i, j, initialBoard[i][j]);
            }
        }

        board.doSimulationStep();

        boolean[][] expectedBoard = {
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
        };

        // Sprawdzamy, czy plansza jest zgodna z oczekiwaniami
        for (int i = 0; i < expectedBoard.length; i++) {
            for (int j = 0; j < expectedBoard[i].length; j++) {
                assertEquals(expectedBoard[i][j], board.get(i, j), String.format("Wartość w (%d, %d) powinna być %b", i, j, expectedBoard[i][j]));
            }
        }
    }

    /**
     * Sprawdza czy trzech przeżyje, jeśli ze sobą sąsiadują
     */
    @Test
    public void testDoStep_ThreeNeighborsSurvives() {
        boolean[][] initialBoard = {
                {false, false, false, false, false},
                {false, true, true, false, false},
                {false, true, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
        };

        // Ustawiamy planszę
        for (int i = 0; i < initialBoard.length; i++) {
            for (int j = 0; j < initialBoard[i].length; j++) {
                board.set(i, j, initialBoard[i][j]);
            }
        }

        board.doSimulationStep();

        boolean[][] expectedBoard = {
                {false, false, false, false, false},
                {false, true, true, false, false},
                {false, true, true, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
        };

        // Sprawdzamy, czy plansza jest zgodna z oczekiwaniami
        for (int i = 0; i < expectedBoard.length; i++) {
            for (int j = 0; j < expectedBoard[i].length; j++) {
                assertEquals(expectedBoard[i][j], board.get(i, j), String.format("Wartość w (%d, %d) powinna być %b", i, j, expectedBoard[i][j]));
            }
        }
    }

    /**
     * Sprawdza czy odżywi pole z trzema żywmi sąsiadami
     */
    @Test
    public void testDoStep_ThreeNeighborsBecomesAlive() {
        boolean[][] initialBoard = {
                {false, false, false, false, false},
                {false, true, true, false, false},
                {false, false, true, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
        };

        // Ustawiamy planszę
        for (int i = 0; i < initialBoard.length; i++) {
            for (int j = 0; j < initialBoard[i].length; j++) {
                board.set(i, j, initialBoard[i][j]);
            }
        }

        board.doSimulationStep();

        boolean[][] expectedBoard = {
                {false, false, false, false, false},
                {false, true, true, false, false},
                {false, true, true, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
        };

        // Sprawdzamy, czy plansza jest zgodna z oczekiwaniami
        for (int i = 0; i < expectedBoard.length; i++) {
            for (int j = 0; j < expectedBoard[i].length; j++) {
                assertEquals(expectedBoard[i][j], board.get(i, j), String.format("Wartość w (%d, %d) powinna być %b", i, j, expectedBoard[i][j]));
            }
        }
    }

    /**
     * Sprawdza prawidłowość zachowania przy zawijaniu
     */
    @Test
    public void testDoStep_CellsAtEdges() {
        boolean[][] initialBoard = {
                {true, true, true, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
        };

        // Ustawiamy planszę
        for (int i = 0; i < initialBoard.length; i++) {
            for (int j = 0; j < initialBoard[i].length; j++) {
                board.set(i, j, initialBoard[i][j]);
            }
        }

        board.doSimulationStep();

        boolean[][] expectedBoard = {
                {false, true, false, false, false},
                {false, true, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, true, false, false, false},
        };

        // Sprawdzamy, czy plansza jest zgodna z oczekiwaniami
        for (int i = 0; i < expectedBoard.length; i++) {
            for (int j = 0; j < expectedBoard[i].length; j++) {
                assertEquals(expectedBoard[i][j], board.get(i, j), String.format("Wartość w (%d, %d) powinna być %b", i, j, expectedBoard[i][j]));
            }
        }
    }

    /**
     * Sprawdza czy więcej niż 3 sąsiadów zabija
     */
    @Test
    public void testDoStep_MoreThanThreeDies() {
        boolean[][] initialBoard = {
                {false, false, false, false, false},
                {false, false, true, false, false},
                {false, true, true, true, false},
                {false, false, true, false, false},
                {false, false, false, false, false},
        };

        // Ustawiamy planszę
        for (int i = 0; i < initialBoard.length; i++) {
            for (int j = 0; j < initialBoard[i].length; j++) {
                board.set(i, j, initialBoard[i][j]);
            }
        }

        board.doSimulationStep();

        boolean[][] expectedBoard = {
                {false, false, false, false, false},
                {false, true, true, true, false},
                {false, true, false, true, false},
                {false, true, true, true, false},
                {false, false, false, false, false},
        };

        // Sprawdzamy, czy plansza jest zgodna z oczekiwaniami
        for (int i = 0; i < expectedBoard.length; i++) {
            for (int j = 0; j < expectedBoard[i].length; j++) {
                assertEquals(expectedBoard[i][j], board.get(i, j), String.format("Wartość w (%d, %d) powinna być %b", i, j, expectedBoard[i][j]));
            }
        }
    }

}
