package growth.screen.overlay;

import growth.main.Window;
import growth.screen.screens.Screen;

/**
 * Overlay abstract class.
 * This class is the overlay class use on screen.
 *
 * @author MightyCode
 * @version 1.0
 */
public abstract class Overlay {

    /**
     * The state of the overlay.
     */
    protected static int overlayState;
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
     * Set the state of the screen which have instance the overlay.
     */
    public static void setScreenState(int newsState){
        Screen.setState(newsState);
    }

    /**
     * Change the state of the overlay.
     * @param newState The new state.
     */
    public static  void setState(int newState){overlayState = newState;}

    /**
     * Free memory.
     */
    public abstract void unload();
}