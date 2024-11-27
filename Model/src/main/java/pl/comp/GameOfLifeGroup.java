package pl.comp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * Abstrakcyjna klasa reprezentująca grupę komórek w grze w życie.
 * Klasa `pl.comp.GameOfLifeGroup` przechowuje listę `cells`, która reprezentuje
 * grupę komórek. Zawiera metody do liczenia liczby żywych oraz martwych
 * komórek w tej grupie.
 */
public abstract class GameOfLifeGroup implements Serializable {
    protected final List<GameOfLifeCell> cells;

    public GameOfLifeGroup(List<GameOfLifeCell> cells) {
        this.cells = cells;
    }

    /**
     * Liczy liczbę żywych komórek w grupie.
     * Przechodzi przez wszystkie komórki w liście `cells` i zlicza te,
     * które są żywe (posiadają wartość true).
     *
     * @return liczba żywych komórek w grupie
     */
    public int countAliveCells() {
        int aliveCount = 0;
        for (GameOfLifeCell cell : cells) {
            if (cell.getCellValue()) {
                aliveCount++;
            }
        }
        return aliveCount;
    }

    /**
     * Liczy liczbę martwych komórek w grupie.
     * Metoda wykorzystuje `countAliveCells()` i odejmuje jego wynik
     * od całkowitej liczby komórek, co daje liczbę komórek martwych.
     *
     * @return liczba martwych komórek w grupie
     */
    public int countDeadCells() {
        return cells.size() - countAliveCells();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        GameOfLifeGroup that = (GameOfLifeGroup) obj;
        return new EqualsBuilder()
                .append(cells, that.cells)
                .append(cells.size(), that.cells.size())
                .append(countAliveCells(), that.countAliveCells())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(cells)
                .append(cells.size())
                .append(countAliveCells())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append(cells)
                .append(cells.size())
                .append(countAliveCells())
                .toString();
    }
}

