package it.polimi.ingsw.cg26.client.ui;

import javafx.embed.swing.JFXPanel;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class MusicPlayer extends JFXPanel{

	private final String path= "src/main/resources/sound/soundtrack.wav";

	private File file;
	
	private AudioInputStream audioInputStream;
	
	private Clip clip;

	public MusicPlayer(){
		
		
		try{
			
			file=new File(path);
			audioInputStream= AudioSystem.getAudioInputStream(file.getAbsoluteFile());
			
			clip= AudioSystem.getClip();
			clip.open(audioInputStream);
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		
		play();
	}
	
	public void play(){
		try{
		    clip.start();
			clip.loop(clip.LOOP_CONTINUOUSLY);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	
	public void stop(){
		try{
			
			clip.stop();
		}catch (Exception e){
			System.out.println(e.getMessage());
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
