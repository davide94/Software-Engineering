package it.polimi.ingsw.cg26.client.ui;

import javafx.embed.swing.JFXPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class MusicPlayer extends JFXPanel{

	private static final long serialVersionUID = -4819055617293583362L;

	private transient AudioInputStream audioInputStream;

	private transient Clip clip;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String MESSAGE = "Exception thrown in the audio handler";

	public MusicPlayer(String fileName){
		try{
			File file = new File(getClass().getResource(fileName).getFile());
			audioInputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
			
			clip= AudioSystem.getClip();
			clip.open(audioInputStream);
		}catch (Exception e){
			logger.error(MESSAGE, e);
		}
	}
	
	public void play(){
		try{
		    clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}catch(Exception e){
			logger.error(MESSAGE, e);
		}
	}

	public void stop(){
		try{
			clip.stop();
		}catch (Exception e){
			logger.error(MESSAGE, e);
		}
	}
	
	public boolean isRunning(){
		return clip.isRunning();
	}	

    public boolean toggle() {
        if (isRunning()) {
            stop();
            return false;
        }
        play();
        return true;
    }
}
