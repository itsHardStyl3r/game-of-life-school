import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameOfLifeBoardInvalidTest {
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
}
