package org.game;


import java.util.Arrays;
import java.util.Random;

public class GameGrid {

    private final int[][] grid;
    Controller controller;
    public boolean endOfGame = false;
    private int score;

    public GameGrid(int x) {
        grid = new int[x][x];
    }

    public GameGrid(int x, Controller controller) {
        grid = new int[x][x];
        this.controller = controller;
    }

    public int getTile(int y, int x) {
        return grid[y][x];
    }

    public void setTile(int y, int x, int value) {
        grid[y][x] = value;
    }

    public int[] getLine(int n) {
        return grid[n];
    }

    public void setLine(int n, int[] value) {
        grid[n] = value;
    }

    public int[] getColumn(int n) {
        int[] res = new int[4];
        for(int i = 0; i < 4; i++) {
            res[i] = grid[i][n];
        }
        return res;
    }

    public void setColumn(int n, int[] value) {
        for(int i = 0; i < 4; i++) {
            grid[i][n] = value[i];
        }
    }

    public void generateNewCell() {
        int value = ((int)(Math.random() * 100) <= 20) ? 4 : 2;
        int randomX = new Random().nextInt(4);
        int randomY = new Random().nextInt(4);
        int currentX = randomX;
        int currentY = randomY;

        boolean placed = false;
        while(!placed) {
            if (getTile(currentX, currentY) == 0) {
                setTile(currentX, currentY, value);
                placed = true;
            } else {
                if (currentX + 1 < 4) {
                    currentX++;
                } else {
                    currentX = 0;
                    if (currentY + 1 < 4) {
                        currentY++;
                    } else currentY = 0;
                }
                if (currentX == randomX && currentY == randomY) {
                    endOfGame = true;
                    System.out.println("не генерирую");
                    return;
                }
            }
        }
    }

    public boolean move(Direction dir) {
        boolean res = false;
        switch (dir) {
            case UP:
            case DOWN:
                for(int i = 0; i < 4; i++){
                    int[] col = getColumn(i);
                    if (dir == Direction.DOWN) {
                        int[] temp = new int[col.length];
                        for (int j = 0; j < col.length; j++){
                            temp[j] = col[col.length - j - 1];
                        }
                        col = temp;
                    }
                    int[] movedCol = moveRow(col);
                    res = res || !Arrays.equals(col, movedCol);

                    if (dir == Direction.DOWN) {
                        int[] temp = new int[movedCol.length];
                        for (int j = 0; j < movedCol.length; j++){
                            temp[j] = movedCol[movedCol.length - j - 1];
                        }
                        movedCol = temp;
                    }
                    setColumn(i, movedCol);
                }
                break;
            case LEFT:
            case RIGHT:
                for(int i = 0; i < 4; i++){
                    int[] row = getLine(i);
                    if (dir == Direction.RIGHT) {
                        int[] temp = new int[row.length];
                        for (int j = 0; j < row.length; j++){
                            temp[j] = row[row.length - j - 1];
                        }
                        row = temp;
                    }
                    int[] movedRow = moveRow(row);
                    res = res || !Arrays.equals(row, movedRow);

                    if (dir == Direction.RIGHT) {
                        int[] temp = new int[movedRow.length];
                        for (int j = 0; j < movedRow.length; j++){
                            temp[j] = movedRow[movedRow.length - j - 1];
                        }
                        movedRow = temp;
                    }
                    setLine(i, movedRow);
                }
        }
        return res;
    }

    private int[] moveRow(int[] arr){
        int[] res = new int[arr.length];
        int[] newArr = new int[arr.length];
        int newArrPointer = 0;

        for(int i = 0; i < arr.length; i++) {
            if(arr[i] != 0) {
                newArr[newArrPointer] = arr[i];
                newArrPointer++;
            }
        }
        newArrPointer = 0;
        int resPointer = 0;

        while (newArrPointer < newArr.length) {
            if (newArrPointer + 1 < newArr.length
                    && newArr[newArrPointer] == newArr[newArrPointer + 1]
                    && newArr[newArrPointer] != 0) {
                res[resPointer] = newArr[newArrPointer] * 2;
                score += res[resPointer];
                newArrPointer++;
            } else {
                res[resPointer] = newArr[newArrPointer];
            }
            newArrPointer++;
            resPointer++;
        }
        return res;
    }

    private void isThere2048() {
        for (int[] ints : grid) {
            for (int anInt : ints) {
                if (anInt == 2048) {
                    endOfGame = true;
                    return;
                }
            }
        }
    }

    private boolean isThere0() {
        for (int[] ints : grid) {
            for (int anInt : ints) {
                if (anInt == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private void logic() {
        Direction dir = controller.getDir();
        if (dir != Direction.NONE) {
            if (move(dir)) generateNewCell();
            else if (!move(dir) && !isThere0()) endOfGame = true;
        }
        isThere2048();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        GameGrid other = (GameGrid) obj;
        return Arrays.deepEquals(this.grid, other.grid);
    }

    @Override
    public String toString() {
        return Arrays.deepToString(this.grid);
    }

    public void run() {
        generateNewCell();
        generateNewCell();
        while(!endOfGame && !controller.isClosed) {
            logic();
        }
        System.out.printf("Game finished! Your score: %d", score);
    }
}

