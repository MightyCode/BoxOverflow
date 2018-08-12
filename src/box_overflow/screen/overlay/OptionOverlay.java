package box_overflow.screen.overlay;

import box_overflow.main.Config;
import box_overflow.main.Window;
import box_overflow.entity.gui.GUIButton;
import box_overflow.entity.gui.GUISlider;
import box_overflow.screen.render.text.FontRenderer;
import box_overflow.sound.SoundManager;
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
    private GUIButton general, video, inputs;

    /**
     * Inputs for general
     */
    private GUICheckBox language, spew;
    private GUISlider musicVolume;
    private GUISlider noiseVolume;

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
                new Vec2(Window.width * 0.2f, Window.height * 0.3f), size,
                TextManager.OPTIONS,0, StaticFonts.monofonto, backgroundColor, hoverColor, textColor, hoverTextColor
        ) {
            @Override public void action() {
                lock = true;
                video.setLock(false);
                inputs.setLock(false);
                Overlay.setState(0);
            }
        };

        general.setMouseOver(true);
        general.setLock(true);

        video = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.3f),
                size, TextManager.OPTIONS,1, StaticFonts.monofonto, backgroundColor, hoverColor, textColor, hoverTextColor
        ) {
            @Override public void action() {
                lock = true;
                general.setLock(false);
                inputs.setLock(false);
                Overlay.setState(1);
            }
        };

        inputs = new GUIButton(
                new Vec2(Window.width * 0.8f, Window.height * 0.3f),
                size, TextManager.OPTIONS,2, StaticFonts.monofonto, backgroundColor, hoverColor, textColor, hoverTextColor
        ) {
            @Override public void action() {
                lock = true;
                video.setLock(false);
                video.setLock(false);
                Overlay.setState(2);
            }
        };

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

        musicVolume = new GUISlider(
                new Vec2(Window.width*0.20f, Window.height*0.41f) ,
                new Vec2(Window.width*0.125f, Window.height*0.1f),
                TextManager.OPTIONS,6, StaticFonts.monofonto, textColor, hoverTextColor,0,100, Config.getMusicVolume()
        ){
            @Override
            public void action () {
                SoundManager.setMusicVolume((int)value);
            }
        };

        noiseVolume = new GUISlider(
                new Vec2(Window.width*0.20f, Window.height*0.55f) ,
                new Vec2(Window.width*0.125f, Window.height*0.1f),
                TextManager.OPTIONS,7, StaticFonts.monofonto, textColor, hoverTextColor,0,100, Config.getNoiseVolume()
        ){
            @Override
            public void action () {
                SoundManager.setNoiseVolume((int)value);
            }
        };
        option = new FontRenderer(TextManager.OPTIONS,8, StaticFonts.IBM, Window.height*0.1f,
                new Vec2(Window.width * 0.5f, Window.height * 0.05f), Color4.BLACK);

        spew = new GUICheckBox(
                new Vec2(Window.width*0.5f, Window.height*0.41f),
                new Vec2(Window.width*0.125f, Window.height*0.1f),
                TextManager.OPTIONS,3, StaticFonts.monofonto, textColor, hoverTextColor
        ){
            @Override
            public void action () {
                Config.setSpew(GUIState == 1);
            }
        };
        spew.setState(Config.getSpew()?1:0);
    }

    /**
     * Update the overlay and its components.
     */
    public void update() {
        if(GameManager.inputsManager.inputPressed(0))  quit();

        general.update();
        video.update();
        inputs.update();

        switch (overlayState){
            case 0:
                language.update();
                musicVolume.update();
                noiseVolume.update();
                break;
            case 1:
                spew.update();
                break;
            case 2:
                break;
        }
    }

    /**
     * Display the overlay.
     */
    public void display() {
        Render.clear(new Color4(0.8f));

        option.renderC();
        general.display();
        video.display();
        inputs.display();

        switch (overlayState){
            case 0:
                language.display();
                musicVolume.display();
                noiseVolume.display();
                break;
            case 1:
                spew.display();
                break;
            case 2:
                break;
        }
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
        video.unload();
        inputs.unload();

        // Language
        language.unload();
        musicVolume.unload();
        noiseVolume.unload();

        spew.unload();
    }
}
