package pl.comp.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static pl.comp.GameOfLifeBoard.getLocaleMessage;

public class JdbcGameOfLifeReader implements AutoCloseable {
    private final Logger logger = LoggerFactory.getLogger(JdbcGameOfLifeReader.class);

    public List<String> getBoards() {
        ArrayList<String> boards = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet boardsRead = null;
        try (Connection conn = DriverManager.getConnection("jdbc:h2:file:./gameoflife;IFEXISTS=TRUE",
                "admin", "admin")) {
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("SELECT * FROM boards");
            boardsRead = stmt.executeQuery();
            while (boardsRead.next()) {
                boards.add(boardsRead.getString(2));
            }
        } catch (Exception e) {
            logger.warn("{} {}", getLocaleMessage("sqlException"), e.toString());
        } finally {
            try {
                if (boardsRead != null) {
                    boardsRead.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                logger.error("{} {}", getLocaleMessage("sqlException"), e.toString());
            }
        }
        return boards;
    }

    @Override
    public void close() {
    }
}
