package pl.comp;

import org.apache.commons.collections4.list.FixedSizeList;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.comp.exceptions.UnspecifiedSimulatorException;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Klasa reprezentująca planszę do gry w życie.
 */
public class GameOfLifeBoard implements Serializable, Cloneable {
    private final int rowsCount;
    private final int colsCount;
    private final GameOfLifeSimulator simulator;
    private GameOfLifeCell[][] board;
    private List<GameOfLifeRow> rows;
    private List<GameOfLifeColumn> columns;

    private static final Logger logger = LoggerFactory.getLogger(GameOfLifeBoard.class);

    /**
     * Konstruktor klasy {@link GameOfLifeBoard}.
     * Inicjalizuje planszę z zadanymi wymiarami i symulatorem.
     *
     * @param rowsCount Liczba wierszy planszy.
     * @param colsCount Liczba kolumn planszy.
     * @param simulator Symulator gry.
     * @throws UnspecifiedSimulatorException Gdy symulator nie został podany — jest nullem.
     */
    public GameOfLifeBoard(int rowsCount, int colsCount, GameOfLifeSimulator simulator) {
        if (simulator == null) {
            logger.error(getLocaleMessage("simulatorIsNullException"));
            throw new UnspecifiedSimulatorException();
        }
        this.rowsCount = Math.max(rowsCount, 1);
        this.colsCount = Math.max(colsCount, 1);
        this.board = new GameOfLifeCell[this.rowsCount][this.colsCount];

        this.rows = new ArrayList<>(this.rowsCount);
        this.columns = new ArrayList<>(this.colsCount);
        this.simulator = simulator;
        initializeBoard(Density.FULL);
    }

    /**
     * Konstruktor klasy {@link GameOfLifeBoard}.
     * Inicjalizuje planszę z zadanymi wymiarami, symulatorem i gęstością żywych komórek.
     *
     * @param rowsCount Liczba wierszy planszy.
     * @param colsCount Liczba kolumn planszy.
     * @param simulator Symulator gry.
     * @param density   Gęstość żywych komórek.
     * @throws UnspecifiedSimulatorException Gdy symulator nie został podany.
     */
    public GameOfLifeBoard(int rowsCount, int colsCount, GameOfLifeSimulator simulator, Density density) {
        if (simulator == null) {
            logger.error(getLocaleMessage("simulatorIsNullException"));
            throw new UnspecifiedSimulatorException();
        }
        this.rowsCount = Math.max(rowsCount, 1);
        this.colsCount = Math.max(colsCount, 1);
        this.board = new GameOfLifeCell[this.rowsCount][this.colsCount];

        this.rows = new ArrayList<>(this.rowsCount);
        this.columns = new ArrayList<>(this.colsCount);
        this.simulator = simulator;
        initializeBoard(density == null ? Density.FULL : density);
    }

