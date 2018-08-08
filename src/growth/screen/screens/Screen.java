package growth.screen.screens;

import growth.screen.GameManager;

/**
 * Screen class.
 * This class is the basic architecture of all screens.
 *
 * @author MightyCode
 * @version 1.1
 */
public abstract class Screen {

    /**
     * ScreenManger.
     * This variable contains the screen manager to interact with it.
     */
    protected GameManager screenManager;

    /**
     * Screen state.
     * This variable contains the different states of game.
     */
    protected static int screenState = 0;

    /**
     * Screen class constructor.
     * Instance the class and set the screenManager reference.
     *
     * @param gameManager Reference of screenManager.
     */
    public Screen(GameManager gameManager) {
        this.screenManager = gameManager;
    }

    /**
     * Base architecture of displaying method
     */
    public abstract void display();
    /**
     * Base architecture of updating method
     */
    public abstract void update();

    /**
     * Base architecture of setting screen method
     */
    public void setScreen(int newScreen){
        screenManager.setScreen(newScreen);
    }

    /**
     * Base architecture of setting state method
     */
    public static void setState(int newState){
        screenState = newState;
    }

    /**
     * Call the screen if the focus of the window change.
     * @param b The focus.
     */
    public void focus(boolean b){}

    /**
     * Base architecture of unloading method
     */
    public void unload() {
    }
}