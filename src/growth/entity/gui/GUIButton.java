package growth.entity.gui;

import growth.util.math.Color4;
import growth.util.math.Vec2;
import growth.screen.render.shape.ShapeRenderer;
import growth.screen.render.text.FontFace;
import growth.screen.render.text.FontRenderer;
import growth.screen.GameManager;

import static org.lwjgl.glfw.GLFW.*;

/**
 * GUIButton class.
 * This class is the structure of the click button.
 * The button have two state, one for mouse's hover and one for not.
 *
 * @author MightyCode
 * @version 1.1
 */
public class GUIButton extends GUIComponent {

    /**
     * The colors of the button.
     */
    private Color4 backgroundColor;
    private Color4 hoverColor;

    /**
     * The colors of text of the button.
     */
    private Color4 textColor;
    private Color4 hoverTextColor;

    /**
     * The font renderer use by the button.
     */
    private FontRenderer fontRenderer;

    /**
     * GUIButton class constructor with the string to display.
     *
     * @param pos Position of the button.
     * @param size Size of the button.
     * @param text The text to display.
     * @param font The font use by its font renderer.
     * @param backgroundColor The background color of the button.
     * @param hoverColor The color of the button on its hovering state.
     * @param textColor The normal text color.
     * @param hoverTextColor The color of the text when the button is hovering.
     */
    public GUIButton(Vec2 pos, Vec2 size, String text, FontFace font, Color4 backgroundColor, Color4 hoverColor, Color4 textColor, Color4 hoverTextColor) {
        super(size);

        this.backgroundColor = backgroundColor;
        this.hoverColor = hoverColor;
        this.textColor = textColor;
        this.hoverTextColor = hoverTextColor;

        fontRenderer = new FontRenderer(text, font, size.getY() - 6, pos, textColor);
        setPos(pos);
    }

    /**
     * GUIButton class constructor with the number of the global string.
     *
     * @param pos Position of the button.
     * @param size Size of the button.
     * @param number The number of the global string to display.
     * @param font The font use by its font renderer.
     * @param backgroundColor The background color of the button.
     * @param hoverColor The color of the button on its hovering state.
     * @param textColor The normal text color.
     * @param hoverTextColor The color of the text when the button is hovering.
     */
    public GUIButton(Vec2 pos, Vec2 size, int screen, int number, FontFace font, Color4 backgroundColor, Color4 hoverColor, Color4 textColor, Color4 hoverTextColor) {
        super(size);

        this.backgroundColor = backgroundColor;
        this.hoverColor = hoverColor;
        this.textColor = textColor;
        this.hoverTextColor = hoverTextColor;

        fontRenderer = new FontRenderer(screen, number, font, size.getY() - 6, pos, textColor);
        setPos(pos);
    }

    /**
     * Update the button.
     */
    public void update() {
        if(lock) return;
        mouseOver = mouseOver();

        if(mouseOver){
            fontRenderer.setColor(hoverTextColor);
            if(GameManager.mouseManager.mousePressed(GLFW_MOUSE_BUTTON_LEFT)) {
                action();
            }
        } else{
            fontRenderer.setColor(textColor);
        }
    }

    /**
     * Display the button.
     */
    public void display() {
        if (!mouseOver) {
            ShapeRenderer.rectC(pos, size, backgroundColor);
            fontRenderer.render();
        } else {
            ShapeRenderer.rectC(pos, size, hoverColor);
            fontRenderer.render();
        }
    }

    /**
     * Set the new position of the button and its font renderer.
     * @param pos The new position.
     */
    public void setPos(Vec2 pos) {
        super.setPos(new Vec2(pos.getX() - (size.getX() / 2), pos.getY() - (size.getY() / 2)));
        fontRenderer.setPos(pos);
    }

    /**
     * Free the memory.
     */
    public void unload(){
        super.unload();
        fontRenderer.unload();
    }
}