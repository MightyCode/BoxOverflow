package box_overflow.main;

/**
 * Main class of the game.
 *
 * @author MightyCode
 * @version of the current game developed: 0.3.4
 */
public class Box_Overflow {

    public static final boolean admin = System.getenv("JAVA_DEV_ADMIN").equals("la couille est dans le potage");

    /**
     * Window.
     * This global variable contains all of the main game structure.
     */
    public static Window window;

    /**
     * Run the game.
     */
    public static void main(String[] args) {
        window = new Window();
        window.run();
    }
}
