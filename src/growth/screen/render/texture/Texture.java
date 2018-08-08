/*
 * Copyright (c) 2018 Amaury Rehel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package growth.screen.render.texture;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;

/**
 * Basic texture class.
 * This class is the most basic texture class possible. It holds a OpenGL texture.
 *
 * Warning : Don't forget to use the clean() function when you do not use that texture anymore.
 *
 * @author yoctoctet
 * @version 1.1
 */
public class Texture {
    /**
     * Texture ID.
     * This variable contains the OpenGL texture ID, generated in the class constructor.
     */
    private int id;

    /**
     * Texture Width.
     * This variable contains the width of the loaded texture.
     */
    private int width;

    /**
     * Texture Width.
     * This variable contains the width of the loaded texture.
     */
    private int height;

    /**
     * Is texture loaded ?
     * This variable stores a boolean value that is false if the texture hasn't been loaded yet or has been unloaded and true if it has been loaded without error.
     */
    private boolean loaded;

    /**
     * Path.
     * This variables contains the path of texture charged.
     */
    private String path;

    /**
     * Texture class constructor. Empty.
     */
    public Texture() {
    }

    /**
     * Texture surcharge class constructor.
     * Instance the class and set the texture with the path.
     *
     * @param path Path to file's image to load.
     */
    public Texture(String path) {
        load(path);
    }

    /**
     * Texture surcharge class constructor.
     * Instance the class and set the texture with the bufferedImage.
     *
     * @param image BufferedImage to load.
     */
    public Texture(BufferedImage image) {
        createImage(image);
    }

    /**
     * Bind the loaded texture.
     */
    public void bind() {
        if (isTextureLoaded()) {
            glBindTexture(GL_TEXTURE_2D, id);
        } else {
            System.err.println("[Error] texture::bind() Binding a unloaded texture.");
        }
    }

    /**
     * Load a bufferedImage from a file.
     *
     * @param path Path of file to load.
     */
    public void load(String path) {
        try {
            this.path = path;
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(this.path));
            createImage(image);
        } catch (Exception e) {
            e.printStackTrace();
            unload();
        }
    }


    /**
     * Load a texture from a bufferedImage.
     *
     * @param image BufferedImage to load.
     */
    private void createImage(BufferedImage image) {
        id = glGenTextures();
        loaded = true;

        try {
            int[] pixels = new int[image.getHeight() * image.getWidth()];

            image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

            System.out.println("Texture num : " + id + " , loaded with path : " + path);

            ByteBuffer buffer = BufferUtils.createByteBuffer(image.getHeight() * image.getWidth() * 4);

            for (int i = 0; i < image.getHeight(); i++) {
                for (int j = 0; j < image.getWidth(); j++) {
                    int pixel = pixels[i * image.getWidth() + j];
                    buffer.put((byte) ((pixel >> 16) & 0xFF)); // RED
                    buffer.put((byte) ((pixel >> 8) & 0xFF)); // GREEN
                    buffer.put((byte) (pixel & 0xFF)); // BLUE
                    buffer.put((byte) ((pixel >> 24) & 0xFF)); // ALPHA
                }
            }

            buffer.flip();

            this.width = image.getWidth();
            this.height = image.getHeight();

            setParam(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
            setParam(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
            setParam(GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            setParam(GL_TEXTURE_MAG_FILTER, GL_LINEAR);

            upload(buffer);
        } catch (Exception e) {
            e.printStackTrace();
            unload();
        }
    }

    /**
     * Upload the image to OpenGL.
     *
     * @param image Image to upload.
     */
    private void upload(ByteBuffer image) {
        bind();
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
    }

    /**
     * Set OpenGL Texture parameter.
     *
     * @param param Parameter name.
     * @param value Parameter value.
     */
    private void setParam(int param, int value) {
        bind();
        glTexParameteri(GL_TEXTURE_2D, param, value);
    }

    /**
     * Delete the texture.
     */
    public void unload() {
        if (isTextureLoaded()) {
            glDeleteTextures(id);
            loaded = false;
            System.out.println("Texture num : " + id + " , unloaded.");
        } else {
            System.err.println("[Error] texture::unload() Unloading an already unloaded texture.");
        }
    }

    /**
     * Return if the texture already has been loaded.
     *
     * @return loaded
     */
    private boolean isTextureLoaded() {
        return loaded;
    }

    /**
     * Return texture ID.
     *
     * @return id
     */
    public int getID() {
        return id;
    }

    /**
     * Return texture width.
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Return texture height.
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }
}
