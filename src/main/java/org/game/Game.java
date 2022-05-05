package org.game;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.awt.image.BufferedImage;
import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Game {

    GameGrid grid;
    Controller controller;

    private static final int gridSize = 4;
    private final int windowSize = 1000;
    private final int tileSize = windowSize / gridSize;

    Game(GameGrid grid, Controller controller) {
        this.grid = grid;
        this.controller = controller;
    }

    // The window handle
    private long window;

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(windowSize, windowSize, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, new InputController(controller));

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        // Set the clear color
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( !glfwWindowShouldClose(window) && !controller.isClosed) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            drawGrid(grid);
            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }



    private BufferedImage setTexture(int n) {
        switch (n) {
            case 2:
                return TextureLoader.loadImage("/tile_2.jpg");
            case 4:
                return TextureLoader.loadImage("/tile_4.jpg");
            case 8:
                return TextureLoader.loadImage("/tile_8.jpg");
            case 16:
                return TextureLoader.loadImage("/tile_16.jpg");
            case 32:
                return TextureLoader.loadImage("/tile_32.png");
            case 64:
                return TextureLoader.loadImage("/tile_64.png");
            case 128:
                return TextureLoader.loadImage("/tile_128.png");
            case 256:
                return TextureLoader.loadImage("/tile_256.png");
            case 512:
                return TextureLoader.loadImage("/tile_512.png");
            case 1024:
                return TextureLoader.loadImage("/tile_1024.png");
            case 2048:
                return TextureLoader.loadImage("/tile_2048.png");

        }
        return TextureLoader.loadImage("/tile_undefined.jpg");
    }

    private void drawImage(int x, int y, int n) {
        BufferedImage im = setTexture(n);
        int id = TextureLoader.loadTexture(im);
        glBindTexture(GL_TEXTURE_2D, id);
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2d(convertX(x), convertY(y));

        glTexCoord2f(1, 0);
        glVertex2d(convertX(x + 1), convertY(y));

        glTexCoord2f(1, 1);;
        glVertex2d(convertX(x + 1), convertY(y + 1));

        glTexCoord2f(0, 1);
        glVertex2d(convertX(x), convertY(y + 1));
        glEnd();
        glDeleteTextures(id);
    }


    private void drawGrid(GameGrid grid) {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if(grid.getTile(x, y) == 0) continue;
                drawImage(y, x, grid.getTile(x, y));
            }
        }
    }

    private double convertX(double cord) {
        return -1 + (cord * tileSize) / (windowSize / 2.0);
    }

    private double convertY(double cord) {
        return 1 - (tileSize * cord) / (windowSize / 2.0);
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
        GameGrid grid = new GameGrid(gridSize, controller);
        Game game = new Game(grid, controller);
        Thread t = new Thread(grid::run);
        t.start();
        game.run();
        t.stop();
    }

}