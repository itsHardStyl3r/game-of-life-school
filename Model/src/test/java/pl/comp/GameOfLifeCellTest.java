package pl.comp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeCellTest {
    private GameOfLifeCell cell;

    @BeforeEach
    public void setUp() {
        cell = new GameOfLifeCell(false);
    }

    /**
     * Test sprawdza, czy po ustawieniu sąsiadów ich liczba jest równa 8.
     */
    @Test
    public void testNeighbors() {
        GameOfLifeCell cell = new GameOfLifeCell(false);
        GameOfLifeCell[] neighbors = new GameOfLifeCell[8];

        for (int i = 0; i < neighbors.length; i++) {
            neighbors[i] = new GameOfLifeCell(i % 2 == 0);
        }

        cell.setNeighbors(List.of(neighbors));
        assertEquals(8, neighbors.length, "Tablica sąsiadów powinna mieć dokładnie 8 elementów.");
    }

    /**
     * Test sprawdzający czy po ustawieniu sąsiadów ich liczba jest równa 8
     */
    @Test
    public void testArrayLength() {
        GameOfLifeCell cell = new GameOfLifeCell(false);

        GameOfLifeCell[] invalidNeighbors = new GameOfLifeCell[7];
        for (int i = 0; i < invalidNeighbors.length; i++) {
            invalidNeighbors[i] = new GameOfLifeCell(false);
        }

        // Spodziewane rzucenie wyjątku przy ustawieniu z niepoprawną liczbą sąsiadów
        try {
            cell.setNeighbors(List.of(invalidNeighbors));
            fail("Spodziewany wyjątek IllegalArgumentException z powodu niepoprawnej liczby sąsiadów.");
        } catch (IllegalArgumentException e) {
            // Wyjątek został przechwycony, test przechodzi
        }

        GameOfLifeCell[] validNeighbors = new GameOfLifeCell[8];
        for (int i = 0; i < validNeighbors.length; i++) {
            validNeighbors[i] = new GameOfLifeCell(false);
        }

        // Ustawienie poprawnej tablicy sąsiadów
        cell.setNeighbors(List.of(validNeighbors));
        List<GameOfLifeCell> neighborsValue = cell.getNeighbors();

        // Sprawdzenie, czy sąsiedzi zostali poprawnie ustawieni, gdy tablica ma dokładnie 8 elementów
        assertNotNull(neighborsValue, "Sąsiedzi ustawieni, tablica ma dokładnie 8 elementów");
        assertEquals(8, neighborsValue.size(), "Tablica sąsiadów powinna mieć 8 elementów");
    }

    /**
     * Test sprawdzający metode nextState() dla komórki martwej.
     */
    @Test
    public void testNextStateDeadCells() {
        // Tworzymy 3 żywych sąsiadów
        GameOfLifeCell aliveNeighbor = new GameOfLifeCell(true);

        // Ustawiamy sąsiadów komórki (3 żywi sąsiedzi)
        cell.setNeighbors(Arrays.asList(aliveNeighbor, aliveNeighbor, aliveNeighbor,
                new GameOfLifeCell(false), new GameOfLifeCell(false),
                new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)));

        // Sprawdzamy, czy komórka przeżyje (powinna stać się żywa, jeśli ma dokładnie 3 żywych sąsiadów)
        boolean nextState = cell.nextState();

        assertTrue(nextState, "Komórka martwa powinna stać się żywa, jeśli ma 3 żywych sąsiadów");
    }

    /**
     * Test sprawdzający metode nextState() dla komórki żywej.
     */
    @Test
    public void testNextStateAliveCell() {
        GameOfLifeCell aliveNeighbor = new GameOfLifeCell(true);

        cell.updateState(true);
        cell.setNeighbors(Arrays.asList(aliveNeighbor, aliveNeighbor, new GameOfLifeCell(false),
                new GameOfLifeCell(false), new GameOfLifeCell(false),
                new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)));

        boolean nextState = cell.nextState();

        assertTrue(nextState, "Komórka żywa powinna przeżyć, jeśli ma 2 lub 3 żywych sąsiadów");
    }

    /**
     * Test sprawdzający, czy komórka martwa nie zmienia stanu, jeśli ma innych sąsiadów niż 3 żywych.
     */
    @Test
    public void testNextStateDeadCellWithTwoAlive() {
        GameOfLifeCell aliveNeighbor = new GameOfLifeCell(true);

        cell.setNeighbors(Arrays.asList(aliveNeighbor, aliveNeighbor, new GameOfLifeCell(false),
                new GameOfLifeCell(false), new GameOfLifeCell(false),
                new GameOfLifeCell(false), new GameOfLifeCell(false), new GameOfLifeCell(false)));

        boolean nextState = cell.nextState();

        assertFalse(nextState, "Komórka martwa nie powinna stać się żywa, jeśli ma mniej niż 3 żywych sąsiadów");
    }

    /**
     * Test sprawdzający wyjątek przy ustawianiu sąsiadów.
     */
    @Test
    public void testSizeWithInvalidSize() {
        assertThrows(IllegalArgumentException.class, () -> cell.setNeighbors(Arrays.asList(new GameOfLifeCell(true), new GameOfLifeCell(false))), "Powinien zostać rzucony wyjątek, gdy lista sąsiadów ma mniej niż 8 elementów.");
    }

    /**
     * Test sprawdzający, czy komórka może mieć dokładnie 8 sąsiadów.
     */
    @Test
    public void testValidSize() {
        // Próba ustawienia sąsiadów z listą o właściwej długości
        assertDoesNotThrow(() -> cell.setNeighbors(Arrays.asList(
                new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(false),
                new GameOfLifeCell(false), new GameOfLifeCell(true), new GameOfLifeCell(false),
                new GameOfLifeCell(true), new GameOfLifeCell(false))), "Powinno być możliwe ustawienie 8 sąsiadów");
    }

    /**
     * Test sprawdzający equals, hashcode i toString.
     * SuppressWarnings dla coverage dla equals():
     * - (this == obj)
     * - (getClass() != obj.getClass())
     */
    @SuppressWarnings({
            "EqualsWithItself",
            "SimplifiableAssertion",
            "EqualsBetweenInconvertibleTypes",
            "ConstantValue"})
    @Test
    void testEqualsAndHashCode() {
        GameOfLifeCell cell1 = new GameOfLifeCell(true);
        GameOfLifeCell cell2 = new GameOfLifeCell(true);
        GameOfLifeCell cell3 = new GameOfLifeCell(false);

        cell1.setNeighbors(List.of(cell2, cell3, cell1, cell2, cell3, cell1, cell2, cell3));
        cell2.setNeighbors(List.of(cell1, cell3, cell1, cell2, cell3, cell1, cell2, cell3));
        cell3.setNeighbors(List.of(cell1, cell2, cell1, cell2, cell3, cell1, cell2, cell3));

        // Sprawdzenie z samym sobą (this == obj)
        assertEquals(cell1, cell1);
        assertEquals(cell2, cell2);
        assertEquals(cell3, cell3);
        assertEquals(cell1.toString(), cell1.toString());
        assertEquals(cell2.toString(), cell2.toString());
        assertEquals(cell3.toString(), cell3.toString());

        // Sprawdzenie z innymi
        assertEquals(cell1, cell2);
        assertNotEquals(cell1, cell3);
        assertEquals(cell1.hashCode(), cell2.hashCode());
        assertNotEquals(cell1.hashCode(), cell3.hashCode());
        assertEquals(cell1.toString(), cell2.toString());
        assertNotEquals(cell1.toString(), cell3.toString());

        assertFalse(cell1.equals("ABC"));
        assertFalse(cell1.equals(null));
        assertFalse(cell2.equals("ABC"));
        assertFalse(cell2.equals(null));
        assertFalse(cell3.equals("ABC"));
        assertFalse(cell3.equals(null));
    }

    /**
     * Test sprawdzający czy plansza jest porównywana
     */
    @Test
    public void testCompareTo() {
        GameOfLifeCell cell1 = new GameOfLifeCell(true);
        GameOfLifeCell cell2 = new GameOfLifeCell(false);
        GameOfLifeCell cell3 = new GameOfLifeCell(true);

        assertTrue(cell1.compareTo(cell2) > 0, "Żywa komórka powinna być większa niż martwa komórka");
        assertTrue(cell2.compareTo(cell1) < 0, "Martwa komórka powinna być mniejsza niż żywa komórka");
        assertEquals(0, cell1.compareTo(cell3), "Dwie żywe komórki powinny być równe");
        assertEquals(0, cell2.compareTo(new GameOfLifeCell(false)), "Dwie martwe komórki powinny być równe");
        assertThrows(NullPointerException.class, () -> cell.compareTo(null));
    }
}
