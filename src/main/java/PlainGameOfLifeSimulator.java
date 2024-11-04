/**
 * Klasa implementująca zasady gry w życie.
 * Implementuje interfejs {@link GameOfLifeSimulator}, który definiuje logikę
 * symulacji.
 */
public class PlainGameOfLifeSimulator implements GameOfLifeSimulator {

    /**
     * Metoda odpowiedzialna za wykonanie jednego kroku symulacji.
     *
     * @param board Obiekt {@link GameOfLifeBoard}, który przechowuje aktualny
     *              stan planszy.
     */
    @Override
    public void doStep(GameOfLifeBoard board) {
        int rows = board.getRows();
        int cols = board.getCols();

        // Obliczamy nowy stan dla każdej komórki
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board.get(i, j).nextState();
            }
        }

        // Aktualizacja stanu planszy
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board.get(i, j).updateState();
            }
        }
    }
    /**
     * Oblicza liczbę żywych sąsiadów danej komórki (z uwzględnieniem, że krawędzie są "cykliczne").
     *
     * @param board Obiekt {@link GameOfLifeBoard} reprezentujący stan planszy.
     * @param x     Współrzędna wiersza komórki.
     * @param y     Współrzędna kolumny komórki.
     * @param rows  Liczba wierszy na planszy.
     * @param cols  Liczba kolumn na planszy.
     * @return Liczba żywych sąsiadów komórki.
     */
    private int countLivingNeighbors(GameOfLifeBoard board, int x, int y, int rows, int cols) {
        int liveNeighbors = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue; // Pomijamy samą komórkę
                }
                int neighborX = (x + i + rows) % rows; // Obsługa krawędzi
                int neighborY = (y + j + cols) % cols; // Obsługa krawędzi
                liveNeighbors += board.get(neighborX, neighborY).getCellValue() ? 1 : 0; // Zliczamy żywych sąsiadów
            }
        }

        return liveNeighbors;
    }
}
