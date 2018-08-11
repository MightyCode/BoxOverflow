package box_overflow.main;

import box_overflow.screen.render.Render;
import box_overflow.screen.GameManager;
import box_overflow.util.Timer;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowFocusCallbackI;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Class of the game's main structure.
 *
 * @author MightyCode
 * @version of game : 2.2
 */
@SuppressWarnings("InfiniteLoopStatement")
public class Window implements GLFWWindowFocusCallbackI {

    /**
     * Window id.
     * This global variable contains the id of window using for many things.
     */
    public static long windowID;

    /**
     * Screen manager.
     * This global variable contains the manager of game'screens.
     */
    public static GameManager gameManager;

    /**
     * Width window size.
     * This global variable contains the width window size.
     */
    public static int width;

    /**
     * Height window size.
     * This global variable contains the height window size.
     */
    public static int height;

    /**
     * 1 second in nanoseconds.
     */
    private static final float SECOND = 1000000000.0f;

    /**
     * Needed TPS.
     */
    public static final float TPS = 60.0f;

    /**
     * Max FPS.
     */
    public static final float FPS = 100000.0f;

    /**
     * Time in a tick.
     */
    private static final double TICK_TIME = SECOND / TPS;

    /**
     * Time in a frame.
     */
    private static final double FRAME_TIME = SECOND / FPS;

    public static Config config;

    /**
     * Window class constructor.
     * Do nothing for the moment
     */
    public Window(){
        // Get the game global configurations.
        config = new Config();
        createWindow();
        glfwSetWindowFocusCallback(windowID,this);
    }

    /**
     * Create the window and get the window ID.
     */
    private static void createWindow(){
        width = Config.getWindowWidth();
        height = Config.getWindowHeight();

        // Setup an error callback.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        createNewWindow();
    }

    /**
     * Create a new window.
     */
    public static void createNewWindow(){

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        // Create the window if fullscreen
        windowID = glfwCreateWindow(width, height, "Box Overflow", NULL, NULL);

        System.out.println("\nWindow with id : "+ windowID +" created");


        if (windowID == NULL)
            throw new RuntimeException("Failed to create the GLFW window");


        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(windowID, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    windowID,
                    (Objects.requireNonNull(vidmode).width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(windowID);

        // Make the window visible
        glfwShowWindow(windowID);
        createCapabilities();

        Render.setViewPort(width, height);

        glEnable(GL_TEXTURE_2D);
        glActiveTexture(GL_TEXTURE0);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
    }

    /**
     * Destroy the current window.
     */
    public static void destroyWindow(){
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(windowID);
        glfwDestroyWindow(windowID);
        System.out.println("Window with id : " + windowID + " deleted");
    }

    /**
     * Method call by main class to begin the game.
     */
    void run() {
        loop();
        exit();
    }

    /**
     * Main method of game.
     */
    private static void loop() {
        // Set the screen manager
        gameManager = new GameManager(Config.getInputs());
        // Set render parameters
        Render.glEnable2D();

        int ticks = 0;
        int frames = 0;

        Timer timer = new Timer();

        double lastTick = 0.0;
        double lastFrame = 0.0;
        double lastSecond = 0.0;

        while(!glfwWindowShouldClose(windowID)){
            if (timer.getDuration() - lastTick >= TICK_TIME) {
                gameManager.update();
                ticks++;
                lastTick += TICK_TIME;
            } else if (timer.getDuration() - lastFrame >= FRAME_TIME) {
                gameManager.display();
                //System.out.println(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory());
                glfwSwapBuffers(windowID);
                glfwPollEvents();
                frames++;
                lastFrame += FRAME_TIME;
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (timer.getDuration() - lastSecond >= SECOND) {
                if(Box_Overflow.admin) glfwSetWindowTitle(windowID, "Box Overflow | FPS:" + frames + "; TPS:" + ticks);
                ticks = frames = 0;
                lastSecond += SECOND;
            }
        }
    }

    /**
     * Exit the game.
     */
    public static void exit() {
        gameManager.unload();
        Config.close();

        destroyWindow();
        // Terminate GLFW and free the error callback
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
        System.exit(0);
    }

    /**
     * Call the manager that the focus of the window as change.
     *
     * @param l Je n'en sais rien.
     * @param b The new state.
     */
    @Override
    public void invoke(long l, boolean b) {
        gameManager.focus(b);
    }
}
