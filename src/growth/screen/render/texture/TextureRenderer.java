package growth.screen.render.texture;

import growth.util.math.Vec2;
import growth.screen.GameManager;

import static org.lwjgl.opengl.GL11.*;

/**
 * Texture rendering abstract class.
 * This class is only call by the Render class to display texture with gl11 technicals.
 *
 * @author MightyCode
 * @version 1.2
 */
public class TextureRenderer {

    /**
     * Display an texture on the screen.
     *
     * @param posX  Position x of the top-left corner image.
     * @param posY  Position y of the top-left corner image.
     * @param sizeX Image's width.
     * @param sizeY Image's height.
     */
    public static void image(float posX, float posY, float sizeX, float sizeY, float alpha) {
        glColor4f(1.f, 1.f, 1.f, alpha);

        glBegin(GL_QUADS);
        glTexCoord2f(0.f, 0.f);
        glVertex2f(posX, posY);

        glTexCoord2f(0.f, 1.f);
        glVertex2f(posX, posY + sizeY);

        glTexCoord2f(1.f, 1.f);
        glVertex2f(posX + sizeX, posY + sizeY);

        glTexCoord2f(1.f, 0.f);
        glVertex2f(posX + sizeX, posY);
        glEnd();
    }

    /**
     * Surcharge method without mark the alpha
     */
    public static void image(float posX, float posY, float sizeX, float sizeY) { image(posX, posY, sizeX, sizeY, 1.f); }

    /**
     * Surcharge method using Vec2 and without mark the alpha.
     */
    public static void image(Vec2 pos, Vec2 size) { image(pos.getX(), pos.getY(), size.getX(), size.getY(), 1.f); }

    /**
     * Surcharge method using Vec2.
     */
    public static void image(Vec2 pos, Vec2 size, float alpha) { image(pos.getX(), pos.getY(), size.getX(), size.getY(), alpha); }

    /**
     * Method to display an image without the camera translation and without mark alpha..
     */
    public static void imageC(float posX, float posY, float sizeX, float sizeY) { image(posX - GameManager.CAMERA.getPosX(), posY - GameManager.CAMERA.getPosY(), sizeX, sizeY, 1.f); }

    /**
     * Surcharge method with the alpha.
     */
    public static void imageC(float posX, float posY, float sizeX, float sizeY, float alpha) { image(posX - GameManager.CAMERA.getPosX(), posY - GameManager.CAMERA.getPosY(), sizeX, sizeY, alpha); }

    /**
     * Surcharge method using Vec2 and without mark the alpha.
     */
    public static void imageC(Vec2 pos, Vec2 size) { image(pos.getX() - GameManager.CAMERA.getPosX(), pos.getY() - GameManager.CAMERA.getPosY(), size.getX(), size.getY(), 1.f); }

    /**
     * Surcharge method using Vec2.
     */
    public static void imageC(Vec2 pos, Vec2 size, float alpha) { image(pos.getX() - GameManager.CAMERA.getPosX(), pos.getY() - GameManager.CAMERA.getPosY(), size.getX(), size.getY(), alpha); }

    /**
     * Display a part of a texture on the screen.
     *
     * @param posX  Position x of the top-left corner image.
     * @param posY  Position y of the top-left corner image.
     * @param sizeX Image's width.
     * @param sizeY Image's height.
     */
    public static void image(float posX, float posY, float sizeX, float sizeY, float fromX, float fromY, float toX, float toY, float alpha) {
        //glActiveTexture(GL_TEXTURE0);
        glColor4f(1f, 1f, 1f, alpha);

        glBegin(GL_QUADS);
        glTexCoord2f(fromX, fromY);
        glVertex2f(posX, posY);

        glTexCoord2f(fromX, toY);
        glVertex2f(posX, posY + sizeY);

        glTexCoord2f(toX, toY);
        glVertex2f(posX + sizeX, posY + sizeY);

        glTexCoord2f(toX, fromY);
        glVertex2f(posX + sizeX, posY);
        glEnd();
    }

    /**
     * Surcharge method without mark the alpha.
     */
    public static void image(float posX, float posY, float sizeX, float sizeY, float fromX, float fromY, float toX, float toY) {
        image(posX, posY, sizeX, sizeY, fromX, fromY, toX, toY, 1.f);
    }

    /**
     * Surcharge method using Vec2 and without mark the alpha.
     */
    public static void image(Vec2 pos, Vec2 size, Vec2 from, Vec2 to) {
        image(pos.getX(), pos.getY(), size.getX(), size.getY(), from.getX(), from.getY(), to.getX(), to.getY(), 1.f);
    }

    /**
     * Surcharge method using Vec2.
     */
    public static void image(Vec2 pos, Vec2 size, Vec2 from, Vec2 to, float alpha) {
        image(pos.getX(), pos.getY(), size.getX(), size.getY(), from.getX(), from.getY(), to.getX(), to.getY(), alpha);
    }

    /**
     * Method to display a part of the texture without the camera translation and without mark alpha..
     */
    public static void imageC(float posX, float posY, float sizeX, float sizeY, float fromX, float fromY, float toX, float toY) {
        image(posX - GameManager.CAMERA.getPosX(), posY - GameManager.CAMERA.getPosY(), sizeX, sizeY, fromX, fromY, toX, toY, 1.f);
    }

    /**
     * Surcharge method.
     */
    public static void imageC(float posX, float posY, float sizeX, float sizeY, float fromX, float fromY, float toX, float toY, float alpha) {
        image(posX - GameManager.CAMERA.getPosX(), posY - GameManager.CAMERA.getPosY(), sizeX, sizeY, fromX, fromY, toX, toY, alpha);
    }

    /**
     * Surcharge method using Vec2 and without mark the alpha.
     */
    public static void imageC(Vec2 pos, Vec2 size, Vec2 from, Vec2 to) {
        image(pos.getX() - GameManager.CAMERA.getPosX(), pos.getY() - GameManager.CAMERA.getPosY(), size.getX(), size.getY(),
                from.getX(), from.getY(), to.getX(), to.getY(), 1.f);
    }

    /**
     * Surcharge method using Vec2.
     */
    public static void imageC(Vec2 pos, Vec2 size, Vec2 from, Vec2 to, float alpha) {
        image(pos.getX() - GameManager.CAMERA.getPosX(), pos.getY() - GameManager.CAMERA.getPosY(), size.getX(), size.getY(),
                from.getX(), from.getY(), to.getX(), to.getY(), alpha);
    }

}
