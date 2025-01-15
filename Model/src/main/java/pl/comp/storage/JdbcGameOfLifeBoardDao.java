package pl.comp.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.comp.GameOfLifeBoard;
import pl.comp.PlainGameOfLifeSimulator;
import pl.comp.exceptions.DaoReadException;
import pl.comp.exceptions.DaoWriteException;
import pl.comp.exceptions.UnknownGameException;

import java.sql.*;

import static pl.comp.GameOfLifeBoard.getLocaleMessage;

public class JdbcGameOfLifeBoardDao implements Dao<GameOfLifeBoard>, AutoCloseable {
    private final Logger logger = LoggerFactory.getLogger(JdbcGameOfLifeBoardDao.class);
    private final String gameName;
    private final String dbName = "admin";
    private final String dbPassword = "admin";
    private final String dbUrl;

    /**
     * A constructor of DAO that supports writing to H2 database.
     *
     * @param gameName A name of game.
     * @param inMemory If true, in-memory database will be used (no file creation). Good for tests. There is 10 seconds
     *                 delay for in-memory option.
     * @throws UnknownGameException   When no game name is specified.
     */
    public JdbcGameOfLifeBoardDao(String gameName, boolean inMemory) {
        if (gameName == null || gameName.isEmpty()) {
            throw new UnknownGameException();
        }
        this.gameName = gameName;

        if (inMemory) {
            dbUrl = "jdbc:h2:mem:gameoflife;DB_CLOSE_DELAY=10";
        } else {
            dbUrl = "jdbc:h2:file:./gameoflife";
        }
        initDatabase();
    }

    private void initDatabase() {
        try (Connection conn = DriverManager.getConnection(dbUrl, dbName, dbPassword);
             Statement stmt = conn.createStatement()) {
            conn.setAutoCommit(false);
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS boards"
                    + "(id INT PRIMARY KEY auto_increment NOT NULL, name VARCHAR(16), rowSize int, colSize int);");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS vals"
                    + "(board_id INT, x int, y int, v bool, FOREIGN KEY (board_id) REFERENCES boards(id));");
            conn.commit();
        } catch (Exception e) {
            logger.error("{} {}", getLocaleMessage("sqlException"), e.getMessage());
        }
    }

    @Override
    public GameOfLifeBoard read() {
        GameOfLifeBoard board = null;
        PreparedStatement stmt = null;
        Statement stmtVal = null;
        ResultSet keys = null;
        ResultSet vals = null;
        try (Connection conn = DriverManager.getConnection(dbUrl, dbName, dbPassword)) {
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("SELECT * FROM boards WHERE name = ?");
            stmt.setString(1, gameName);
            stmt.execute();
            keys = stmt.getResultSet();
            if (keys.next()) {
                stmtVal = conn.createStatement();
                vals = stmtVal.executeQuery("SELECT * FROM vals WHERE board_id = " + keys.getInt("id")
                        + " ORDER BY x,y");
                board = new GameOfLifeBoard(keys.getInt("rowSize"), keys.getInt("colSize"),
                        new PlainGameOfLifeSimulator());
                for (int i = 0; i < board.getRows().size(); i++) {
                    for (int j = 0; j < board.getColumns().size(); j++) {
                        if (vals.next()) {
                            board.set(i, j, vals.getBoolean("v"));
                        }
                    }
                }
                conn.commit();
            } else {
                logger.error(getLocaleMessage("sqlException"));
                throw new DaoReadException();
            }
        } catch (Exception e) {
            logger.error("{} {}", getLocaleMessage("sqlException"), e.getMessage());
            throw new DaoReadException();
        } finally {
            try {
                if (stmtVal != null) {
                    stmtVal.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (vals != null) {
                    vals.close();
                }
                if (keys != null) {
                    keys.close();
                }
            } catch (Exception e) {
                logger.error("{} {}", getLocaleMessage("sqlException"), e.getMessage());
            }
        }
        return board;
    }

    @Override
    public void write(GameOfLifeBoard board) {
        PreparedStatement stmt = null;
        Statement stmt2 = null;
        ResultSet keys = null;
        try (Connection conn = DriverManager.getConnection(dbUrl, dbName, dbPassword)) {
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("INSERT INTO boards (name, rowSize, colSize) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, gameName);
            stmt.setInt(2, board.getRows().size());
            stmt.setInt(3, board.getColumns().size());
            stmt.executeUpdate();
            keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                stmt2 = conn.createStatement();
                for (int i = 0; i < board.getRows().size(); i++) {
                    for (int j = 0; j < board.getColumns().size(); j++) {
                        stmt2.executeUpdate("INSERT INTO vals VALUES"
                                + "(" + keys.getInt(1) + "," + i + "," + j + "," + board.get(i, j) + ");");
                    }
                }
                conn.commit();
            } else {
                logger.error(getLocaleMessage("sqlException"));
                throw new DaoReadException();
            }
        } catch (Exception e) {
            logger.error("{} {}", getLocaleMessage("sqlException"), e.getMessage());
            throw new DaoWriteException();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (stmt2 != null) {
                    stmt2.close();
                }
                if (keys != null) {
                    keys.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() {
    }
}
