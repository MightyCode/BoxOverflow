package box_overflow.screen.render;

import box_overflow.entity.Eobject.Emoveable;
import box_overflow.main.Window;
import box_overflow.screen.screens.GameScreen;
import box_overflow.util.math.Color4;
import box_overflow.util.math.Vec2;
import box_overflow.screen.render.shape.ShapeRenderer;

import static org.lwjgl.opengl.GL11.glTranslatef;

/**
 * Screen class.
 * This class is the basic architecture of all screens.
 *
 * @author MightyCode
 * @version 1.0
 */
public class Camera {
    /**
     * Map position x.
     * This variable contains the position y of the beginning of the rendering of the map.
     */
    private int posX;

    /**
     * Map position y.
     * This variable contains the position y of the beginning of the rendering of the map.
     */
    private int posY;

    /**
     * Tween.
     * This variable contains the smooth movement of camera
     * 1 -> rigid and immediate set new camera position.
     */
    private float tweenX;

    /**
     * Tween.
     * This variable contains the smooth movement of camera
     * 1 -> rigid and immediate set new camera position.
     */
    private float tweenY;


    /**
     * The entity that will follow the camera.
     */
    private Emoveable user;

    private boolean breake;


    /**
     * Minimal position x of camera.
     * This variable contains the minimal position x of the camera use to stop the scrolling.
     */
    private int xMin;
    /**
     * Minimal position y of camera.
     * This variable contains the minimal position y of the camera use to stop the scrolling.
     */

    private int yMin;

    /**
     * Maximal position x of camera.
     * This variable contains the maximal position x of the camera use to stop the scrolling.
     */
    private int xMax;

    /**
     * Maximal position y of camera.
     * This variable contains the maximal position y of the camera use to stop the scrolling.
     */
    private int yMax;

    public Camera(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }

    public void setPosition(boolean isTween){
        if(breake){
            breake = false;
            return;
        }
        int posX = (int)( Window.width / 2 - user.getPos().getX()*GameScreen.tile);
        int posY = (int)( Window.height / 2 - user.getPos().getY()*GameScreen.tile);
        setPosition(posX, posY, isTween);
        breake = false;
    }

    public void setPosition(int posX, int posY, boolean isTween){
        breake = true;
        float newTweenX = (isTween)? tweenX : 1;
        float newTweenY = (isTween)? tweenY : 1;

        glTranslatef((int)((posX - this.posX) * newTweenX),(int)((posY - this.posY) * newTweenY),0);

        this.posX += (int)((posX - this.posX) * newTweenX);
        this.posY += (int)((posY - this.posY) * newTweenY);
        //fixBounds();
    }

    /*
    private void fixBounds() {
        if(posX > xMin){
            glTranslatef(xMin - posX,0,0);
            posX = xMin;
        } else if (posX < xMax){
            glTranslatef(xMax - posX,0,0);
            posX = xMax;
        }

        if(posY > yMin){
            glTranslatef(0,yMin - posY,0);
            posY = yMin;
        } else if (posY < yMax){
            glTranslatef(0, yMax - posY ,0);
            posY = yMax;
        }
    }*/


    /**
     * Set the camera tween.
     *
     * @param tweenX Set the new tween in width.
     * @param tweenY Set the new tween in height.
     */
    public void setTween(float tweenX, float tweenY) {
        this.tweenX = tweenX;
        this.tweenY = tweenY;
    }

    /**
     * Change the entity that will follow the camera.
     *
     * @param newUser The entity that will follow the camera.
     */
    public void setEntityToCamera(Emoveable newUser){
        this.user = newUser;
    }

    /**
     * Set the maximum corner.
     *
     * @param xMax The position x of the maximum.
     * @param yMax The position y of the maximum.
     */
    public void setBoundMax(int xMax, int yMax){
        this.xMax = xMax;
        this.yMax = yMax;
    }

    /**
     * Set the minimum corner.
     *
     * @param xMin The position x of the minimum.
     * @param yMin The position y of the minimum.
     */
    public void setBoundMin(int xMin, int yMin){
        this.xMin = xMin;
        this.yMin = yMin;
    }

    /**
     * Set the map position x.
     *
     * @param posX New position x of the camera.
     */
    public void setPosX(int posX){
        glTranslatef(posX - this.posX , 0,0);
    }

    /**
     * Set the map position y.
     *
     * @param posY New position y of the camera.
     */
    public void setPosY(int posY){
        glTranslatef(0, posY - this.posY ,0);
        this.posY = posY;
    }

    /**
     * Return the map position x.
     *
     * @return position x
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Return the map position y.
     *
     * @return Position y.
     */
    public int getPosY() {
        return posY;
    }
}
