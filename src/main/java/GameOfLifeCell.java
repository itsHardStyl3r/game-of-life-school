public class GameOfLifeCell {
    private boolean value;
    private GameOfLifeCell[] neighbors;
    private boolean nextState;

    public GameOfLifeCell(boolean value) {
        this.value = value;
        this.neighbors = new GameOfLifeCell[8]; // Max 8
    }

    public boolean getCellValue() {
        return value;
    }

    public boolean nextState() {
        int aliveNeighbors = 0;
        for (GameOfLifeCell neighbor : neighbors) {
            if (neighbor != null && neighbor.getCellValue()) {
                aliveNeighbors++;
            }
        }

        if (value) { // żywa
            nextState = aliveNeighbors == 2 || aliveNeighbors == 3; // Przeżywa
        } else { // martwa
            nextState = aliveNeighbors == 3; // Ożywa
        }
        return nextState;
    }

    public void updateState() {
        this.value = nextState;
    }

    public void setNeighbors(GameOfLifeCell[] neighbors) {
        this.neighbors = neighbors;
    }

}
