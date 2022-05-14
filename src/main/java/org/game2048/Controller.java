package org.game2048;

public class Controller {
    private Direction dir = Direction.NONE;
    public boolean isClosed = false;
    public boolean player = false;
    public boolean abilityToChange = true;

    public Direction getDir() {
        Direction currDir = this.dir;
        this.dir = Direction.NONE;
        return currDir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public void close() {isClosed = true;}

    public void changePlayer() {player = true;}

    public void abilitySwitch() {
        abilityToChange = false;
    }
}
