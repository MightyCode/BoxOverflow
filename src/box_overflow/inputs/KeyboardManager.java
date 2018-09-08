package box_overflow.inputs;

import box_overflow.main.Window;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.glfwGetKey;

/**
 * This class is the keyboard manager.
 *
 * @author MightyCode
 * @version 1.0
 */
public class KeyboardManager {

    /**
     * Key number.
     * This variable contains the number of input in a keyboard.
     */
    private static final int Keys = 512;

    /**
     * Keys state.
     * This class contains the state of every keys.
     */
    private final boolean[] state = new boolean[Keys];

    /**
     * Temp keys state.
     * This class contains the state of every keys in the previous frame.
     */
    private final boolean[] tempState = new boolean[Keys];

    /**
     * Keyboard manager class.
     * Instance the class.
     */
    public KeyboardManager(){
        Arrays.fill(state, false);
        Arrays.fill(tempState, false);
    }

    /**
     * Return the state of key called.
     *
     * @param keyID Key's ID.
     *
     * @return State of the key.
     */
    public static boolean key(int keyID){
        return glfwGetKey(Window.windowID, keyID) == 1;
    }

    /**
     * Test if the key has just been pressed.
     *
     * @param keyID Key's ID.
     * @return boolean
     */
    public boolean keyPressed(int keyID){
        if((state[keyID] && !tempState[keyID])){
            tempState[keyID] = true;
            return true;
        }
        return false;
    }

    /**
     * Test if the key has just been released.
     *
     * @param keyID Key's ID.
     * @return boolean
     */
    public boolean keyReleased(int keyID){
        return (!state[keyID] && tempState[keyID]);
    }

    public void dispose(int keyID){
        tempState[keyID] = state[keyID];
        state[keyID] = glfwGetKey(Window.windowID, keyID) == 1;
    }
}