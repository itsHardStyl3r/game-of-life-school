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
        // Zakładamy, że rozmiar planszy to 5x5, ale może być dowolny
        int rows = 5;
        int cols = 5;
        boolean[][] newBoard = new boolean[rows][cols];

        // Przechodzimy przez wszystkie komórki planszy
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int livingNeighbors = countLivingNeighbors(board, i, j, rows, cols);

                // Reguły gry (żywe/martwe komórki)
                if (board.get(i, j)) {  // Komórka żywa
                    newBoard[i][j] = livingNeighbors >= 2 && livingNeighbors <= 3; // Umiera
                } else { // Martwa komórka
                    newBoard[i][j] = livingNeighbors == 3; // Ożywa
                }
            }
        }

        // Aktualizacja stanu planszy
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board.set(i, j, newBoard[i][j]); // Ustawienie nowego stanu
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
                liveNeighbors += board.get(neighborX, neighborY) ? 1 : 0; // Zliczamy żywych sąsiadów
            }
        }

        return liveNeighbors;
    }
}
