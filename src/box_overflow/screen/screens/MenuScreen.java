package box_overflow.screen.screens;

import box_overflow.main.Config;
import box_overflow.screen.overlay.HelpOverlay;
import box_overflow.screen.render.texture.Texture;
import box_overflow.screen.render.texture.TextureRenderer;
import box_overflow.sound.Sound;
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

import static org.lwjgl.glfw.GLFW.*;

/**
 * Menu class.
 * This class is the menu screen.
 *
 * @author MightyCode
 * @version 1.1
 */
public class MenuScreen extends Screen {

    private FontRenderer title, credit;

    private FontRenderer[] levels;
    private int currentMap;

    private GUIButton goGame, previous, next, options, help,quit;

    private Texture background;
    private OptionOverlay option;
    private HelpOverlay oHelp;

    private Texture win, play, lock;
    private Texture[] textures;
    private float offset = Window.width*0.10f;

    private String music = "menu-music";

    /**
     * Menu screen class constructor.
     * Instance the menu and set the menu screen's variables.

     * @param gameManager Add gameManager to change the global screen.
     */
    public MenuScreen(GameManager gameManager) {
        super(gameManager);
        Render.setClearColor(new Color4(0.8f,0.8f,0.8f, 0.8f));
        currentMap = Config.getCurrentMap();
        // Security
        if(Config.getMapConcluded(currentMap) == 0){
            int i;
            for(i = Config.getNumberOfMap()-1; i > 0 ; i--){
                if(Config.getMapConcluded(i) != 0){
                    currentMap = i;
                    Config.setCurrentMap(currentMap);
                    break;
                }
            }

            if(i == 0) {
                Config.setMapConcluded(1, 1);
                currentMap = 1;
                Config.setCurrentMap(currentMap);
            }
        }

        Vec2 size = new Vec2(Window.width * 0.13f, Window.height * 0.05f);
        Color4 backgroundColor = new Color4(0.0f, 0.0f, 0.0f, 0.0f);
        Color4 hoverColor = new Color4(0.0f, 0.0f, 0.0f, 0.2f);
        Color4 textColor = new Color4(0.2f, 0.2f, 0.2f, 1.0f);
        Color4 hoverTextColor = Color4.BLACK;

        goGame = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.50f),
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

        previous = new GUIButton(
                new Vec2(Window.width * 0.40f, Window.height * 0.50f),
                new Vec2(Window.width * 0.07f, Window.height * 0.05f),
               "<-",
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ){
            @Override
            public void action() {
                ((MenuScreen)Window.gameManager.getScreen()).currentMap(-1);
            }
        };

        next = new GUIButton(
                new Vec2(Window.width * 0.60f, Window.height * 0.50f),
                new Vec2(Window.width * 0.07f, Window.height * 0.05f),
                "->",
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ) {
            @Override
            public void action() {
                ((MenuScreen)Window.gameManager.getScreen()).currentMap(1);
            }
        };

        options = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.59f),
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
                Window.gameManager.setState(1);
                Config.close();
            }
        };

        help = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.68f),
                size,
                TextManager.MENU,6,
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ) {
            @Override
            public void action() {
                Window.gameManager.setState(2);
            }
        };

        quit = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.77f),
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
                Window.gameManager.setState(0);
            }
        };

        levels = new FontRenderer[Config.getNumberOfMap()];
        background = new Texture("/textures/menu/background.png");
        win = new Texture("/textures/menu/win.png");
        play = new Texture("/textures/menu/play.png");
        lock = new Texture("/textures/menu/lock.png");
        textures = new Texture[Config.getNumberOfMap()];
        for(int i = 0; i < Config.getNumberOfMap(); i++){
            textures[i] = new Texture();
            switch(Config.getMapConcluded(i+1)){
                case 0:
                    textures[i] = lock;
                    break;
                case 1 :
                    textures[i] = play;
                    break;
                case 2 :
                    textures[i] = win;
                    break;
            }
        }

        placeFont();
        GameManager.CAMERA.setPosition(0,0,false);
        title = new FontRenderer("Box overflow", StaticFonts.monofonto, Window.width*0.06f,
                new Vec2(Window.width*0.5f, Window.height*0.10f), Color4.BLACK.copy());
        credit = new FontRenderer("Game created by MightyCode and TheQFM", StaticFonts.IBM, Window.width*0.020f,
                new Vec2(Window.width*0.5f, Window.height*0.90f), Color4.BLACK.copy());
        oHelp = new HelpOverlay(this);

        GameManager.soundManager.addSound("resources/sfx/music1.ogg", Sound.MUSIC, music, true);
        GameManager.soundManager.play(music);
    }

    /**
     * Update the menu.
     */
    public void update() {
        switch (screenState) {
            case 0:
                if(GameManager.keyboardManager.keyPressed(GLFW_KEY_LEFT))currentMap(-1);
                else if(GameManager.keyboardManager.keyPressed(GLFW_KEY_RIGHT))currentMap(1);
                if(GameManager.inputsManager.inputPressed(6)) Window.gameManager.setScreen(GameManager.GAMESCREEN);
                goGame.update();
                next.update();
                previous.update();
                options.update();
                help.update();
                quit.update();
                break;
            case 1:
                option.update();
                break;
            case 2:
                oHelp.update();
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
                GameManager.CAMERA.setPosition(Window.width/2 - (int)(currentMap*offset),0,true);
                background.bind();
                TextureRenderer.imageC(0,0,Window.width,Window.height);
                title.renderC();
                credit.renderC();
                goGame.display();
                next.display();
                previous.display();
                options.display();
                help.display();
                quit.display();

                for(int i = 0; i < Config.getNumberOfMap(); i++){
                    textures[i].bind();
                    TextureRenderer.image(((i+1) * offset) - Window.width*0.022f,
                            Window.height*0.325f , Window.width*0.05f, Window.width*0.05f);
                    levels[i].render();
                }
                break;
            case 1:
                option.display();
                break;
            case 2:
                oHelp.display();
                break;
        }
    }

    private void currentMap(int add){
        int tempNumber = currentMap + add;
        if(tempNumber >= 1 && tempNumber <= Config.getNumberOfMap() && Config.getMapConcluded(tempNumber) != 0){
            currentMap = tempNumber;
        } else if(tempNumber >= 1){
            currentMap = 1;

        } else{
            currentMap = Config.getLastMap();

        }
        Config.setCurrentMap(currentMap);
        placeFont();
    }

    private void placeFont(){
        float size = 0.03f;
        for(int i = 0; i < Config.getNumberOfMap(); i++){
            Vec2 place = new Vec2((i+1) * offset  ,Window.height*0.30f);
            if(i+1 != currentMap){
                levels[i] = new FontRenderer(String.valueOf(i+1), StaticFonts.monofonto, Window.width*0.02f,
                        place, Color4.BLACK.copy());
            } else {
                place.setY(Window.height*0.27f);
                levels[i] = new FontRenderer(String.valueOf(i+1), StaticFonts.monofonto, Window.width*size,
                        place, Color4.BLACK.copy());
                place.setY(Window.height*0.30f);
            }
        }
    }

    /**
     * Unload resources in menu to free memory.
     */
    public void unload() {
        // Unload buttons
        goGame.unload();
        next.unload();
        previous.unload();
        options.unload();
        quit.unload();
        title.unload();
        credit.unload();
        // Unload the overlay
        option.unload();
        oHelp.unload();
        background.unload();
        win.unload();
        play.unload();
        lock.unload();
        GameManager.soundManager.remove(music);
    }
}