/**
 * Klasa implementująca zasady gry w życie.
 * Implementuje interfejs {@link GameOfLifeSimulator}, który definiuje logikę symulacji.
 */
public class PlainGameOfLifeSimulator implements GameOfLifeSimulator {

    /**
     * Metoda odpowiedzialna za wykonanie jednego kroku symulacji.
     */
    @Override
    public void doStep(GameOfLifeBoard board) {
        int rows = board.getBoard().length;
        int cols = board.getBoard()[0].length;
        boolean[][] newBoard = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int livingNeighbors = countLivingNeighbors(i, j, board);

                //Reguły gry(żywe/martwe komórki)
                if (board.getBoard()[i][j]) { //Komórka żywa
                    newBoard[i][j] = livingNeighbors >= 2 && livingNeighbors <= 3; //Umiera
                }
                if (!board.getBoard()[i][j]) { //Komórka martwa
                    newBoard[i][j] = livingNeighbors == 3; //Ożywa
                }
            }
        }

        board.setBoard(newBoard); //Zastąpienie starej planszy nową
    }

    /**
     * Oblicza liczbę żywych sąsiadów danej komórki (z uwzględnieniem, że krawędzie są "cykliczne").
     *
     * @param x Indeks wiersza komórki.
     * @param y Indeks kolumny komórki.
     * @return Liczba żywych sąsiadów.
     */
    private int countLivingNeighbors(int x, int y, GameOfLifeBoard board) {
        int rows = board.getBoard().length;
        int cols = board.getBoard()[0].length;
        int liveNeighbors = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue; // Pomijamy samą komórkę
                }
                int neighborX = (x + i + rows) % rows; // Obsługa krawędzi
                int neighborY = (y + j + cols) % cols; // Obsługa krawędzi
                liveNeighbors += board.getBoard()[neighborX][neighborY] ? 1 : 0; // Zliczamy żywych sąsiadów

            }
        }
        return liveNeighbors;
    }
}
