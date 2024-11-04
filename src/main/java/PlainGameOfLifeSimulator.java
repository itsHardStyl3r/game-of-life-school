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
}
