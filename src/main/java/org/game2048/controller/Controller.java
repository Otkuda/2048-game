package org.game2048.controller;

import org.game2048.model.Direction;
import org.game2048.model.GameGrid;

public class Controller {
    public boolean isClosed = false;
    public boolean player = false;
    public boolean abilityToChange = true;
    GameGrid grid;

    public Controller(GameGrid grid) {
        this.grid = grid;
    }

    public void moveGrid(Direction dir) {
        if(grid.move(dir)) grid.generateNewCell();

    }

    public void restart() {
        grid.clear();
        grid.generateNewCell();
        grid.generateNewCell();
        System.out.printf("Game finished! Your score: %d \n", grid.getScore());
        grid.setScore(0);
    }

    public void close() {isClosed = true;}

    public void changePlayer() {player = true;}

    public void abilitySwitch() {
        abilityToChange = false;
    }
}
