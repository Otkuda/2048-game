package org.game;

public class Controller {
    private Direction dir = Direction.NONE;

    public Direction getDir() {
        Direction currDir = this.dir;
        this.dir = Direction.NONE;
        return currDir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }
}
