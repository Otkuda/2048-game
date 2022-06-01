package org.game2048.controller;

import org.game2048.controller.Controller;
import org.game2048.model.Direction;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class InputController extends GLFWKeyCallback {

    Controller controller;

    public InputController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW.GLFW_PRESS) {
            switch (key) {
                case GLFW.GLFW_KEY_RIGHT:
                    controller.moveGrid(Direction.RIGHT);
                    controller.abilitySwitch();
                    break;
                case GLFW.GLFW_KEY_LEFT:
                    controller.moveGrid(Direction.LEFT);
                    controller.abilitySwitch();
                    break;
                case GLFW.GLFW_KEY_UP:
                    controller.moveGrid(Direction.UP);
                    controller.abilitySwitch();
                    break;
                case GLFW.GLFW_KEY_DOWN:
                    controller.moveGrid(Direction.DOWN);
                    controller.abilitySwitch();
                    break;
                case GLFW.GLFW_KEY_ESCAPE:
                    controller.close();
                    break;
                case GLFW.GLFW_KEY_P:
                    if (controller.abilityToChange) {
                        controller.changePlayer();
                        controller.abilitySwitch();
                    }
                    break;
                case GLFW.GLFW_KEY_R:
                    controller.restart();
                    break;
            }
        }
    }
}
