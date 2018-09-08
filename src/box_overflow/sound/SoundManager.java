package box_overflow.sound;

import box_overflow.main.Config;

import java.util.HashMap;

/**
 * Sound Manager.
 * This class manage the sound.
 *
 * @author MightyCode
 * @version 1.0
 */
public class SoundManager {

    /**
     * These values between 0 and 100 manage all of the sound.
     */
    private static float musicVolume;
    private static float noiseVolume;
    private static HashMap<String, Sound> sound = new HashMap<>();
    /**
     * Class constructor.
     */
    public SoundManager(){
    }

    public void addSound(String path, int type, String index){
        sound.put(index, new Sound(path, type));
    }
    public void addSound(String path, int type, String index, boolean loop){
        sound.put(index, new Sound(path, type, loop));
    }

    public void play(String index){
        sound.get(index).play();
    }

    public void stop(String index){
        sound.get(index).remove();
    }

    public static float getVolume(int type){
        if(type == Sound.NOISE){
            return noiseVolume;
        } else if(type == Sound.MUSIC){
            return musicVolume;
        } else return 1.0f;
    }

    public void remove(String index){
        sound.get(index).remove();
        sound.remove(index);
    }

    /**
     * Return the music volume.
     * @return The volume.
     */
    public static int getMusicVolume() { return (int)musicVolume*100; }

    /**
     * Set the music volume for the sound Manager and the Config.
     * @param newMusicVolume The new music volume.
     */
    public static void setMusicVolume(int newMusicVolume) {
        if(newMusicVolume < 0) musicVolume = 0;
        else if(newMusicVolume > 100) musicVolume = 100;
        else musicVolume = ((float)newMusicVolume)/100;
        Config.setMusicVolume((int)(musicVolume*100));
        for(String currentKey : sound.keySet()) {
            sound.get(currentKey).reload();
        }
    }

    /**
     * Return the noise volume.
     * @return The volume.
     */
    public static int getNoiseVolume() { return (int)noiseVolume*100; }

    /**
     * Set the noise volume for the sound Manager and the Config.
     * @param newNoiseVolume The new noise volume.
     */
    public static void setNoiseVolume(int newNoiseVolume) {
        if(newNoiseVolume < 0) noiseVolume = 0.0f;
        if(newNoiseVolume > 100) noiseVolume = 0.1f;
        noiseVolume = ((float)newNoiseVolume)/100;
        Config.setNoiseVolume((int)(noiseVolume*100));
        for(String currentKey : sound.keySet()) {
            sound.get(currentKey).reload();
        }
    }

    public void unload(){
        for(String index : sound.keySet()) {
            sound.get(index).remove();
        }
        sound = new HashMap<>();
    }
}
