public abstract class GameOfLifeGroup {
    protected final GameOfLifeCell[] cells;

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
        return cells.length - countAliveCells();
    }
}
