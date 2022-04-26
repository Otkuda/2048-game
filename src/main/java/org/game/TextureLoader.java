package org.game;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;

public class TextureLoader {
    private static final int BYTES_PER_PIXEL = 4;




    public static int loadTexture(BufferedImage im) {
        int[] pixels = new int[im.getHeight() * im.getWidth()];
        pixels = im.getRGB(0,0,im.getWidth(),im.getHeight(), pixels,0,im.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer(im.getHeight() * im.getWidth() * BYTES_PER_PIXEL);
        for(int y = 0; y < im.getHeight(); y++) {
            for(int x = 0; x < im.getWidth(); x++) {
                int pixel = pixels[y * im.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xff)); // Красный
                buffer.put((byte) ((pixel >> 8) & 0xff)); // Зеленый
                buffer.put((byte) ((pixel & 0xff))); // Синий
                buffer.put((byte) ((pixel >> 24) & 0xff)); // Альфа - прозрачность
            }
        }
        buffer.flip();

        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, im.getWidth(), im.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        return textureID;

    }

    public static BufferedImage loadImage(String loc)
    {
        try {
            return ImageIO.read(Game.class.getResource(loc));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
