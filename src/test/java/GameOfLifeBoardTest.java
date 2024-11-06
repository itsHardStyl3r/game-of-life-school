import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

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

    /**
     * Testuje metodę countAliveCells(), sprawdzając czy liczba żywych komórek jest poprawna.
     */
    @Test
    public void testCountAliveCells() {
        GameOfLifeCell[] cells = {
                new GameOfLifeCell(true),
                new GameOfLifeCell(false),
                new GameOfLifeCell(true)
        };
        GameOfLifeRow row = new GameOfLifeRow(cells);

        assertEquals(2, row.countAliveCells(), "Liczba żywych komórek powinna wynosić 2.");
    }

    /**
     * Sprwadza czy liczba martwych komórek jest poprawna.
     */
    @Test
    public void testCountDeadCells() {
        GameOfLifeCell[] cells = {
                new GameOfLifeCell(true),
                new GameOfLifeCell(false),
                new GameOfLifeCell(true),
                new GameOfLifeCell(false)
        };
        GameOfLifeColumn column = new GameOfLifeColumn(cells);

        assertEquals(2, column.countDeadCells(), "Liczba martwych komórek powinna wynosić 2.");
    }

    /**
     * Sprawdza warunek neighbor != null && neighbor.getCellValue()
     */
    @Test
    public void testCountLivingNeighbors() throws Exception {
        GameOfLifeCell cell = new GameOfLifeCell(false);
        GameOfLifeCell[] neighbors = {
                new GameOfLifeCell(true),
                null,
                new GameOfLifeCell(true),
                new GameOfLifeCell(false),
                null,
                new GameOfLifeCell(true),
                new GameOfLifeCell(false),
                new GameOfLifeCell(false)
        };
        cell.setNeighbors(neighbors);
        Method countLivingNeighborsMethod = GameOfLifeCell.class.getDeclaredMethod("countLivingNeighbors");
        countLivingNeighborsMethod.setAccessible(true);
        int livingNeighbors = (int) countLivingNeighborsMethod.invoke(cell);
        assertEquals(3, livingNeighbors, "Liczba żywych sąsiadów powinna wynosić 3.");
    }

    /**
     * Test sprawdza, czy po ustawieniu sąsiadów ich liczba jest równa 8.
     */
    @Test
    public void testNeighbors() {
        GameOfLifeCell cell = new GameOfLifeCell(false);
        GameOfLifeCell[] neighbors = new GameOfLifeCell[8];

        for (int i = 0; i < neighbors.length; i++) {
            neighbors[i] = new GameOfLifeCell(i % 2 == 0); // Na zmianę: żywa lub martwa komórka
        }

        cell.setNeighbors(neighbors);
        assertEquals(8, neighbors.length, "Tablica sąsiadów powinna mieć dokładnie 8 elementów.");
    }

    /**
     * Test sprawdza, czy po ustawieniu sąsiadów ich liczba jest równa 8.
     */
    @Test
    public void testArrayLength() throws Exception {
        GameOfLifeCell cell = new GameOfLifeCell(false);

        GameOfLifeCell[] invalidNeighbors = new GameOfLifeCell[7];
        for (int i = 0; i < invalidNeighbors.length; i++) {
            invalidNeighbors[i] = new GameOfLifeCell(false);
        }

        cell.setNeighbors(invalidNeighbors);

        Field neighborsField = GameOfLifeCell.class.getDeclaredField("neighbors");
        neighborsField.setAccessible(true);
        GameOfLifeCell[] neighborsValue = (GameOfLifeCell[]) neighborsField.get(cell);

        assertTrue(neighborsValue == null || Arrays.stream(neighborsValue).allMatch(Objects::isNull),
                "Sąsiedzi nieustawieni, jeśli tablica nie ma 8 elementów.");

        GameOfLifeCell[] validNeighbors = new GameOfLifeCell[8];
        for (int i = 0; i < validNeighbors.length; i++) {
            validNeighbors[i] = new GameOfLifeCell(false);
        }

        cell.setNeighbors(validNeighbors);
        neighborsValue = (GameOfLifeCell[]) neighborsField.get(cell);

        assertNotNull(neighborsValue, "Sąsiedzi ustawieni, tablica ma dokładnie 8 elementów");
        assertEquals(8, neighborsValue.length, "Tablica sąsiadów powinna mieć 8 elementów");
    }
}