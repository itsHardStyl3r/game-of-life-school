package pl.comp;

import java.util.List;

public class GameOfLifeRow extends GameOfLifeGroup {
    public GameOfLifeRow(List<GameOfLifeCell> cells) {
        super(cells);
    }


    @Override
    public GameOfLifeRow clone() {
        return (GameOfLifeRow) super.clone();
    }
}