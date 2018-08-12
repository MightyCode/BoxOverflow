package box_overflow.screen.screens;

import box_overflow.inputs.KeyboardManager;
import box_overflow.main.Box_Overflow;
import box_overflow.main.Config;
import box_overflow.screen.render.texture.Texture;
import box_overflow.screen.render.texture.TextureRenderer;
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

    private FontRenderer admin;
    private FontRenderer[] levels;
    private int currentMap;

    private GUIButton goGame, previous, next, options, quit;

    private Texture background;
    private OptionOverlay option;

    private Texture win,play,lock;
    private Texture[] textures;
    private float offset = Window.width*0.2f;

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

        /*title = new FontRenderer(TextManager.MENU,0, StaticFonts.monofonto, Window.width*0.06f,
                new Vec2(Window.width * 0.5f, Window.height * 0.13f), Color4.BLACK);*/

        if(Box_Overflow.admin) admin = new FontRenderer("Mode admin", StaticFonts.monofonto, Window.width*0.02f,
                new Vec2(Window.width * 0.5f, Window.height * 0.90f), new Color4(0.2f,0.2f,0.2f,0.9f));

        else admin = new FontRenderer("", StaticFonts.monofonto, 0,
                new Vec2(0, 0), new Color4(0,0,0,0));

        Vec2 size = new Vec2(Window.width / 4f, Window.height / 20f);
        Color4 backgroundColor = new Color4(0.0f, 0.0f, 0.0f, 0.0f);
        Color4 hoverColor = new Color4(0.0f, 0.0f, 0.0f, 0.2f);
        Color4 textColor = new Color4(0.2f, 0.2f, 0.2f, 1.0f);
        Color4 hoverTextColor = Color4.BLACK;

        goGame = new GUIButton(
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

        previous = new GUIButton(
                new Vec2(Window.width * 0.25f, Window.height * 0.43f),
                size,
                TextManager.MENU,2,
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
                new Vec2(Window.width * 0.75f, Window.height * 0.43f),
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
                ((MenuScreen)Window.gameManager.getScreen()).currentMap(1);
            }
        };

        options = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.55f),
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
                Config.close();
            }
        };

        quit = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.70f),
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

        levels = new FontRenderer[Config.getNumberOfMap()];
        background = new Texture("/textures/menu/background.png");
        win = new Texture("/textures/menu/win.png");
        play = new Texture("/textures/menu/play.png");
        lock = new Texture("/textures/menu/lock.png");
        textures = new Texture[Config.getNumberOfMap()];
        for(int i = 0; i < Config.getNumberOfMap(); i++){
            textures[i] = new Texture();
            System.out.println();
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
    }

    /**
     * Update the menu.
     */
    public void update() {
        switch (screenState) {
            case 0:
                if(GameManager.keyboardManager.keyPressed(GLFW_KEY_LEFT))currentMap(-1);
                else if(GameManager.keyboardManager.keyPressed(GLFW_KEY_RIGHT))currentMap(1);
                if(KeyboardManager.key(GLFW_KEY_ENTER)) Window.gameManager.setScreen(GameManager.GAMESCREEN);

                goGame.update();
                next.update();
                previous.update();
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
                GameManager.CAMERA.setPosition(Window.width/2 - (int)(currentMap*offset),0,true);
                background.bind();
                TextureRenderer.imageC(0,0,1280,720);
                //title.render();
                admin.renderC();
                goGame.display();
                next.display();
                previous.display();
                options.display();
                quit.display();

                for(int i = 0; i < Config.getNumberOfMap(); i++){
                    textures[i].bind();
                    TextureRenderer.image((i+1) * offset - Window.width*0.025f,
                            Window.height*0.225f , Window.width*0.05f, Window.width*0.05f);
                    levels[i].render();
                }
                break;
            case 1:
                option.display();
                break;
        }
    }

    public void currentMap(int add){
        int tempNumber = currentMap + add;
        if(tempNumber >= 1 && tempNumber <= Config.getNumberOfMap() && Config.getMapConcluded(tempNumber) != 0){
            currentMap = tempNumber;
            Config.setCurrentMap(currentMap);
        } else if(tempNumber >=1){
            currentMap = 1;
            Config.setCurrentMap(currentMap);
        } else{
            currentMap = Config.getLastMap();
        }
        placeFont();
    }

    public void placeFont(){
        for(int i = 0; i < Config.getNumberOfMap(); i++){
            Vec2 place = new Vec2((i+1) * offset,Window.height*0.20f);
            levels[i] = new FontRenderer(String.valueOf(i+1), StaticFonts.monofonto, Window.width*0.02f,
                    place,Color4.BLACK.copy());
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
        //title.unload();
        // Unload the overlay
        option.unload();
        background.unload();
        win.unload();
        play.unload();
        lock.unload();
    }
}