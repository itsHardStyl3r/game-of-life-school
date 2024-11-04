public abstract class GameOfLifeGroup {
    protected GameOfLifeCell[] cells;

    public GameOfLifeGroup(GameOfLifeCell[] cells) {
        this.cells = cells;
    }

    public int countAliveCells() {
        int aliveCount = 0;
        for (GameOfLifeCell cell : cells) {
            if (cell.getCellValue()) {
                aliveCount++;
            }
        }
        return aliveCount;
    }

    public int countDeadCells() {
        int deadCount = 0;
        for (GameOfLifeCell cell : cells) {
            if (!cell.getCellValue()) {
                deadCount++;
            }
        }
        return deadCount;
    }
}
