package org.game;

import com.sun.tools.jconsole.JConsoleContext;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class InputController extends GLFWKeyCallback {

    Controller controller;

    InputController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW.GLFW_PRESS) {
            switch (key) {
                case GLFW.GLFW_KEY_RIGHT:
                    controller.setDir(Direction.RIGHT);
                    break;
                case GLFW.GLFW_KEY_LEFT:
                    controller.setDir(Direction.LEFT);
                    break;
                case GLFW.GLFW_KEY_UP:
                    controller.setDir(Direction.UP);
                    break;
                case GLFW.GLFW_KEY_DOWN:
                    controller.setDir(Direction.DOWN);
                    break;
                case GLFW.GLFW_KEY_ESCAPE:
                    controller.close();
                    break;
            }
        }
    }
}
