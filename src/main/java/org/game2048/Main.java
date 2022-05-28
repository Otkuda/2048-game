package org.game2048;

import org.game2048.controller.Controller;
import org.game2048.model.GameGrid;
import org.game2048.view.GraphicsDisplay;

public class Main {

    static GameGrid grid = new GameGrid(4);
    static Controller cont = new Controller(grid);


    public static void main(String[] args) {
        GraphicsDisplay display = new GraphicsDisplay(grid, cont);
        Thread t = new Thread(Main::logic);
        t.start();
        display.run();
        System.out.printf("Game finished! Your score: %d", grid.getScore());
    }

    public static void logic() {
        grid.generateNewCell();
        grid.generateNewCell();
        while (!grid.endOfGame && !cont.isClosed) {
            grid.isThere2048();
            grid.ifLost();
        }
        cont.close();
    }

}
