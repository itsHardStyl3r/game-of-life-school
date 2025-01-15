package pl.comp;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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

    @Test
    public void testComparing() {
        GameOfLifeCell[] cells = {
                new GameOfLifeCell(true),
                new GameOfLifeCell(false),
                new GameOfLifeCell(true),
        };
        GameOfLifeColumn column = new GameOfLifeColumn(List.of(cells));
        assertEquals(column, column);
        assertFalse(column.equals("ABC"));
        assertFalse(column.equals(null));
    }
}
