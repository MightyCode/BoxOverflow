package box_overflow.main;

import box_overflow.util.FileMethods;
import box_overflow.util.XmlReader;

import java.io.File;

/**
 * This class save the config of the game.
 *
 * @author MightyCode
 * @version 1.0
 */
public class Config {


    private static String projectPath;
    private static int currentWindowWidth;
    private static int currentWindowHeight;
    private static int[][] inputs;
    private static String language;
    private static int currentMap;
    private static int numberOfMap;
    private static int mapConcluded[];
    private static int lastMap;
    private static boolean spew;
    private static int noiseVolume;
    private static int musicVolume;

    public static final String CONFIG_PATH = "data/config/config.xml";
    public static final String ENTITY_PATH = "box_overflow.entity.type.";
    public static final String MAP_PATH = "/map/map";

    /**
     * Class constructor.
     */
    public Config(){
        // Information
        projectPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();

        // If the directory don't exists
        File test = new File("data/");
        if (!test.exists() && !test.isDirectory()){
            System.out.println("Create file Data");
            File config = new File("data/config");
            config.mkdirs();

            if(!FileMethods.copyFromJar("/config/configOriginal.xml","data/config/config.xml")){
                System.out.println("Error on creation of Data");
                Window.exit();
            }
        }

        test = new File("data/config");
        if (!test.exists() && !test.isDirectory()){
            System.out.println("Create file Config");
            File config = new File("data/config");
            config.mkdirs();
            if(!FileMethods.copyFromJar("/config/configOriginal.xml","data/config/config.xml")){
                System.out.println("Error on creation of config");
                Window.exit();
            }
        }


        test = new File(Config.CONFIG_PATH);
        if (!test.exists() && !test.isDirectory()){
            System.out.println("Create file Config.xml");
            if(!FileMethods.copyFromJar("/config/configOriginal.xml","data/config/config.xml")){
                System.out.println("Error on creation of Config.xml");
                Window.exit();
            }
        }

        // Load configurationss
        XmlReader.loadConfig();
    }


    /**
     * Get the window width size.
     * @return The size(int).
     */
    public static int getWindowWidth() { return currentWindowWidth; }

    /**
     * Set the window size in width.
     * @param windowWidth The new window size width.
     */
    public static void setWindowWidth(int windowWidth) { Config.currentWindowWidth = windowWidth; }

    /**
     * Get the window height size.
     * @return The size(int).
     */
    public static int getWindowHeight() { return currentWindowHeight; }

    /**
     * Set the window size in height.
     * @param windowHeight The new window size height.
     */
    public static void setWindowHeight(int windowHeight) { Config.currentWindowHeight = windowHeight; }

    /**
     * Get the inputs.
     * @return The inputs table (int[][])
     */
    public static int[][] getInputs() { return inputs; }

    /**
     * Set the new inputs
     * @param inputs The new inputs.
     */
    public static void setInputs(int[][] inputs) { Config.inputs = inputs; }

    /**
     * Get current the language.
     * @return The aka(string)
     */
    public static String getLanguage(){return language;}

    /**
     * Set the new game's language.
     * @param newLanguage The new language.
     */
    public static void setLanguage(String newLanguage){language = newLanguage;}

    /**
     * Get music volume.
     * @return The music volume(int).
     */
    public static int getMusicVolume() { return musicVolume; }

    /**
     * Set the new music volume.
     * @param newVolume The new music volume.
     */
    public static void setMusicVolume(int newVolume){ musicVolume = newVolume; }

    /**
     * Get noise volume.
     * @return The noise volume(int).
     */
    public static int getNoiseVolume() { return noiseVolume; }

    /**
     * Set the new noise volume.
     * @param newNoiseVolume The new noise volume.
     */
    public static void setNoiseVolume(int newNoiseVolume){ noiseVolume = newNoiseVolume; }

    /**
     * Save configurations on game's close.
     */
    public static void close(){ XmlReader.saveConfiguration(); }

    public static int getCurrentMap() {
        return currentMap;
    }

    public static void setCurrentMap(int attribute) { currentMap = attribute; }

    public static void setNumberOfMap(int attribute) { numberOfMap = attribute; }

    public static int getNumberOfMap() { return numberOfMap; }

    public static int getMapConcluded(int mapIndex){ if(mapIndex > mapConcluded.length) return -1; return mapConcluded[mapIndex-1]; }

    public static int[] getMapsConcluded(){return mapConcluded;}

    public static void setMapConcluded(int mapConcluded[]){Config.mapConcluded = mapConcluded;}

    public static void setMapConcluded(int index, int value){ if(index <= numberOfMap) Config.mapConcluded[index-1] = value;}

    public static void setSpew(boolean newState){
        spew = newState;
    }

    public static boolean getSpew(){
        return spew;
    }

    public static void setLastMap(int last){
        lastMap = last;
    }

    public static int getLastMap(){
        return lastMap;
    }
}