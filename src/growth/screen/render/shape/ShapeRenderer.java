package growth.screen.render.shape;

import growth.util.math.Color4;
import growth.util.math.Vec2;
import growth.screen.GameManager;

import static org.lwjgl.opengl.GL11.*;

/**
 * Shape rendering abstract class.
 * This class is only call by the Render class to display different shape
 *
 * @author MightyCode
 * @version 1.1
 */
public class ShapeRenderer {

    /**
     * Display a rectangle using vec2
     * @param pos The position of the rectangle.
     * @param size The size of the rectangle.
     * @param color The color of the rectangle.
     */
    public static void rect(Vec2 pos, Vec2 size, Color4 color) {
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_TEXTURE);
        glColor4f(color.getR(), color.getG(), color.getB(), color.getA());
        glBegin(GL_QUADS);
        glVertex2d(pos.getX(), pos.getY());
        glVertex2d(pos.getX() + size.getX(), pos.getY());
        glVertex2d(pos.getX() + size.getX(), pos.getY() + size.getY());
        glVertex2d(pos.getX(), pos.getY() + size.getY());
        glEnd();
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_TEXTURE);
    }

    /**
     * Display a rectangle without taking into account the movement of the camera.
     * @param pos The position of the rectangle.
     * @param size The size of the rectangle.
     * @param color The color of the rectangle.
     */
    public static void rectC(Vec2 pos, Vec2 size, Color4 color) {
        rect(new Vec2(pos.getX() - GameManager.CAMERA.getPosX(), pos.getY() - GameManager.CAMERA.getPosY()), size, color);
    }
}
