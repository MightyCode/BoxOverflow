package box_overflow.screen.render;

import box_overflow.entity.Eobject.Emoveable;
import box_overflow.entity.type.Player;
import box_overflow.main.Window;
import box_overflow.screen.screens.GameScreen;

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

    private boolean breaks;

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
        if(breaks){
            breaks = false;
            return;
        }

        int posX = (int)( Window.width / 2 - user.getPos().getX()*GameScreen.tile + ((Player)user).getDelta().getX());
        int posY = (int)( Window.height / 2 - user.getPos().getY()* GameScreen.tile + ((Player)user).getDelta().getY ());
        setPosition(posX, posY, isTween);
        breaks = false;
    }

    public void setPosition(float posX, float posY, boolean isTween){
        breaks = true;
        float newTweenX = (isTween)? tweenX : 1;
        float newTweenY = (isTween)? tweenY : 1;

        glTranslatef((int)((posX - this.posX) * newTweenX),(int)((posY - this.posY) * newTweenY),0);

        this.posX += (int)((posX - this.posX) * newTweenX);
        this.posY += (int)((posY - this.posY) * newTweenY);
    }

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
