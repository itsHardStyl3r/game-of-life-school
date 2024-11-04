public class GameOfLifeCell {
    private boolean value;
    private GameOfLifeCell[] neighbors;

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
            return aliveNeighbors == 2 || aliveNeighbors == 3; // Przeżywa
        } else { // martwa
            return aliveNeighbors == 3; // Ożywa
        }
    }

    public void updateState() {
        this.value = nextState();
    }

    public void setNeighbors(GameOfLifeCell[] neighbors) {
        this.neighbors = neighbors;
    }


}
