package org.game2048;

import org.game2048.controller.Controller;
import org.game2048.model.GameGrid;
import org.game2048.model.Player;
import org.game2048.view.GraphicsDisplay;

import java.io.*;

public class Main {

    static GameGrid grid = new GameGrid(4);
    static Controller cont = new Controller(grid);
    static Player pl = new Player(grid, cont);


    public static void main(String[] args) {
        GraphicsDisplay display = new GraphicsDisplay(grid, cont);
        Thread t = new Thread(Main::logic);
        t.start();
        display.run();
        t.stop();
    }


    public static void logic() {
        try {
            cont.createNewScoreFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        grid.generateNewCell();
        grid.generateNewCell();
        while (!grid.endOfGame && !cont.isClosed) {
            if(cont.player) pl.start();
            else {
                grid.isThere2048();
                grid.ifLost();
                grid.isThere0();
            }
        }
        cont.updateScore();
        System.out.printf("Game finished! Your score: %d \nBest Score: %d \n\n", grid.getScore(), cont.getBestScore());
        cont.close();
    }




}
