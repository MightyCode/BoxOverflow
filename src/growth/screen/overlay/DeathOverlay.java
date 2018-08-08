package growth.screen.overlay;

import growth.main.Window;
import growth.util.TextManager;
import growth.util.math.Color4;
import growth.util.math.Vec2;
import growth.entity.gui.GUIButton;
import growth.screen.render.shape.ShapeRenderer;
import growth.screen.render.text.FontRenderer;
import growth.screen.render.text.StaticFonts;
import growth.screen.GameManager;
import growth.screen.screens.GameScreen;
import growth.screen.screens.Screen;

/**
 * Death Overlay class.
 * This class is the death overlay class used when the player die.
 *
 * @author MightyCode
 * @version 1.1
 */
public class DeathOverlay extends Overlay {

    /**
     * Lose title texture.
     * This variable contains the texture's "title"  of the overlay.
     */
    private FontRenderer loose;

    /**
     * GUIButtons.
     */
    private GUIButton retry, quitter;

    /**
     * Death overlay class constructor.
     * Instance the class and set overlay's variables.
     */
    public DeathOverlay(Screen screen) {
        super(screen);

        // Init variables
        // Title
        loose = new FontRenderer(TextManager.DEATH,0, StaticFonts.IBM, Window.width*0.05f, new Vec2(), Color4.WHITE);
        loose.setPos(new Vec2(Window.width * 0.5f,Window.height * 0.20f));

        Vec2 size = new Vec2(Window.width / 4f, Window.height / 20f);
        Color4 backgroundColor = new Color4(0.40f, 0.65f, 0.65f, 0.5f);
        Color4 hoverColor = new Color4(0.40f, 0.65f, 0.65f, 0.95f);
        Color4 textColor = new Color4(0.8f, 0.8f, 0.8f, 1.0f);
        Color4 hoverTextColor = Color4.WHITE;

        retry = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height*0.45f),
                size,
                TextManager.DEATH,1,
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ) {
            @Override
            public void action() {
                Window.gameManager.setScreen(GameManager.GAMESCREEN);
            }
        };

        quitter = new GUIButton(
                new Vec2(Window.width *0.5f, Window.height*0.55f),
                size,
                TextManager.DEATH,2,
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ) {
            @Override
            public void action() {
                Screen.setState(GameScreen.STATE_NORMAL);
                Window.gameManager.setScreen(GameManager.MENUSCREEN);
            }
        };
    }

    /**
     * Update the overlay and its components.
     */
    public void update() {
        retry.update();
        quitter.update();
    }

    /**
     * Display the overlay.
     */
    public void display() {
        // Black rectangle
        ShapeRenderer.rectC(new Vec2(), new Vec2(Window.width, Window.height), new Color4(0.0f, 0.0f, 0.0f, 0.6f));
        ShapeRenderer.rectC(new Vec2(0.1f * Window.width, 0.15f * Window.height),
                new Vec2(0.8f * Window.width, 0.75f * Window.height), new Color4(0.0f, 0.0f, 0.0f, 0.5f));

        // Textures and button
        loose.render();

        retry.display();
        quitter.display();
    }

    /**
     * Unload the overlay.
     */
    public void unload() {
        retry.unload();
        quitter.unload();
        loose.unload();
    }
}