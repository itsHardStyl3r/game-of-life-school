import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameOfLifeBoardTest {
    private GameOfLifeBoard board;
    private guPlainGameOfLifeSimulator simulator;

    /**
     * 5x5 - plansza testowa
     */
    @BeforeEach
    public void setUp() {
        simulator = new guPlainGameOfLifeSimulator();
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
        board.setBoard(initialBoard);
        board.doSimulationStep();
        boolean[][] expectedBoard = {
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
        };
        assertArrayEquals(expectedBoard, board.getBoard());
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
        board.setBoard(initialBoard);
        board.doSimulationStep();
        boolean[][] expectedBoard = {
                {false, false, false, false, false},
                {false, true, true, false, false},
                {false, true, true, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
        };
        assertArrayEquals(expectedBoard, board.getBoard());
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
        board.setBoard(initialBoard);
        board.doSimulationStep();
        boolean[][] expectedBoard = {
                {false, false, false, false, false},
                {false, true, true, false, false},
                {false, true, true, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
        };
        assertArrayEquals(expectedBoard, board.getBoard());
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
        board.setBoard(initialBoard);
        board.doSimulationStep();
        boolean[][] expectedBoard = {
                {false, true, false, false, false},
                {false, true, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, true, false, false, false},
        };
        assertArrayEquals(expectedBoard, board.getBoard());
    }

    /**
     * Sprawdza czy wiecej niż 3 sąsiadów zabija
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
        board.setBoard(initialBoard);
        board.doSimulationStep();
        boolean[][] expectedBoard = {
                {false, false, false, false, false},
                {false, true, true, true, false},
                {false, true, false, true, false},
                {false, true, true, true, false},
                {false, false, false, false, false},
        };
        assertArrayEquals(expectedBoard, board.getBoard());
    }

    /**
     * Sprawdza czy generowane są różne stany planszy
     */
    @Test
    public void testRandomBoard() {
        GameOfLifeBoard board1 = new GameOfLifeBoard(5, 5, simulator);
        GameOfLifeBoard board2 = new GameOfLifeBoard(5, 5, simulator);

        boolean[][] initialBoard1 = board1.getBoard();
        boolean[][] initialBoard2 = board2.getBoard();

        //Zakładamy, że plansze nie będą takie same
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
