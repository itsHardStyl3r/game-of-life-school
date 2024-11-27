package pl.comp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileGameOfLifeBoardDao implements Dao<GameOfLifeBoard>, AutoCloseable {
    private final String fileName;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public FileGameOfLifeBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public GameOfLifeBoard read() throws Exception {
        inputStream = new ObjectInputStream(new FileInputStream(fileName));
        return (GameOfLifeBoard) inputStream.readObject();
    }

    @Override
    public void write(GameOfLifeBoard board) throws Exception {
        outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
        outputStream.writeObject(board);
    }

    @Override
    public void close() throws Exception {
        if (outputStream != null) {
            outputStream.close();
            outputStream = null;
        }
        if (inputStream != null) {
            inputStream.close();
            inputStream = null;
        }
    }
}
