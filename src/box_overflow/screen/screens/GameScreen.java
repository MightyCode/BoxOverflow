package box_overflow.screen.screens;

import box_overflow.entity.type.Player;
import box_overflow.entity.EntityManager;
import box_overflow.game.LevelManager;
import box_overflow.main.Config;
import box_overflow.main.Window;
import box_overflow.screen.overlay.*;
import box_overflow.screen.render.Render;
import box_overflow.screen.GameManager;
import box_overflow.sound.Sound;
import box_overflow.util.math.Color4;
import box_overflow.util.math.Vec2;

/**
 * Game class.
 * This class is the game screen.
 *
 * @author MightyCode
 * @version 1.0
 */
public class GameScreen extends Screen {

    public static int MAX_GAMETILESIZE = 96;

    public static int MIN_GAMETILESIZE = 64;

    public static int tile = 256;

    /**
     * The entity manager manage every entity of the game.
     */
    public static final EntityManager entityManager = new EntityManager();

    /**
     * Game's states.
     * These static final variable counting the different state of game.
     */
    public static final int STATE_NORMAL = 0;
    public static final int STATE_PAUSE = 1;
    public static final int STATE_OPTION = 2;
    public static final int STATE_WIN = 3;

    /**
     * Pause Overlay.
     * This variable contains the pause overlay.
     */
    private final PauseOverlay pause;
    /**
     * Option overlay.
     * This variable contains the overlay where the player change its options.
     */
    private final OptionOverlay option;

    private final WinOverlay win;

    public static LevelManager lvm;

    private String music = "music-game";

    /**
     * Game screen class constructor.
     * Instance the class and set all of the GameScreen's variables.
     *
     * @param gameManager Add gameManager to change the global screen.
     */
    public GameScreen(GameManager gameManager) {
        super(gameManager);
        Render.setClearColor(new Color4(0.1f, 0.1f, 0.1f, 1f));
        System.out.println("\n-------------------------- \n");

        /* Init gameScreen's variables */
        // Init screen vars
        screenState = STATE_NORMAL;
        // Init screen's overlay
        pause = new PauseOverlay(this);
        option = new OptionOverlay(this){
            @Override
            public void quit(){
                Window.gameManager.setState(STATE_PAUSE);
                Config.close();
            }
        };

        win = new WinOverlay(this);

        Player player = new Player(this,new Vec2(1.0f,1.5f));

        entityManager.setPlayer(player);
        GameManager.CAMERA.setEntityToCamera(player);
        GameManager.CAMERA.setPosition(false);

        lvm = new LevelManager();
        lvm.load();

        GameManager.soundManager.addSound("/sfx/music2.ogg", Sound.MUSIC, music, true);
        GameManager.soundManager.play(music);
    }

    /**
     * Update the screen in terms of the game's state.
     */
    public void update() {
        switch (screenState) {
            case STATE_NORMAL:
                updateGame();
                break;
            case STATE_PAUSE:
                lvm.fullmap();
                pause.update();
                break;
            case STATE_OPTION:
                option.update();
                break;
            case STATE_WIN:
                lvm.finish();
                win.update();
            default:
                updateGame();
                break;
        }
    }

    /**
     * Update the player and the map.
     */
    private void updateGame() {
        if(GameManager.inputsManager.inputPressed(0) && getState() == STATE_NORMAL){
            screenState = STATE_PAUSE;
            lvm.saveTileSize();
        }
        // Update player
        lvm.update();
        entityManager.update();

        //GameManager.CAMERA.setPosition(true);
        GameManager.CAMERA.setPosition(true);
        entityManager.dispose();
    }

    /**
     * Display the screen in terms of the game'state
     */
    public void display() {
        // clear the framebuffer
        Render.clear();

        switch (screenState) {
            case STATE_NORMAL:
                displayGame();
                break;
            case STATE_PAUSE:
                displayGame();
                pause.display();
                break;
            case STATE_OPTION:
                option.display();
                break;
            case STATE_WIN :
                displayGame();
                win.display();
        }
    }

    /**
     * Display the map and the player
     */
    private void displayGame() {
        // Draw player
        lvm.display();
    }

    /**
     * Unload the texture to free memory.
     */
    public void unload() {
        pause.unload();
        option.unload();
        win.unload();
        lvm.unload();
        entityManager.removeAll();
        entityManager.getPlayer().unload();
        GameManager.soundManager.remove(music);
    }

    /**
     * If the focus ins't on the game, the normal state change to be the escape state.
     * @param b The focus.
     */
    public void focus(boolean b) {
        if (!b && screenState == STATE_NORMAL) screenState = STATE_PAUSE;
    }
}
