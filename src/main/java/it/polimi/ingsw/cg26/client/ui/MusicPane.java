package it.polimi.ingsw.cg26.client.ui;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MusicPane extends AnchorPane{
	
	private List<Button> buttons;
	private Button stop;
	private Button play;
	
	public MusicPane() {
		
		DropShadow shadow = new DropShadow();
        shadow.setOffsetY(3.0f);
        shadow.setRadius(50.0);
        shadow.setColor(Color.BLACK);
        
        setPrefWidth(20.0);
        setPrefHeight(20.0);
        setVisible(false);
		setEffect(shadow);
		
		setStyle("-fx-background-image: url(" + getClass().getResource("/img/actions.jpg") + ");" +
                "-fx-background-position: center;" +
                "-fx-background-size: 100% 100%;");
       this.addEventHandler(MouseEvent.MOUSE_EXITED, e -> this.setVisible(false));
       addButtons();
       addButtonsEffects();
  
	}
	
	private void addButtons() {
        
		play = new Button("Play");
		stop = new Button("stop");
		
		buttons = new ArrayList<>();
		buttons.addAll(Arrays.asList(play, stop));
		
		for(Button b : buttons) {
			b.setPrefWidth(20);
	        b.setPrefHeight(10);
	        b.setStyle("-fx-background-color: transparent");
		}
		
		
		AnchorPane.setLeftAnchor(play, 0.0);
        AnchorPane.setBottomAnchor(play, 0.0);
        AnchorPane.setRightAnchor(stop, 0.0);
        AnchorPane.setBottomAnchor(stop, 0.0);
        
        this.getChildren().addAll(play,stop);
        
	}
	
	private void addButtonsEffects() {
		
		
		 for(Button b : buttons) {
				b.addEventHandler(MouseEvent.MOUSE_EXITED, e -> b.setStyle("-fx-background-color: transparent"));
			}
		 
		 EventHandler<InputEvent> playHandler = (InputEvent event) ->
			play.setStyle("-fx-background-image: url(" + getClass().getResource("/img/musicButtons/playButton.png") + ");" +
				"-fx-background-position: center;" +
				"-fx-background-size: 100% 100%;" + 
				"-fx-background-color: transparent");
		play.addEventHandler(MouseEvent.MOUSE_ENTERED, playHandler);
		play.addEventHandler(MouseEvent.MOUSE_RELEASED, playHandler);
		play.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> 
		play.setStyle("-fx-background-image: url(" + getClass().getResource("/img/musicButtons/playButton.png") + ");" +
             "-fx-background-position: center;" +
             "-fx-background-size: 100% 100%;" + 
             "-fx-background-color: transparent"));
		
		
		
		
		EventHandler<InputEvent> stopHandler = (InputEvent event) ->
		stop.setStyle("-fx-background-image: url(" + getClass().getResource("/img/musicButtons/stopButton.png") + ");" +
			"-fx-background-position: center;" +
			"-fx-background-size: 100% 100%;" + 
			"-fx-background-color: transparent");
	stop.addEventHandler(MouseEvent.MOUSE_ENTERED, playHandler);
	stop.addEventHandler(MouseEvent.MOUSE_RELEASED, playHandler);
	stop.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> 
	stop.setStyle("-fx-background-image: url(" + getClass().getResource("/img/musicButtons/stopButton.png") + ");" +
         "-fx-background-position: center;" +
         "-fx-background-size: 100% 100%;" + 
         "-fx-background-color: transparent"));
	}
	
	public Button getPlay() {
		return this.play;
	}
	
	public Button getStop() {
		return this.stop;
	}

}
