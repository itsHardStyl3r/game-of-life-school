package pl.comp;

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
        int rows = board.getRows().size();
        int columns = board.getColumns().size();
        boolean[][] nextState = new boolean[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                nextState[i][j] = board.getCell(i, j).nextState();
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board.set(i, j, nextState[i][j]);
            }
        }
    }
}
