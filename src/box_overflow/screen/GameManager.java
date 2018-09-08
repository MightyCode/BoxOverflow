package box_overflow.screen;

import box_overflow.inputs.InputManager;
import box_overflow.screen.render.Camera;
import box_overflow.screen.render.text.StaticFonts;
import box_overflow.screen.render.texture.TextureManager;
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
     * These static final variable counting the different endState of screen.
     */
    public static final int MENUSCREEN = 0;
    public static final int GAMESCREEN = 1;

    /**
     * Different managers of the game
     */
    public static TextManager textManager;
    public static SoundManager soundManager;
    public static InputManager inputsManager;
    public static KeyboardManager keyboardManager;
    public static MouseManager mouseManager;
    public static TextureManager texManager;

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
        texManager = new TextureManager();

        // Load the fist screen
        currentScreen = (new MenuScreen(this));
        CAMERA.setTween(0.10f,0.10f);
    }

    /**
     * Update the current screen.
     */
    public void update() {
        if(inputsManager.inputPressed(7)) texManager.state();
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
        currentScreen.setState(0);
    }

    public void setState(int state) {
        currentScreen.setState(state);
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
        GameManager.soundManager.unload();
        texManager.endState();
    }

    public Screen getScreen(){
        return currentScreen;
    }
}