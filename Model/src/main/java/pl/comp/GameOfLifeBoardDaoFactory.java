package pl.comp;

public class GameOfLifeBoardDaoFactory {
    public static Dao<GameOfLifeBoard> createFileGameOfLifeBoardDao(String fileName) {
        return new FileGameOfLifeBoardDao(fileName);
    }
}
