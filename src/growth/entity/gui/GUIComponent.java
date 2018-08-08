package growth.entity.gui;

import growth.entity.Eobject.Edrawable;
import growth.inputs.MouseManager;
import growth.util.math.Vec2;

/**
 * GUIComponent class.
 * This class is mother class of all GUI components like a button.
 * All components have two state lock or unlock width different state.
 *
 * @author MightyCode
 * @version 1.1
 */
public abstract class GUIComponent extends Edrawable {

    /**
     * The state of the GUIComponent.
     */
    protected int GUIState;

    /**
     * The state of locking for the GUIComponent.
     */
    protected boolean lock;

    public static int UNHOVER = 0;
    public static int HOVER = 1;
    public static int HOVER_LOCK = 2;
    public static int UNHOVER_LOCK = 3;

    /**
     * The state of mouse's overing.
     */
    protected boolean mouseOver;

    /**
     * The class constructor with position and size
     * @param pos The position of GUIComponent.
     * @param size The size of GUIComponent.
     */
    public GUIComponent(Vec2 pos, Vec2 size){
        this.pos = pos;
        this.size = size;
    }

    /**
     * The class constructor with just size
     * @param size The size of GUIComponent.
     */
    public GUIComponent(Vec2 size){
        this.size = size;
    }

    /**
     * Override class for what does the GUIComponent.
     */
    protected void action(){}

    /**
     * Test if the mouse is over the GUIComponent.
     */
    protected boolean mouseOver() {
        return  (MouseManager.mouseX() > pos.getX() &&
                MouseManager.mouseX() < pos.getX() + size.getX()) &&
                (MouseManager.mouseY() > pos.getY() &&
                        MouseManager.mouseY() < pos.getY() + size.getY());
    }

    /**
     * The new state of the button.
     * @param newState The new state.
     */
    public void setMouseOver(boolean newState){ mouseOver = newState;}

    /**
     * Set the lock state of the GUIComponent.
     * @param newState The new locking state.
     */
    public void setLock(boolean newState){ lock = newState; }

    /**
     * Set the new state of the GUIComponent.
     * @param newState The new state.
     */
    public void setState(int newState){ GUIState = newState; }

    /**
     * Surcharge method of setState() to use with boolean attribute.
     * @param newState The new state.
     */
    public void setState(boolean newState){ GUIState = (newState)? 1 : 0; }

    /**
     * Set the position  of the GUIComponents.
     * @param pos The new position.
     */
    public void setPos(Vec2 pos) { this.pos = pos.copy(); }

    public void unload(){
        super.unload();
    }
}
