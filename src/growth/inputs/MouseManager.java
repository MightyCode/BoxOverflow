package growth.inputs;

import growth.main.Window;
import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

/**
 * This class is the mouse manager.
 *
 * @author MightyCode
 * @version 1.0
 */
public class MouseManager {

    /**
     * Input number.
     * This class contains the number of input in a keyboard.
     */
    private static final int MOUSE_BUTTONS = 8;

    /**
     * Buttons state.
     * This class contains the state of every keys.
     */
    private final boolean[] state = new boolean[MOUSE_BUTTONS];

    /**
     * Temp buttons state.
     * This class contains the state of every keys in the previous frame.
     */
    private final boolean[] tempState = new boolean[MOUSE_BUTTONS];

    /**
     * Mouse manager class.
     * Instance the class
     */
    public MouseManager(){
        for(int i = 0; i < MOUSE_BUTTONS; i++) {
            state[i] = false;
            tempState[i] = false;
        }
    }

    /**
     * Return the state of key called.
     *
     * @param buttonID button's ID.
     *
     * @return state of the button.
     */
    public static boolean button(int buttonID){
        return glfwGetMouseButton(Window.windowID, buttonID) == 1;
    }

    /**
     * Test if the button has just been pressed.
     *
     * @param buttonID Button's ID.
     * @return boolean
     */
    public boolean mousePressed(int buttonID){
        tempState[buttonID] = state[buttonID];
        state[buttonID] = glfwGetMouseButton(Window.windowID, buttonID) == 1;
        return state[buttonID] && !tempState[buttonID];
    }

    /**
     * Test if the key has just been released.
     *
     * @param buttonID Button's ID.
     * @return boolean
     */
    public boolean mouseReleased(int buttonID){
        tempState[buttonID] = state[buttonID];
        state[buttonID] = glfwGetMouseButton(Window.windowID, buttonID) == 1;
        return !state[buttonID] && tempState[buttonID];
    }

    /**
     * See the position of mouse x.
     *
     * @return mouse position x.
     */
    public static float mouseX(){
        DoubleBuffer x = BufferUtils.createDoubleBuffer(1);

        glfwGetCursorPos(Window.windowID, x, null);
        return (float)x.get(0);
    }

    /**
     * See the position of mouse y.
     *
     * @return mouse position y.
     */
    public static float mouseY(){
        DoubleBuffer y = BufferUtils.createDoubleBuffer(1);

        glfwGetCursorPos(Window.windowID, null, y);
        return (float)y.get(0);
    }
}