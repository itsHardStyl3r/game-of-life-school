public class GameOfLifeCell {
    private boolean isAlive;
    private GameOfLifeCell[] neighbors;
    private boolean nextState;

    public GameOfLifeCell(boolean isAlive) {
        this.isAlive = isAlive;
        this.neighbors = new GameOfLifeCell[8];
    }

    public void setNeighbors(GameOfLifeCell[] neighbors) {
        if (neighbors.length == 8) {
            this.neighbors = neighbors;
        } else {
            throw new IllegalArgumentException("Komórka musi mieć osiem sąsiadów.");
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void nextState() {
        int livingNeighbors = countLivingNeighbors();
        if (isAlive) {
            nextState = livingNeighbors >= 2 && livingNeighbors <= 3;
        } else {
            nextState = livingNeighbors == 3;
        }
    }

    public void updateState() {
        this.isAlive = nextState;
    }

    private int countLivingNeighbors() {
        int liveNeighbors = 0;
        for (GameOfLifeCell neighbor : neighbors) {
            if (neighbor != null && neighbor.isAlive()) {
                liveNeighbors++;
            }
        }
        return liveNeighbors;
    }
}
