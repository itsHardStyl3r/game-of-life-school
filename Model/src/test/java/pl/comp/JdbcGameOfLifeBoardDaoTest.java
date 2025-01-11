package pl.comp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.comp.storage.JdbcGameOfLifeBoardDao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JdbcGameOfLifeBoardDaoTest {
    private final String gameName = "g" + System.currentTimeMillis();
    private final PlainGameOfLifeSimulator simulator = new PlainGameOfLifeSimulator();
    private GameOfLifeBoard board;

    @BeforeEach
    void setUp() {
        int rowsCount = 5;
        int columnsCount = 5;
        board = new GameOfLifeBoard(rowsCount, columnsCount, simulator);
    }

    @Test
    public void testWriteAndRead() {
        try (JdbcGameOfLifeBoardDao dao = new JdbcGameOfLifeBoardDao(gameName, true)) {
            dao.write(board);

            GameOfLifeBoard readBoard = dao.read();
            for (int i = 0; i < board.getRows().size(); i++) {
                for (int j = 0; j < board.getColumns().size(); j++) {
                    assertEquals(board.get(i, j), readBoard.get(i, j));
                }
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
