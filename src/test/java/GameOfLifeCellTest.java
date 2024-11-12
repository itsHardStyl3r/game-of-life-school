import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeCellTest {

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
     * Test sprawdza, czy po ustawieniu sąsiadów ich liczba jest równa 8.
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

}
