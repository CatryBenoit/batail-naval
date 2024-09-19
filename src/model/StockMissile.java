package model;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;

public class StockMissile extends ContainerElement {

    public StockMissile(int x, int y, GameStageModel gameStageModel) {
        super("ship", x, y, 1, 1 , gameStageModel);

    }
}
