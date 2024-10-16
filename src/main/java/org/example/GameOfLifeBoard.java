package org.example;

import java.util.Random;

/**
 * Klasa {@link GameOfLifeBoard} reprezentująca planszę gry Gra w Życie.
 */
public class GameOfLifeBoard {
    private final int rows;
    private final int cols;
    private boolean[][] board;

    /**
     * Tworzy nową grę (obiekt {@link GameOfLifeBoard}) z losowym początkowym stanem komórek o podanych wymiarach.
     *
     * @param rows liczba wierszy
     * @param cols liczba kolumn
     */
    public GameOfLifeBoard(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.board = new boolean[rows][cols];

        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = random.nextBoolean();
            }
        }
    }

    /**
     * Tworzy nową grę (obiekt {@link GameOfLifeBoard}) na podstawie podanej początkowej planszy.
     *
     * @param initialBoard tablica z początkowym stanem gry
     */
    public GameOfLifeBoard(boolean[][] initialBoard) {
        this.rows = initialBoard.length;
        this.cols = initialBoard[0].length;
        this.board = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            System.arraycopy(initialBoard[i], 0, this.board[i], 0, cols);
        }
    }

    /**
     * Zwraca kopię obecnego stanu planszy.
     *
     * @return Kopia tablicy z obecnym stanem gry.
     */
    public boolean[][] getBoard() {
        boolean[][] copy = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, cols);
        }
        return copy;
    }

    /**
     * Wykonuje jeden krok symulacji Gry w Życie.
     * - Komórka ożywa, jeśli ma trzech sąsiadów,
     * - Komórka nie umiera, jeśli ma dwóch lub trzech sąsiadów,
     * - Komórka umiera, jeśli ma jednego sąsiada,
     * - Komórka umiera, jeśli ma więcej niż trzech sąsiadów.
     */
    public void doStep() {
        boolean[][] newBoard = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int aliveAround = countNear(i, j);

                if (!board[i][j] && aliveAround == 3) {
                    newBoard[i][j] = true;
                } else if (board[i][j] && (aliveAround == 2 || aliveAround == 3)) {
                    newBoard[i][j] = true;
                } else if (board[i][j] && aliveAround == 1) {
                    newBoard[i][j] = false;
                } else if (board[i][j] && aliveAround > 3) {
                    newBoard[i][j] = false;
                }
            }
        }

        this.board = newBoard;
    }

    /**
     * Zlicza liczbę żywych komórek wokół komórki w danym miejscu.
     *
     * @param row wiersz komórki
     * @param col kolumna komórki
     * @return liczba żywych komórek w okolicy podanej
     */
    private int countNear(int row, int col) {
        int near = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                int aliveInRow = (rows + row + i) % rows;
                int aliveInCol = (cols + col + j) % cols;

                if (board[aliveInRow][aliveInCol]) {
                    near++;
                }
            }
        }
        return near;
    }
}
