import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameOfLifeBoardTest {
    private GameOfLifeBoard board;
    private PlainGameOfLifeSimulator simulator;

    /**
     * 5x5 - plansza testowa
     */
    @BeforeEach
    public void setUp() {
        simulator = new PlainGameOfLifeSimulator();
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

        board = new GameOfLifeBoard(initialBoard, simulator);

        board.doSimulationStep();

        boolean[][] expectedBoard = {
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
        };

        for (int i = 0; i < expectedBoard.length; i++) {
            for (int j = 0; j < expectedBoard[i].length; j++) {
                assertEquals(expectedBoard[i][j], board.get(i, j));
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

    /**
     * Sprawdza czy generowane są różne stany planszy
     */
    @Test
    public void testRandomBoard() {
        GameOfLifeBoard board1 = new GameOfLifeBoard(5, 5, simulator);
        GameOfLifeBoard board2 = new GameOfLifeBoard(5, 5, simulator);

        boolean[][] initialBoard1 = new boolean[5][5];
        boolean[][] initialBoard2 = new boolean[5][5];

        // Uzupełniamy tablice do sprawdzenia różnic
        for (int i = 0; i < initialBoard1.length; i++) {
            for (int j = 0; j < initialBoard1[i].length; j++) {
                initialBoard1[i][j] = board1.get(i, j);
                initialBoard2[i][j] = board2.get(i, j);
            }
        }

        // Zakładamy, że plansze nie będą takie same
        boolean areBoardsDifferent = false;
        for (int i = 0; i < initialBoard1.length; i++) {
            if (!Arrays.equals(initialBoard1[i], initialBoard2[i])) {
                areBoardsDifferent = true;
                break;
            }
        }

        assertTrue(areBoardsDifferent, "Dwa wywołania powinny dać inny stan");
    }
}