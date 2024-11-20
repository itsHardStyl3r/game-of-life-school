import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GameOfLifeColumnTest {
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
        GameOfLifeColumn column = new GameOfLifeColumn(List.of(cells));
        assertEquals(2, column.countDeadCells(), "Liczba martwych komórek powinna wynosić 2.");
    }

    @Test
    public void testComparing(){
        GameOfLifeCell[] cells = {
                new GameOfLifeCell(true),
                new GameOfLifeCell(false),
                new GameOfLifeCell(true),
        };
        GameOfLifeRow row = new GameOfLifeRow(List.of(cells));
        assertEquals(row, row);
        assertFalse(row.equals("ABC"));
        assertFalse(row.equals(null));
    }
}
