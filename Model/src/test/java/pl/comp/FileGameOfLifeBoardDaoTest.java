package pl.comp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileGameOfLifeBoardDaoTest {
    private static final String TEST_FILE = "testBoard.txt";
    private final PlainGameOfLifeSimulator simulator = new PlainGameOfLifeSimulator();
    private GameOfLifeBoard board;
    private FileGameOfLifeBoardDao dao;

    @BeforeEach
    void setUp() {
        dao = new FileGameOfLifeBoardDao(TEST_FILE);
        int rowsCount = 5;
        int columnsCount = 5;
        board = new GameOfLifeBoard(rowsCount, columnsCount, simulator);
    }

    @AfterEach
    void tearDown() throws Exception {
        dao.close();
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testWriteAndRead() throws Exception {
        dao.write(board);
        GameOfLifeBoard readBoard = dao.read();
        assertNotNull(readBoard, "Odczytana plansza nie powinna być nullem");
        assertEquals(board, readBoard, "Zapisana i odczytana plansza powinny być takie same");
    }

    @Test
    void testFileCreatedAfterWrite() throws Exception {
        File file = new File(TEST_FILE);
        assertFalse(file.exists(), "Plik nie powinien istnieć przed zapisem");
        dao.write(board);
        assertTrue(file.exists(), "Plik powinien zostać utworzony po zapisie");
    }

    @Test
    void testFileContentPersistence() throws Exception {
        dao.write(board);
        dao.close();
        dao = new FileGameOfLifeBoardDao(TEST_FILE);
        GameOfLifeBoard readBoard = dao.read();
        assertEquals(board, readBoard, "Odczytana plansza powinna być identyczna z zapisaną");
    }
}
