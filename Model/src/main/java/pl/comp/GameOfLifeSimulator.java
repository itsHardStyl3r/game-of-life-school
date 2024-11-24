package pl.comp;

/**
 * Interfejs definiujący zachowanie symulatora gry w życie.
 * Klasy implementujące ten interfejs będą musiały zdefiniować metodę doStep,
 * która przeprowadza jeden krok symulacji.
 */
public interface GameOfLifeSimulator {
    /**
     * Przeprowadza pojedynczy krok symulacji na podanej planszy gry.
     *
     * @param board Obiekt {@link GameOfLifeBoard}, który przechowuje aktualny
     *              stan planszy oraz umożliwia jej modyfikację.
     */
    void doStep(GameOfLifeBoard board);
}