package growth.entity.gui;

import growth.screen.render.Animation;
import growth.util.math.Color4;
import growth.util.math.Vec2;
import growth.screen.render.text.FontFace;
import growth.screen.render.text.FontRenderer;
import growth.screen.render.texture.TextureRenderer;
import growth.screen.GameManager;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

/**
 * GUICheck box class.
 * This class is the structure of the check box.
 * The check box have two state and action, one for checking and one for unchecking.
 *
 * @author MightyCode
 * @version 1.0
 */
public class GUICheckBox extends GUIComponent {

    public static int HOVER_CHECK = 5;
    public static int UNHOVER_CHECK = 4;

    /**
     * The colors of text of the check box.
     */
    private Color4 textColor;
    private Color4 hoverTextColor;

    /**
     * The font renderer use by the check box.
     */
    private FontRenderer fontRenderer;

    /**
     * GUICheck box class constructor with the number of the global string.
     *
     * @param pos Position of the button.
     * @param size Size of the button.
     * @param number The number of the global string to display.
     * @param font The font use by its font renderer.
     * @param textColor The normal text color.
     * @param hoverTextColor The color of the text when the button is hovering.
     */
    public GUICheckBox(Vec2 pos, Vec2 size, int screen,  int number, FontFace font, Color4 textColor, Color4 hoverTextColor){
        super(new Vec2(pos.getX() - (size.getX() / 2), pos.getY() - (size.getY() / 2)),size);

        // Loading texture
        animations.add(new Animation("/textures/menu/GUICheckBox-uncheck.png"));
        animations.add(new Animation("/textures/menu/GUICheckBox-uncheckHover.png"));
        // For lock
        animations.add(null);
        animations.add(null);
        animations.add(new Animation("/textures/menu/GUICheckBox-check.png"));
        animations.add(new Animation("/textures/menu/GUICheckBox-checkHover.png"));

        this.textColor = textColor;
        this.hoverTextColor = hoverTextColor;

        fontRenderer = new FontRenderer(screen,number, font, size.getY()*0.4f, pos, textColor);
        fontRenderer.setPos(new Vec2(this.pos.getX() + size.getX()/2, this.pos.getY()+size.getX()*0.1f));
    }

    /**
     * Update the button.
     */
    public void update() {
        if (lock) return;
        mouseOver = mouseOver();

        if(mouseOver){
            if(GUIState == 1){
                animationPlayed = HOVER_CHECK;
            } else{
                animationPlayed = HOVER;
            }

            fontRenderer.setColor(hoverTextColor);
            if(GameManager.mouseManager.mousePressed(GLFW_MOUSE_BUTTON_LEFT)) {
                GUIState = (GUIState == 1)? 0 : 1;
                action();
            }
        } else{
            if(GUIState == 1){
                animationPlayed = UNHOVER_CHECK;
            } else{
                animationPlayed = UNHOVER;
            }

            fontRenderer.setColor(textColor);
        }
    }

    /**
     * Display the button.
     */
    public void display() {
        animations.get(animationPlayed).bind();
        TextureRenderer.imageC(pos,size,1f);
        fontRenderer.render();
    }

    /**
     * Free the memory.
     */
    public void unload(){
        super.unload();
        fontRenderer.unload();
    }
}
