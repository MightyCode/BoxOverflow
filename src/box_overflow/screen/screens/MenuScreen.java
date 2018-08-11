package box_overflow.screen.screens;

import box_overflow.main.Box_Overflow;
import box_overflow.util.TextManager;
import box_overflow.util.math.Color4;
import box_overflow.util.math.Vec2;
import box_overflow.screen.render.Render;
import box_overflow.screen.render.text.FontRenderer;
import box_overflow.screen.render.text.StaticFonts;
import box_overflow.screen.GameManager;
import box_overflow.entity.gui.GUIButton;
import box_overflow.main.Window;
import box_overflow.screen.overlay.OptionOverlay;

/**
 * Menu class.
 * This class is the menu screen.
 *
 * @author MightyCode
 * @version 1.1
 */
public class MenuScreen extends Screen {

    /**
     * The font renderer for the menu title.
     */
    private FontRenderer title, admin;

    /**
     * GUIButtons used on the menu.
     */
    private GUIButton goToGame, newGame, chargeGame, options, quit;

    /**
     * The option overlay to change the current parameters.
     */
    private OptionOverlay option;

    /**
     * Menu screen class constructor.
     * Instance the menu and set the menu screen's variables.

     * @param gameManager Add gameManager to change the global screen.
     */
    public MenuScreen(GameManager gameManager) {
        super(gameManager);
        Render.setClearColor(new Color4(0.667f,0.459f,0.224f));

        title = new FontRenderer(TextManager.MENU,0, StaticFonts.monofonto, Window.width*0.06f,
                new Vec2(Window.width * 0.5f, Window.height * 0.13f), Color4.BLACK);

        if(Box_Overflow.admin) admin = new FontRenderer("Mode admin", StaticFonts.monofonto, Window.width*0.02f,
                new Vec2(Window.width * 0.5f, Window.height * 0.90f), new Color4(0.2f,0.2f,0.2f,0.9f));

        else admin = new FontRenderer("", StaticFonts.monofonto, 0,
                new Vec2(0, 0), new Color4(0,0,0,0));

        Vec2 size = new Vec2(Window.width / 4f, Window.height / 20f);
        Color4 backgroundColor = new Color4(0.0f, 0.0f, 0.0f, 0.0f);
        Color4 hoverColor = new Color4(0.0f, 0.0f, 0.0f, 0.2f);
        Color4 textColor = new Color4(0.2f, 0.2f, 0.2f, 1.0f);
        Color4 hoverTextColor = Color4.BLACK;

        goToGame = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.40f),
                size,
                TextManager.MENU,1,
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

        chargeGame = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.48f),
                size,
                TextManager.MENU,2,
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        );

        newGame = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.56f),
                size,
                TextManager.MENU,3,
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

        options = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.64f),
                size,
                TextManager.MENU,4,
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ) {
            @Override
            public void action() {
                MenuScreen.setState(1);
            }
        };

        quit = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.72f),
                size,
                TextManager.MENU,5,
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ) {
            @Override
            public void action() {
                Window.exit();
            }
        };

        // Load the option Overlay
        option = new OptionOverlay(this) {
            @Override
            public void quit() {
                Screen.setState(0);
            }
        };
    }

    /**
     * Update the menu.
     */
    public void update() {
        switch (screenState) {
            case 0:
                goToGame.update();
                newGame.update();
                chargeGame.update();
                options.update();
                quit.update();
                break;
            case 1:
                option.update();
                break;
        }

    }

    /**
     * Display the menu.
     */
    public void display() {
        Render.clear();
        switch (screenState) {
            case 0:
                title.render();
                admin.render();

                goToGame.display();
                newGame.display();
                chargeGame.display();
                options.display();
                quit.display();
                break;
            case 1:
                option.display();
                break;
        }
    }

    /**
     * Unload resources in menu to free memory.
     */
    public void unload() {
        // Unload buttons
        goToGame.unload();
        newGame.unload();
        chargeGame.unload();
        options.unload();
        quit.unload();
        title.unload();
        // Unload the overlay
        option.unload();
    }
}