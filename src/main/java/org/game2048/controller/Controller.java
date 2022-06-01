package org.game2048.controller;

import org.game2048.model.Direction;
import org.game2048.model.GameGrid;

import java.io.*;

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

    public void createNewScoreFile() throws IOException {
        File scores = new File("scores.txt");
        scores.createNewFile();
    }

    private void writeScore(int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("scores.txt"))){
            writer.write(score + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getBestScore() {
        try(BufferedReader br = new BufferedReader(new FileReader("scores.txt"))) {
            String score, lastScore = "0";
            while((score = br.readLine()) != null) {
                lastScore = score;
            }
            return Integer.parseInt(lastScore);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void updateScore() {
        int bestScore = getBestScore();
        if (grid.getScore() > bestScore) {
            writeScore(grid.getScore());
        }
    }

    public void restart() {
        grid.clear();
        grid.generateNewCell();
        grid.generateNewCell();
        updateScore();
        System.out.printf("Game finished! Your score: %d \nBest score: %d \n\n", grid.getScore(), getBestScore());
        grid.setScore(0);
    }

    public void close() {isClosed = true;}

    public void changePlayer() {player = true;}

    public void abilitySwitch() {
        abilityToChange = false;
    }
}
