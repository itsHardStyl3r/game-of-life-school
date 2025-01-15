package pl.comp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.comp.storage.Dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static pl.comp.storage.GameOfLifeBoardDaoFactory.createDatabaseGameOfLifeBoardDao;

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
        try (Dao<GameOfLifeBoard> dao = createDatabaseGameOfLifeBoardDao(gameName, true)) {
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
