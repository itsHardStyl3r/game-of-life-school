package pl.comp.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.comp.GameOfLifeBoard;
import pl.comp.exceptions.DaoReadException;
import pl.comp.exceptions.DaoWriteException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static pl.comp.GameOfLifeBoard.getLocaleMessage;

public class FileGameOfLifeBoardDao implements Dao<GameOfLifeBoard>, AutoCloseable {
    private final String fileName;
    private final Logger logger = LoggerFactory.getLogger(FileGameOfLifeBoardDao.class);
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public FileGameOfLifeBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public GameOfLifeBoard read() {
        try {
            inputStream = new ObjectInputStream(new FileInputStream(fileName));
            return (GameOfLifeBoard) inputStream.readUnshared();
        } catch (Exception e) {
            logger.error(getLocaleMessage("daoException"), e.toString());
            close();
            throw new DaoReadException();
        }
    }

    @Override
    public void write(GameOfLifeBoard board) {
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
            outputStream.writeUnshared(board);
        } catch (Exception e) {
            logger.error(getLocaleMessage("daoException"), e.toString());
            close();
            throw new DaoWriteException();
        }
    }

    @Override
    public void close() {
        try {
            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }
            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }
        } catch (Exception e) {
            logger.error(getLocaleMessage("daoException"), e.toString());
        }
    }
}
