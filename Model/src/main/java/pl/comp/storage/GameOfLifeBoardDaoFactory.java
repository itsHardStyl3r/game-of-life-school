package pl.comp.storage;

import pl.comp.GameOfLifeBoard;

public class GameOfLifeBoardDaoFactory {
    public static Dao<GameOfLifeBoard> createFileGameOfLifeBoardDao(String fileName) {
        return new FileGameOfLifeBoardDao(fileName);
    }

    public static Dao<GameOfLifeBoard> createDatabaseGameOfLifeBoardDao(String gameName) {
        return new JdbcGameOfLifeBoardDao(gameName, false);
    }
}
