package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Klasa z testami klasy {@link GameOfLifeBoard}.
 */
public class GameOfLifeBoardTest {

    /**
     * Test samotności — komórka bez sąsiadów powinna umrzeć.
     */
    @Test
    public void testSamotnosci() {
        boolean[][] boardInitial = {
                {false, false, false},
                {false, true, false},
                {false, false, false}
        };
        boolean[][] boardFirst = {
                {false, false, false},
                {false, false, false},
                {false, false, false}
        };

        GameOfLifeBoard board = new GameOfLifeBoard(boardInitial);
        board.doStep();
        assertArrayEquals(board.getBoard(), boardFirst);
    }

    /**
     * Test ożycia — komórka z trzema sąsiadami powinna żyć.
     */
    @Test
    public void testRegulyOzycia() {
        boolean[][] boardInitial = {
                {false, false, false, false},
                {false, true, true, false},
                {false, false, true, false},
                {false, false, false, false}
        };
        boolean[][] boardFirst = {
                {false, false, false, false},
                {false, true, true, false},
                {false, true, true, false},
                {false, false, false, false}
        };

        GameOfLifeBoard board = new GameOfLifeBoard(boardInitial);
        board.doStep();
        assertArrayEquals(board.getBoard(), boardFirst);
    }

    /**
     * Test niezmiennej konstrukcji klocka.
     */
    @Test
    public void testKlocek() {
        boolean[][] boardInitial = {
                {false, false, false, false},
                {false, true, true, false},
                {false, true, true, false},
                {false, false, false, false}
        };
        boolean[][] boardFirst = {
                {false, false, false, false},
                {false, true, true, false},
                {false, true, true, false},
                {false, false, false, false}
        };

        GameOfLifeBoard board = new GameOfLifeBoard(boardInitial);
        board.doStep();
        assertArrayEquals(board.getBoard(), boardFirst);
    }

    /**
     * Test zarówno konstrukcji Migacz (Blinker), jak i przechodzenia przez krawędzie.
     */
    @Test
    public void testMigaczKrawedzie() {
        boolean[][] boardInitial = {
                {false, true, true, true, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false}
        };
        boolean[][] boardFirst = {
                {false, false, true, false, false},
                {false, false, true, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, true, false, false}
        };

        GameOfLifeBoard board = new GameOfLifeBoard(boardInitial);
        board.doStep();
        assertArrayEquals(board.getBoard(), boardFirst);
    }

    /**
     * Sprawdzamy, czy kolejne wywołanie konstruktora da inną planszę.
     */
    @Test
    public void testKolejneWywolania() {
        GameOfLifeBoard board1 = new GameOfLifeBoard(10, 10);
        GameOfLifeBoard board2 = new GameOfLifeBoard(10, 10);

        assertNotEquals(board1.getBoard(), board2.getBoard());
    }

    /**
     * Test pustej planszy — powinna pozostać pusta.
     */
    @Test
    public void testEmptyBoard() {
        boolean[][] boardInitial = {
                {false, false, false},
                {false, false, false},
                {false, false, false}
        };
        boolean[][] boardFirst = {
                {false, false, false},
                {false, false, false},
                {false, false, false}
        };

        GameOfLifeBoard board = new GameOfLifeBoard(boardInitial);
        board.doStep();
        assertArrayEquals(board.getBoard(), boardFirst);
    }

    /**
     * Test pełnej planszy — wszystkie komórki umierają z przeludnienia.
     */
    @Test
    public void testFullBoard() {
        boolean[][] boardInitial = {
                {true, true, true},
                {true, true, true},
                {true, true, true}
        };
        boolean[][] boardFirst = {
                {false, false, false},
                {false, false, false},
                {false, false, false}
        };

        GameOfLifeBoard board = new GameOfLifeBoard(boardInitial);
        board.doStep();
        assertArrayEquals(board.getBoard(), boardFirst);
    }
}
