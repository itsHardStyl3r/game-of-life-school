import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameOfLifeRowTest {
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
        GameOfLifeRow row = new GameOfLifeRow(List.of(cells));

        assertEquals(2, row.countAliveCells(), "Liczba żywych komórek powinna wynosić 2.");
    }
}
