package box_overflow.sound;
import sun.audio.AudioStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;
import java.net.URL;

public class Sound {

    private String path;

    private AudioInputStream au;
    private URL in;

    public Sound(String path){
        this.path = path;
        try{
            in = this.getClass().getClassLoader().getResource(path);
            au = AudioSystem.getAudioInputStream(in);
            // Get a sound clip resource.
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public AudioInputStream getAudio(){
        return au;
    }
}
