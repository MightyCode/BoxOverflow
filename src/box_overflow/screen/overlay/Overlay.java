package box_overflow.screen.overlay;

import box_overflow.main.Window;
import box_overflow.screen.screens.Screen;

/**
 * Overlay abstract class.
 * This class is the overlay class use on screen.
 *
 * @author MightyCode
 * @version 1.0
 */
public abstract class Overlay {

    /**
     * The endState of the overlay.
     */
    public int overlayState;
    /**
     * Screen.
     * This variable contains the screen which have instance this overlay.
     */
    protected final Screen screen;

    /**
     * Overlay abstract class constructor.
     * Instance the child class and set overlay's child variables.
     */
    Overlay(Screen screen) {
        this.screen = screen;
        overlayState = 0;
    }

    /**
     * Update the overlay.
     */
    public abstract void update();

    /**
     * Update the overlay.
     */
    public abstract void display();

    /**
     * Set the screen.
     */
    public void setScreen(int newScreen){
        Window.gameManager.setScreen(newScreen);
    }

    /**
     * Set the endState of the screen which have instance the overlay.
     */
    public static void setScreenState(int newsState){
        Window.gameManager.setState(newsState);
    }

    /**
     * Change the endState of the overlay.
     * @param newState The new endState.
     */
    public void setState(int newState){overlayState = newState;}

    /**
     * Free memory.
     */
    public abstract void unload();
}