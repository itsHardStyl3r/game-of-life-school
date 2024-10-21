// do zrobineia, skopiowany jest stary gameoflifeboard

import java.util.Arrays;
import java.util.Random;

/**
 * Klasa implementująca zasady gry w życie.
 * Implementuje interfejs {@link GameOfLifeSimulator}, który definiuje logikę
 * symulacji.
 */
public class guPlainGameOfLifeSimulator implements GameOfLifeSimulator {
    private final int rows;
    private final int cols;
    private final Random random = new Random();
    private boolean[][] board;

    /**
     * Konstruktor klasy, który przyjmuje wymiary planszy i losowo inicjalizuje stany komórek.
     *
     * @param rows Liczba wierszy planszy.
     * @param cols Liczba kolumn planszy.
     */
    public GameOfLifeBoard(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.board = new boolean[rows][cols];
        initializeBoard();
    }

    /**
     * Inicjalizuje planszę losowymi wartościami (0 lub 1).
     */
    private void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = random.nextBoolean(); // 0 - martwa, 1 - żywa
            }
        }
    }

    /**
     * Zwraca kopię planszy, aby nie narażać jej na modyfikacje z zewnątrz.
     *
     * @return Kopia planszy jako tablica dwuwymiarowa.
     */
    public boolean[][] getBoard() {
        boolean[][] boardCopy = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            boardCopy[i] = Arrays.copyOf(board[i], cols);
        }
        return boardCopy;
    }

    /**
     * Ustawia planszę na podaną tablicę, przyjmuje kopię wejściowej tablicy, aby uniknąć bezpośrednich modyfikacji.
     *
     * @param newBoard Nowa plansza jako tablica dwuwymiarowa.
     */
    public void setBoard(boolean[][] newBoard) {
        for (int i = 0; i < rows; i++) {
            board[i] = Arrays.copyOf(newBoard[i], cols);
        }
    }

    /**
     * Oblicza liczbę żywych sąsiadów danej komórki (z uwzględnieniem, że krawędzie są "cykliczne").
     *
     * @param x Indeks wiersza komórki.
     * @param y Indeks kolumny komórki.
     * @return Liczba żywych sąsiadów.
     */
    private int countLivingNeighbors(int x, int y) {
        int liveNeighbors = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue; // Pomijamy samą komórkę
                }
                int neighborX = (x + i + rows) % rows; // Obsługa krawędzi
                int neighborY = (y + j + cols) % cols; // Obsługa krawędzi
                liveNeighbors += board[neighborX][neighborY] ? 1 : 0; // Zliczamy żywych sąsiadów

            }
        }
        return liveNeighbors;
    }

    /**
     * Metoda odpowiedzialna za wykonanie jednego kroku symulacji.
     */
    public void doStep() {
        boolean[][] newBoard = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int livingNeighbors = countLivingNeighbors(i, j);

                //Reguły gry(żywe/martwe komórki)
                if (board[i][j]) { //Komórka żywa
                    newBoard[i][j] = livingNeighbors >= 2 && livingNeighbors <= 3; //Umiera
                }
                if (!board[i][j]) { //Komórka martwa
                    newBoard[i][j] = livingNeighbors == 3; //Ożywa
                }
            }
        }

        this.board = newBoard; //Zastąpienie starej planszy nową
    }
}