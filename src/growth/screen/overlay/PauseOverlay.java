package growth.screen.overlay;

import growth.main.Window;
import growth.util.TextManager;
import growth.util.math.Color4;
import growth.util.math.Vec2;
import growth.entity.gui.GUIButton;
import growth.screen.render.shape.ShapeRenderer;
import growth.screen.render.text.FontRenderer;
import growth.screen.render.text.StaticFonts;
import growth.screen.screens.GameScreen;
import growth.screen.screens.Screen;
import growth.screen.GameManager;

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
        pause = new FontRenderer(TextManager.PAUSE,0, StaticFonts.IBM, Window.width*0.05f, new Vec2(), Color4.WHITE);
        pause.setPos(new Vec2(Window.width * 0.5f, Window.height * 0.20f));

        Vec2 size = new Vec2(Window.width / 4f, Window.height / 20f);
        Color4 backgroundColor = new Color4(0.40f, 0.65f, 0.65f, 0.5f);
        Color4 hoverColor = new Color4(0.40f, 0.65f, 0.65f, 0.95f);
        Color4 textColor = new Color4(0.8f, 0.8f, 0.8f, 1.0f);
        Color4 hoverTextColor = Color4.WHITE;

        continuer = new GUIButton(
                new Vec2(Window.width / 2, Window.height/2.4f), size,
                TextManager.PAUSE,1,
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ){
            @Override
            public void action () {
                Screen.setState(GameScreen.STATE_NORMAL);
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
                hoverTextColor
        ){
            @Override
            public void action () {
                Screen.setState(GameScreen.STATE_OPTION);
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
                hoverTextColor
        ){
            @Override
            public void action () {
                Screen.setState(GameScreen.STATE_NORMAL);
                Window.gameManager.setScreen(GameManager.MENUSCREEN);
            }
        };
    }

    /**
     * Update the overlay and its components.
     */
    public void update(){
        if(GameManager.inputsManager.inputPressed(0)) {
            Screen.setState(GameScreen.STATE_NORMAL);
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
        ShapeRenderer.rectC(new Vec2(), new Vec2(Window.width, Window.height), new Color4(0.0f, 0.0f, 0.0f, 0.6f));
        ShapeRenderer.rectC(new Vec2(0.1f * Window.width, 0.15f * Window.height),
                new Vec2(0.8f * Window.width, 0.75f * Window.height), new Color4(0.0f, 0.0f, 0.0f, 0.5f));

        // Textures and button
        pause.render();

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