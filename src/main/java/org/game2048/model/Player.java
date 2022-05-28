package org.game2048.model;

import org.game2048.controller.Controller;

import java.util.*;

public class Player {

    GameGrid actualGrid;
    Controller controller;
    private final List<Direction> dirList = new ArrayList<>();

    {
        dirList.add(Direction.UP);
        dirList.add(Direction.DOWN);
        dirList.add(Direction.LEFT);
        dirList.add(Direction.RIGHT);
    }

    public Player(GameGrid grid, Controller controller) {
        this.actualGrid = grid;
        this.controller = controller;
    }
    /*
    Aлгоритм к которому я пришел. Смотрит на два шага вперед и после выбирает совокупность движений, которая
    принесет больше очков. В поиске улучшения моего алгоритма нашел эту статью https://www.baeldung.com/cs/2048-algorithm.
     */

    private ArrayList<Direction> checkMoves(GameGrid grid) {
        HashMap<Integer, ArrayList<Direction>> all = new HashMap<>();
        for(Direction d : dirList) {
            GameGrid testGrid = new GameGrid(grid);
            testGrid.move(d);
            for(Direction dir : dirList) {
                ArrayList<Direction> moves = new ArrayList<>();
                moves.add(d);
                moves.add(dir);
                GameGrid testGrid2 = new GameGrid(testGrid);
                testGrid2.move(dir);
                int score = testGrid2.getScore();
                if(!testGrid2.equals(grid)) all.put(score, moves);
            }
        }
        int maxScore = all.keySet().stream().mapToInt(v -> v)
                .max().orElseThrow(NoSuchElementException::new);

        return all.get(maxScore);
    }

    public void start() {
        try {
            while (!actualGrid.endOfGame) {
                ArrayList<Direction> moves = checkMoves(actualGrid);
                for (Direction d : moves) {
                    if (actualGrid.move(d)) actualGrid.generateNewCell();
                    Thread.sleep(2L);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
