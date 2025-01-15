package pl.comp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.comp.storage.Dao;
import pl.comp.storage.GameOfLifeBoardDaoFactory;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileGameOfLifeBoardDaoTest {
    private static final String TEST_FILE = "board" + System.currentTimeMillis() + ".txt";
    private final PlainGameOfLifeSimulator simulator = new PlainGameOfLifeSimulator();
    private final File file = new File(TEST_FILE);
    private GameOfLifeBoard board;

    @BeforeEach
    void setUp() {
        int rowsCount = 5;
        int columnsCount = 5;
        board = new GameOfLifeBoard(rowsCount, columnsCount, simulator);
    }

    @AfterEach
    void tearDown() {
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testWriteAndRead() {
        GameOfLifeBoard readBoard = null;
        try (Dao<GameOfLifeBoard> dao = GameOfLifeBoardDaoFactory.createFileGameOfLifeBoardDao(TEST_FILE)) {
            dao.write(board);
            readBoard = dao.read();
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertNotNull(readBoard, "Odczytana plansza nie powinna być nullem");
        assertEquals(board, readBoard, "Zapisana i odczytana plansza powinny być takie same");
    }

    @Test
    void testFileCreatedAfterWrite() {
        File file = new File(TEST_FILE);
        assertFalse(file.exists(), "Plik nie powinien istnieć przed zapisem");
        try (Dao<GameOfLifeBoard> dao = GameOfLifeBoardDaoFactory.createFileGameOfLifeBoardDao(TEST_FILE)) {
            dao.write(board);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertTrue(file.exists(), "Plik powinien zostać utworzony po zapisie");
    }

    @Test
    void testFileContentPersistence() {
        try (Dao<GameOfLifeBoard> dao = GameOfLifeBoardDaoFactory.createFileGameOfLifeBoardDao(TEST_FILE)) {
            dao.write(board);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        GameOfLifeBoard readBoard = null;
        try (Dao<GameOfLifeBoard> dao = GameOfLifeBoardDaoFactory.createFileGameOfLifeBoardDao(TEST_FILE)) {
            readBoard = dao.read();
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(board, readBoard, "Odczytana plansza powinna być identyczna z zapisaną");
    }

    @SuppressWarnings("InstantiationOfUtilityClass")
    @Test
    void testGameOfLifeBoardDaoFactoryConstructor() {
        assertDoesNotThrow(() -> {
            new GameOfLifeBoardDaoFactory();
        });
    }
}
