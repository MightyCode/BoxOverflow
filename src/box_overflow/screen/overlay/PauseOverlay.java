package box_overflow.screen.overlay;

import box_overflow.main.Window;
import box_overflow.util.TextManager;
import box_overflow.util.math.Color4;
import box_overflow.util.math.Vec2;
import box_overflow.entity.gui.GUIButton;
import box_overflow.screen.render.shape.ShapeRenderer;
import box_overflow.screen.render.text.FontRenderer;
import box_overflow.screen.render.text.StaticFonts;
import box_overflow.screen.screens.GameScreen;
import box_overflow.screen.screens.Screen;
import box_overflow.screen.GameManager;

/**
 * Pause Overlay class.
 * This class is the pause overlay class use in the game.
 *
 * @author MightyCode
 * @version 1.1
 */
public class PauseOverlay extends Overlay{

    /**
     * Pause title texture.
     * This variable contains the texture's "title"  of the overlay.
     */
    private FontRenderer pause;

    /**
     * The GUIButton use on the overlay.
     */
    private GUIButton continuer, options,  quitter;

    /**
     * Pause overlay class constructor.
     * Instance the class and set overlay's variables.
     */
    public PauseOverlay(Screen screen){
        super(screen);
        // Init variable

        // Title
        pause = new FontRenderer(TextManager.PAUSE,0, StaticFonts.IBM, Window.width*0.05f, new Vec2(), new Color4(1,1,1,0.8f));
        pause.setPos(new Vec2(Window.width * 0.5f, Window.height * 0.20f));

        Vec2 size = new Vec2(Window.width * 0.25f, Window.height * 0.05f);
        Color4 backgroundColor = new Color4(0.40f, 0.65f, 0.65f, 0.5f);
        Color4 hoverColor = new Color4(0.40f, 0.65f, 0.65f, 0.95f);
        Color4 textColor = new Color4(0.8f, 0.8f, 0.8f, 0.8f);
        Color4 hoverTextColor = Color4.WHITE;

        continuer = new GUIButton(
                new Vec2(Window.width / 2, Window.height/2.4f), size,
                TextManager.PAUSE,1,
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor,this
        ){
            @Override
            public void action () {
                GameScreen.lvm.retur();
                Window.gameManager.setState(GameScreen.STATE_NORMAL);
            }
        };

        options = new GUIButton(
                new Vec2(Window.width / 2, Window.height/2f),
                size,
                TextManager.PAUSE,2,
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor,this
        ){
            @Override
            public void action () {
                Window.gameManager.setState(GameScreen.STATE_OPTION);
            }
        };

        quitter = new GUIButton(
                new Vec2(Window.width / 2, Window.height/1.71f),
                size,
                TextManager.PAUSE,3,
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor,this
        ){
            @Override
            public void action () {
                Window.gameManager.setScreen(GameManager.MENUSCREEN);
            }
        };
    }

    /**
     * Update the overlay and its components.
     */
    public void update(){
        if(GameManager.inputsManager.inputPressed(0)) {
            Window.gameManager.setState(GameScreen.STATE_NORMAL);
        }

        continuer.update();
        options.update();
        quitter.update();
    }

    /**
     * Update the overlay.
     */
    public void display(){
        // Black rectangle
        ShapeRenderer.rectC(new Vec2(), new Vec2(Window.width, Window.height), new Color4(0.0f, 0.0f, 0.0f, 0.3f));
        ShapeRenderer.rectC(new Vec2(0.1f * Window.width, 0.15f * Window.height),
                new Vec2(0.8f * Window.width, 0.75f * Window.height), new Color4(0.0f, 0.0f, 0.0f, 0.2f));

        // Textures and button
        pause.renderC();

        continuer.display();
        options.display();
        quitter.display();
    }

    /**
     * Unload the overlay.
     */
    public void unload(){
        continuer.unload();
        options.unload();
        quitter.unload();
        pause.unload();
    }
}