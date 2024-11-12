import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeRandomBoardTest {
    private PlainGameOfLifeSimulator simulator;

    /**
     * 5x5 - plansza testowa
     */
    @BeforeEach
    public void setUp() {
        simulator = new PlainGameOfLifeSimulator();
        new GameOfLifeBoard(5, 5, simulator);
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
