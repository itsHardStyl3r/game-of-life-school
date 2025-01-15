package pl.comp.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.comp.exceptions.DaoReadException;
import pl.comp.exceptions.DbCredentialsException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static pl.comp.GameOfLifeBoard.getLocaleMessage;

public class JdbcGameOfLifeReader implements AutoCloseable {
    private String dbName;
    private String dbPassword;
    private String dbUrl;
    private final Logger logger = LoggerFactory.getLogger(JdbcGameOfLifeReader.class);

    public JdbcGameOfLifeReader() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("credentials.txt");
        if (inputStream == null) {
            throw new DbCredentialsException();
        }
        try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                dbName = line.split("\\|")[0];
                dbPassword = line.split("\\|")[1];
                dbUrl = "jdbc:h2:" + line.split("\\|")[2];
            }
        } catch (Exception e) {
            throw new DbCredentialsException();
        }
    }

    public List<String> getBoards() {
        ArrayList<String> boards = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet boardsRead = null;
        try (Connection conn = DriverManager.getConnection(dbUrl, dbName, dbPassword)) {
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("SELECT * FROM boards");
            boardsRead = stmt.executeQuery();
            while (boardsRead.next()) {
                boards.add(boardsRead.getString(2));
            }
        } catch (Exception e) {
            logger.error("{} {}", getLocaleMessage("sqlException"), e.getMessage());
            throw new DaoReadException();
        } finally {
            try {
                if (boardsRead != null) {
                    boardsRead.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                logger.error("{} {}", getLocaleMessage("sqlException"), e.getMessage());
            }
        }
        return boards;
    }

    @Override
    public void close() {
    }
}
