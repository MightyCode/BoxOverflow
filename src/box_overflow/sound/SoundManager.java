package box_overflow.sound;

import box_overflow.main.Config;
import sun.audio.AudioPlayer;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

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
    private static int musicVolume;
    private static int noiseVolume;

    private Clip clip;

    /**
     * Class constructor.
     */
    public SoundManager(){
        musicVolume = Config.getMusicVolume();
        noiseVolume = Config.getNoiseVolume();
        try{
            clip = AudioSystem.getClip();
            clip.open(StaticSound.box.getAudio());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void play(Sound s){
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResource("sfx/box.wav")));
            clip.start();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Return the music volume.
     * @return The volume.
     */
    public static int getMusicVolume() { return musicVolume; }

    /**
     * Set the music volume for the sound Manager and the Config.
     * @param newMusicVolume The new music volume.
     */
    public static void setMusicVolume(int newMusicVolume) {
        if(newMusicVolume < 0) musicVolume = 0;
        else if(newMusicVolume > 100) musicVolume = 100;
        else musicVolume = newMusicVolume;
        Config.setMusicVolume(musicVolume);
    }

    /**
     * Return the noise volume.
     * @return The volume.
     */
    public static int getNoiseVolume() { return noiseVolume; }

    /**
     * Set the noise volume for the sound Manager and the Config.
     * @param newNoiseVolume The new noise volume.
     */
    public static void setNoiseVolume(int newNoiseVolume) {
        if(newNoiseVolume < 0) noiseVolume = 0;
        if(newNoiseVolume > 100) noiseVolume = 100;
        else noiseVolume = newNoiseVolume;
        Config.setNoiseVolume(noiseVolume);
    }
}
