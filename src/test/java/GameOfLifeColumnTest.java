import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
