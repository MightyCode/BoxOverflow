package ludum42.screen.screens;

import ludum42.entity.type.Player;
import ludum42.game.Hud;
import ludum42.entity.EntityManager;
import ludum42.screen.render.Render;
import ludum42.screen.GameManager;
import ludum42.screen.overlay.DeathOverlay;
import ludum42.screen.overlay.OptionOverlay;
import ludum42.screen.overlay.PauseOverlay;
import ludum42.util.math.Vec2;

/**
 * Game class.
 * This class is the game screen.
 *
 * @author MightyCode
 * @version 1.0
 */
public class GameScreen extends Screen {

    public static Hud hud;

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
    public static final int STATE_DEATH = 3;

    /**
     * Pause Overlay.
     * This variable contains the pause overlay.
     */
    private final PauseOverlay pause;

    /**
     * Death Overlay.
     * This variable contains the overlay of death that appears when the player dies.
     */
    private final DeathOverlay death;

    /**
     * Option overlay.
     * This variable contains the overlay where the player change its options.
     */
    private final OptionOverlay option;

    /**
     * Game screen class constructor.
     * Instance the class and set all of the GameScreen's variables.
     *
     * @param gameManager Add gameManager to change the global screen.
     */
    public GameScreen(GameManager gameManager) {
        super(gameManager);

        /*File test = new File("data/saves");
        if(!test.exists() && !test.isDirectory()){
            System.out.println("Create file save");
            File save = new File("data\\saves");
            save.mkdirs();
        }*/

        hud = new Hud();

        Render.setClearColor(0.67f, 0.85f, 0.90f, 1f);
        System.out.println("\n-------------------------- \n");

        /* Init gameScreen's variables */
        // Init screen vars
        screenState = STATE_NORMAL;
        // Init screen's overlay
        pause = new PauseOverlay(this);
        death = new DeathOverlay(this);
        option = new OptionOverlay(this){
            @Override
            public void quit(){
                Screen.setState(STATE_PAUSE);
            }
        };

        // Init tileMap
        GameManager.CAMERA.setTween(0.3f, 1f);

        Player player = new Player(this,new Vec2(0));

        entityManager.setPlayer(player);

        // Add player for the camera
        GameManager.CAMERA.setEntityToCamera(player);

        // Set the position of map before beginning of the game
        GameManager.CAMERA.setPosition(false);
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
                pause.update();
                break;
            case STATE_DEATH:
                death.update();
                break;
            case STATE_OPTION:
                option.update();
                break;
            default:
                updateGame();
                break;
        }
    }

    /**
     * Update the player and the map.
     */
    private void updateGame() {
        if(GameManager.inputsManager.inputPressed(0))screenState = STATE_PAUSE;

        // Update player
        entityManager.update();
        GameManager.CAMERA.setPosition(true);
        hud.update();

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
                hud.display();
                break;
            case STATE_PAUSE:
                displayGame();
                pause.display();
                break;
            case STATE_OPTION:
                option.display();
                break;
            case STATE_DEATH:
                displayGame();
                death.display();
                break;
        }
    }

    /**
     * Display the map and the player
     */
    private void displayGame() {
        // Draw player
        entityManager.display();
    }

    /**
     * Unload the texture to free memory.
     */
    public void unload() {
        hud.unload();
        pause.unload();
        death.unload();
        option.unload();
        entityManager.removeAll();
        entityManager.setPlayer(null);
    }

    /**
     * If the focus ins't on the game, the normal state change to be the escape state.
     * @param b The focus.
     */
    public void focus(boolean b) {
        if (!b && screenState == STATE_NORMAL) screenState = STATE_PAUSE;
    }
}
