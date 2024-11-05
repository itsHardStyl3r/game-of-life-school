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
        for (GameOfLifeRow row : board.getRows()) {
            for (GameOfLifeCell cell : row.cells) {
                cell.nextState();
            }
        }
        for (GameOfLifeRow row : board.getRows()) {
            for (GameOfLifeCell cell : row.cells) {
                cell.updateState();
            }
        }
    }
}
