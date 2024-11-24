package pl.comp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeBoardTest {
    private final int rowsCount = 5;
    private final int columnsCount = 5;
    private final PlainGameOfLifeSimulator simulator = new PlainGameOfLifeSimulator();
    private GameOfLifeBoard board;

    /**
     * 5x5 - plansza testowa
     */
    @BeforeEach
    public void setUp() {
        board = new GameOfLifeBoard(rowsCount, columnsCount, simulator);
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
     * Test sprawdzający niezawodność FixedSizeList.
     * Próba dodania kolejnego Row do listy o stałym rozmiarze - fail.
     * Próba ustawienia pierwszego Row na inny - pomyślnie.
     */
    @Test
    public void testSizeAndFixedSize() {
        assertEquals(board.getRows().size(), rowsCount);
        assertEquals(board.getColumns().size(), columnsCount);
        GameOfLifeRow row = new GameOfLifeRow(Arrays.asList(
                new GameOfLifeCell(false),
                new GameOfLifeCell(false),
                new GameOfLifeCell(true),
                new GameOfLifeCell(true),
                new GameOfLifeCell(false)
        ));

        GameOfLifeColumn column = new GameOfLifeColumn(Arrays.asList(
                new GameOfLifeCell(false),
                new GameOfLifeCell(false),
                new GameOfLifeCell(true),
                new GameOfLifeCell(true),
                new GameOfLifeCell(false)
        ));

        assertThrows(UnsupportedOperationException.class, () -> board.getRows().add(row));
        assertDoesNotThrow(() -> board.getRows().set(0, row)); // Dla błędu powinno być UnsupportedOperationException

        assertThrows(UnsupportedOperationException.class, () -> board.getColumns().add(column));
        assertDoesNotThrow(() -> board.getColumns().set(0, column));
    }

    /**
     * Test sprawdza konstruktor
     */
    @Test
    public void test_BadConstructor() {
        board = new GameOfLifeBoard(-1, -1, simulator);
        assertNotNull(board.getRow(0), "Tablica powinna posiadać przynajmniej jeden wiersz");
        assertNotNull(board.getColumn(0), "Tablica powinna posiadać przynajmniej jedną kolumnę");

        board = new GameOfLifeBoard(0, 0, simulator);
        assertNotNull(board.getRow(0), "Tablica powinna posiadać przynajmniej jeden wiersz");
        assertNotNull(board.getColumn(0), "Tablica powinna posiadać przynajmniej jedną kolumnę");
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
     * Test sprawdzający equals, hashcode i toString.
     * SuppressWarnings dla coverage dla equals():
     * - (this == obj)
     * - (getClass() != obj.getClass())
     */
    @SuppressWarnings({
            "EqualsWithItself",
            "SimplifiableAssertion",
            "EqualsBetweenInconvertibleTypes",
            "ConstantValue"})
    @Test
    void testEqualsAndHashCode() {
        boolean[][] initialBoard1 = {
                {false, false, false},
                {false, false, true},
                {false, true, true},
        };
        boolean[][] initialBoard2 = {
                {false, false, false},
                {true, true, true},
                {false, true, false},
        };
        GameOfLifeBoard board1 = new GameOfLifeBoard(3, 3, simulator);
        GameOfLifeBoard board2 = new GameOfLifeBoard(3, 3, simulator);
        GameOfLifeBoard board3 = new GameOfLifeBoard(3, 3, simulator);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board1.set(i, j, initialBoard1[i][j]);
                board2.set(i, j, initialBoard1[i][j]);
                board3.set(i, j, initialBoard2[i][j]);
            }
        }

        assertEquals(board1, board1);
        assertEquals(board2, board2);
        assertEquals(board3, board3);
        assertEquals(board1.toString(), board1.toString());
        assertEquals(board2.toString(), board2.toString());
        assertEquals(board3.toString(), board3.toString());

        assertEquals(board1, board2);
        assertNotEquals(board1, board3);
        assertEquals(board1.hashCode(), board2.hashCode());
        assertNotEquals(board1.hashCode(), board3.hashCode());

        assertEquals(board1.toString(), board2.toString());
        assertNotEquals(board1.toString(), board3.toString());

        assertFalse(board1.equals("ABC"));
        assertFalse(board1.equals(null));
        assertFalse(board2.equals("ABC"));
        assertFalse(board2.equals(null));
        assertFalse(board3.equals("ABC"));
        assertFalse(board3.equals(null));
    }
}

