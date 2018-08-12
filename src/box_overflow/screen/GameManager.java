package box_overflow.screen;

import box_overflow.inputs.InputManager;
import box_overflow.screen.render.Camera;
import box_overflow.screen.render.text.StaticFonts;
import box_overflow.screen.screens.GameScreen;
import box_overflow.screen.screens.MenuScreen;
import box_overflow.screen.screens.Screen;
import box_overflow.inputs.KeyboardManager;
import box_overflow.inputs.MouseManager;
import box_overflow.sound.SoundManager;
import box_overflow.util.TextManager;

/**
 * ScreenManager class.
 * This class is the screenManager class used to run the game screens.
 *
 * @author MightyCode
 * @version 1.1
 */
public class GameManager {

    /**
     * Current screen.
     * This variable contains the current screen displayed.
     */
    private Screen currentScreen;

    /**
     * Screen's states.
     * These static final variable counting the different state of screen.
     */
    public static final int MENUSCREEN = 0;
    public static final int GAMESCREEN = 1;

    /**
     * Text manager to manage every text on different language for the game.
     */
    public static TextManager textManager;

    /**
     * Sound manager to manage the sound on the game.
     */
    public static SoundManager soundManager;

    /**
     * Input manager to manage the inputs related to the actions of the player.
     */
    public static InputManager inputsManager;

    /**
     * Keyboard manager to manage the keyboard.
     */
    public static KeyboardManager keyboardManager;

    /**
     * Input manager to manage the mouse.
     */
    public static MouseManager mouseManager;

    /**
     * The camera of the game.
     */
    public static final Camera CAMERA = new Camera(0,0);

    /**
     * ScreenManager class constructor.
     * Instance the class and set the current screen.
     */
    public GameManager(int[][] input) {
        // Instance of the different manager
        textManager = new TextManager();
        soundManager = new SoundManager();
        inputsManager = new InputManager(input);
        keyboardManager = new KeyboardManager();
        mouseManager = new MouseManager();

        // Load the fist screen
        currentScreen = (new MenuScreen(this));
        CAMERA.setTween(0.25f,0.25f);
    }

    /**
     * Update the current screen.
     */
    public void update() {
        currentScreen.update();
        inputsManager.dispose();
    }

    /**
     * Display the current screen.
     */
    public void display() {
        currentScreen.display();
    }

    /**
     * Change the current screen.
     *
     * @param screen Set the new current screen.
     */
    public void setScreen(int screen) {
        currentScreenUnload();
        currentScreen = null;
        System.runFinalization();
        switch (screen) {
            case MENUSCREEN:
                currentScreen = (new MenuScreen(this));
                break;
            case GAMESCREEN:
                currentScreen = (new GameScreen(this));
                break;
        }
    }

    /**
     * Unload the currentScreen.
     */
    private void currentScreenUnload() {
        currentScreen.unload();
    }

    /**
     * Method call when the focus of the game change.
     */
    public void focus(boolean b){ currentScreen.focus(b);}

    /**
     * Unload the game before leaving.
     */
    public void unload() {
        currentScreen.unload();
        currentScreen = null;
        StaticFonts.unload();
    }

    public Screen getScreen(){
        return currentScreen;
    }
}