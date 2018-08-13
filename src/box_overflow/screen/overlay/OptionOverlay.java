package box_overflow.screen.overlay;

import box_overflow.main.Config;
import box_overflow.main.Window;
import box_overflow.entity.gui.GUIButton;
import box_overflow.screen.render.text.FontRenderer;
import box_overflow.screen.render.texture.Texture;
import box_overflow.screen.render.texture.TextureRenderer;
import box_overflow.util.TextManager;
import box_overflow.util.math.Color4;
import box_overflow.util.math.Vec2;
import box_overflow.screen.render.Render;
import box_overflow.entity.gui.GUICheckBox;
import box_overflow.screen.render.text.StaticFonts;
import box_overflow.screen.GameManager;
import box_overflow.screen.screens.Screen;

/**
 * Option Overlay class.
 * This class is use on game and on the main menu to change the options.
 *
 * @author MightyCode
 * @version 1.0
 */
public class OptionOverlay extends Overlay {

    /**
     * Font renderer to render the help text.q
     */
    private FontRenderer option;

    /**
     * GUIButton to choose the categories
     */
    private GUIButton general, quit;

    /**
     * Inputs for general
     */
    private GUICheckBox language, spew;

    private Texture background;

    /**
     * Option overlay class constructor.
     * Instance the class and set overlay's variables.
     */
    protected OptionOverlay(Screen screen){
        super(screen);

        Vec2 size = new Vec2(Window.width / 4f, Window.height / 20f);
        Color4 backgroundColor = new Color4(0.0f, 0.0f, 0.0f, 0.0f);
        Color4 hoverColor = new Color4(0.0f, 0.0f, 0.0f, 0.2f);
        Color4 textColor = new Color4(0.2f, 0.2f, 0.2f, 1.0f);
        Color4 hoverTextColor = Color4.BLACK;

        general = new GUIButton(
                new Vec2(Window.width * 0.50f, Window.height * 0.3f), size,
                TextManager.OPTIONS,0, StaticFonts.monofonto, backgroundColor, hoverColor, textColor, hoverTextColor,this
        ) {
            @Override public void action() {
                lock = true;
                overlay.setState(0);
            }
        };

        general.setMouseOver(true);
        general.setLock(true);

        language = new GUICheckBox(
                new Vec2(Window.width*0.5f, Window.height*0.41f),
                new Vec2(Window.width*0.125f, Window.height*0.1f),
                TextManager.OPTIONS,4, StaticFonts.monofonto, textColor, hoverTextColor
        ){
            @Override
            public void action () {
                if(GUIState == 0){
                   GameManager.textManager.changeLanguage("fr");
                } else{
                    GameManager.textManager.changeLanguage("en");
                }
            }
        };
        language.setState(Config.getLanguage().equals("en"));

        option = new FontRenderer(TextManager.OPTIONS,8, StaticFonts.IBM, Window.height*0.1f,
                new Vec2(Window.width * 0.5f, Window.height * 0.05f), Color4.BLACK);

        spew = new GUICheckBox(
                new Vec2(Window.width*0.5f, Window.height*0.53f),
                new Vec2(Window.width*0.125f, Window.height*0.1f),
                TextManager.OPTIONS,3, StaticFonts.monofonto, textColor, hoverTextColor
        ){
            @Override
            public void action () {
                Config.setSpew(GUIState == 1);
            }
        };
        spew.setState(Config.getSpew()?1:0);

        quit = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.90f),
                size,
                TextManager.OPTIONS,5,
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ) {
            @Override
            public void action(){
                quit();
            }
        };
        background = new Texture("/textures/menu/background.png");
    }

    /**
     * Update the overlay and its components.
     */
    public void update() {
        if(GameManager.inputsManager.inputPressed(0)) quit();
        general.update();
        language.update();
        spew.update();
        quit.update();
    }

    /**
     * Display the overlay.
     */
    public void display() {
        Render.clear(new Color4(0.8f));
        background.bind();
        TextureRenderer.imageC(0,0,Window.width,Window.height);
        option.renderC();
        general.display();
        language.display();
        spew.display();
        quit.display();
    }

    /**
     * Override method to write what to do when you quit the overlay.
     */
    public void quit(){
    }

    /**
     * Unload the overlay.
     */
    public void unload() {
        System.out.println("\n-------------------------- \n");
        // Textures
        option.unload();

        // Main part buttons
        general.unload();

        // Language
        language.unload();
        /*musicVolume.unload();
        noiseVolume.unload();*/
        spew.unload();
        quit.unload();
        background.unload();
    }
}