    /**
     * Inicjalizuje planszę losowymi wartościami komórek
     * Korzysta z `Collections.shuffle()` do losowego ustawienia stanu komórek
     */
    private void initializeBoard(Density density) {
        List<GameOfLifeCell> allCells = new ArrayList<>(rowsCount * colsCount);

        // Określamy liczbę żywych i martwych komórek
        int aliveCount = rowsCount * colsCount / 2;
        if (density != Density.FULL) {
            aliveCount = Math.round(rowsCount * colsCount * density.getDensity());
        }
        int deadCount = (rowsCount * colsCount) - aliveCount;

        // Dodajemy komórki żywe
        for (int i = 0; i < aliveCount; i++) {
            allCells.add(new GameOfLifeCell(true));
        }

        // Dodajemy komórki martwe
        for (int i = 0; i < deadCount; i++) {
            allCells.add(new GameOfLifeCell(false));
        }

        // Losowe przetasowanie komórek na planszy
        Collections.shuffle(allCells);

        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < colsCount; j++) {
                board[i][j] = allCells.removeFirst();
            }
            rows.add(new GameOfLifeRow(Arrays.asList(board[i])));
        }

        for (int j = 0; j < colsCount; j++) {
            List<GameOfLifeCell> column = new ArrayList<>(rowsCount);
            for (int i = 0; i < rowsCount; i++) {
                column.add(board[i][j]);
            }
            columns.add(new GameOfLifeColumn(column));
        }
        rows = FixedSizeList.fixedSizeList(rows);
        columns = FixedSizeList.fixedSizeList(columns);
        linkNeighbors();
    }

    public static String getLocaleMessage(String s) {
        try {
            return ResourceBundle.getBundle("messages").getString(s);
        } catch (MissingResourceException e) {
            return "missing " + s;
        }
    }

    /**
     * Łączy sąsiadujące komórki z każdą komórką w planszy, umożliwiając im dostęp do swoich sąsiadów.
     */
    private void linkNeighbors() {
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < colsCount; j++) {
                List<GameOfLifeCell> neighbors = new ArrayList<>(8);
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        if (x == 0 && y == 0) {
                            continue;
                        }
                        int ni = (i + x + rowsCount) % rowsCount;
                        int nj = (j + y + colsCount) % colsCount;
                        neighbors.add(board[ni][nj]);
                    }
                }
                board[i][j].setNeighbors(neighbors);
            }
        }
    }

    /**
     * Zwraca wartość stanu komórki na danej pozycji.
     *
     * @param x Indeks wiersza.
     * @param y Indeks kolumny.
     * @return True, jeśli komórka jest żywa; false, jeśli jest martwa.
     */
    public boolean get(int x, int y) {
        return board[x][y].getCellValue();
    }

    /**
     * Zwraca komórkę na danej pozycji.
     *
     * @param x Indeks wiersza.
     * @param y Indeks kolumny.
     * @return pl.comp.GameOfLifeCell
     */
    public GameOfLifeCell getCell(int x, int y) {
        return board[x][y];
    }

    /**
     * Ustawia nowy stan komórki na danej pozycji.
     *
     * @param x     Indeks wiersza.
     * @param y     Indeks kolumny.
     * @param state Nowy stan komórki (true — żywa, false — martwa).
     */
    public void set(int x, int y, boolean state) {
        board[x][y].updateState(state);
    }

    /**
     * Zwraca listę obiektów {@link GameOfLifeRow}.
     *
     * @return Kopia listy rzędów planszy.
     */
    public List<GameOfLifeRow> getRows() {
        return rows;
    }

    /**
     * Zwraca listę obiektów {@link GameOfLifeColumn}.
     *
     * @return Kopia listy kolumn planszy.
     */
    public List<GameOfLifeColumn> getColumns() {
        return columns;
    }

    /**
     * Wykonuje krok symulacji w dostępnej symulacji.
     */
    public void doSimulationStep() {
        simulator.doStep(this);
    }

    /**
     * Zwraca kolumnę o indeksie i jako obiekt pl.comp.GameOfLifeColumn.
     *
     * @param i Indeks kolumny.
     * @return Obiekt pl.comp.GameOfLifeColumn reprezentujący kolumnę o podanym indeksie.
     */
    public GameOfLifeColumn getColumn(int i) {
        return columns.get(i);
    }

    /**
     * Zwraca wiersz o indeksie i jako obiekt pl.comp.GameOfLifeRow.
     *
     * @param i Indeks wiersza.
     * @return Obiekt pl.comp.GameOfLifeRow reprezentujący wiersz o podanym indeksie.
     */
    public GameOfLifeRow getRow(int i) {
        return rows.get(i);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        GameOfLifeBoard that = (GameOfLifeBoard) obj;
        return new EqualsBuilder()
                .append(rowsCount, that.rowsCount)
                .append(colsCount, that.colsCount)
                .append(rows, that.rows)
                .append(columns, that.columns)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(rowsCount)
                .append(colsCount)
                .append(rows)
                .append(columns)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("rowsCount", rowsCount)
                .append("colsCount", colsCount)
                .append("rows", rows)
                .append("columns", columns)
                .toString();
    }

    @Override
    public GameOfLifeBoard clone() {
        try {
            GameOfLifeBoard cloned = (GameOfLifeBoard) super.clone();
            cloned.rows = this.rows.stream().map(GameOfLifeRow::clone).collect(Collectors.toList());
            cloned.columns = this.columns.stream().map(GameOfLifeColumn::clone).collect(Collectors.toList());
            cloned.board = new GameOfLifeCell[this.rowsCount][this.colsCount];
            for (int i = 0; i < this.rowsCount; i++) {
                for (int j = 0; j < this.colsCount; j++) {
                    cloned.board[i][j] = this.board[i][j].clone();
                }
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
