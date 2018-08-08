package growth.inputs;

import growth.screen.GameManager;
import growth.screen.screens.GameScreen;

/**
 * This class is the input manager.
 *
 * @author MightyCode
 * @version 1.0
 */
public class InputManager {
    /**
     * Input type.
     * This table contains the type of the input for each action.
     */
    private int[] type;

    /**
     * Inputs value.
     * This table contains the value for each action.
     */
    private int[] inputs;

    /**
     * Input manager class.
     * Instance the class, set the input and its type.
     */
    public InputManager(int[][] input){
        inputs = new int[input.length];
        type = new int[input.length];
        for(int i = 0; i < input.length; i++){
            if(input[i][0] == -1){
                inputs[i] = input[i][1];
                type[i] = 1;
            } else {
                inputs[i] = input[i][0];
                type[i] = 0;
            }
        }
    }

    /**
     * Return the state of input called.
     *
     * @param inputs Inputs's ID.
     *
     * @return State of the input (boolean).
     */
    public boolean input(int inputs){
        if(type[inputs] == 0) return KeyboardManager.key(this.inputs[inputs]);
        else return MouseManager.button(this.inputs[inputs]);
    }

    /**
     * Test if the input has just been pressed.
     *
     * @param inputs Inputs's ID.
     * @return State of the input(boolean).
     */
    public boolean inputPressed(int inputs){
        if(type[inputs] == 0) return GameManager.keyboardManager.keyPressed(this.inputs[inputs]);
        else return GameManager.mouseManager.mousePressed(this.inputs[inputs]);
    }

    /**
     * Test if the input has just been released.
     *
     * @param inputs Iy's ID.
     * @return State of the input (boolean).
     */
    public boolean inputReleased(int inputs){
        if(type[inputs] == 0) return GameManager.keyboardManager.keyReleased(this.inputs[inputs]);
        else return GameManager.mouseManager.mouseReleased(this.inputs[inputs]);
    }

    /**
     * Test if the key has just been released.
     *
     * @param number The action for the new inputs.
     * @param newType The new type for the new action.
     * @param newValue The new value for the action.
     */
    public void setInput(int number, int newType, int newValue){
        type[number] = newType;
        inputs[number] = newValue;
    }

    public void dispose(){
        for(int i = 0; i < inputs.length ; i++) {
            if (type[i] == 0) {
                GameManager.keyboardManager.dispose(inputs[i]);
            }
        }
    }
}
