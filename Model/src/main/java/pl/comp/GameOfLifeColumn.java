package pl.comp;

import java.util.List;

public class GameOfLifeColumn extends GameOfLifeGroup {
    public GameOfLifeColumn(List<GameOfLifeCell> cells) {
        super(cells);
    }

    @Override
    public GameOfLifeColumn clone() {
        return (GameOfLifeColumn) super.clone();
    }
}
