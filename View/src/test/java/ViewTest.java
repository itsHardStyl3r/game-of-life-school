import javafx.beans.property.adapter.JavaBeanBooleanProperty;
import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import org.junit.jupiter.api.Test;
import pl.comp.Density;
import pl.comp.GameOfLifeBoard;
import pl.comp.GameOfLifeCell;
import pl.comp.PlainGameOfLifeSimulator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ViewTest {

    @Test
    public void testBidirectional() {
        int size = 5;
        GameOfLifeBoard gameBoard = new GameOfLifeBoard(size, size, new PlainGameOfLifeSimulator(), Density.FULL);
        for (int i = 0; i < gameBoard.getRows().size(); i++) {
            for (int j = 0; j < gameBoard.getColumns().size(); j++) {
                GameOfLifeCell cell = gameBoard.getCell(i, j);
                try {
                    JavaBeanBooleanProperty cellValueProperty = JavaBeanBooleanPropertyBuilder.create()
                            .bean(cell)
                            .name("cellValue")
                            .getter("getCellValue")
                            .setter("updateState")
                            .build();
                    assertEquals(cell.getCellValue(), cellValueProperty.get());

                    // Zmiana stanu "z kodu"
                    cell.updateState(!cell.getCellValue());
                    assertEquals(cell.getCellValue(), cellValueProperty.get());

                    // Zmiana stanu z JavaBeanBooleanProperty
                    cellValueProperty.set(!cell.getCellValue());
                    assertEquals(cell.getCellValue(), cellValueProperty.get());
                } catch (NoSuchMethodException e) {
                    fail(e);
                }
            }
        }
    }
}
