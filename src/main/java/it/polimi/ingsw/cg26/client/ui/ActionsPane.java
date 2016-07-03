package it.polimi.ingsw.cg26.client.ui;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActionsPane extends AnchorPane {

	private List<Button> actions;
	private Button acquire;
	private Button buildKing;
	private Button electAsMainAction;
	private Button build;
	private Button engageAssistant;
	private Button changeBPT;
	private Button electAsQuickAction;
	private Button additionalMainAction;
	private Button foldQuickAction;
	private Button timer;

    private HBox obscuration;
	
	public ActionsPane() {
		DropShadow shadow = new DropShadow();
        shadow.setOffsetY(3.0f);
        shadow.setRadius(50.0);
        shadow.setColor(Color.BLACK);
        
        setPrefWidth(400.0);
        setPrefHeight(310.0);
        setVisible(false);
		setEffect(shadow);
        setStyle("-fx-background-image: url(" + getClass().getResource("/img/actions.jpg") + ");" +
                      "-fx-background-position: center;" +
                      "-fx-background-size: 100% 100%;");
        this.addEventHandler(MouseEvent.MOUSE_EXITED, e -> this.setVisible(false));
        addButtons();
        addButtonsEffects();

        Label l = new Label("You cannot perform an action now.");
        l.setTextFill(Color.WHITE);
        obscuration = new HBox(l);
        obscuration.setAlignment(Pos.CENTER);
        obscuration.setPrefSize(getPrefWidth(), getPrefHeight());
        obscuration.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        getChildren().add(obscuration);
	}
	
	private void addButtons() {
		acquire = new Button();
		buildKing = new Button();
		electAsMainAction = new Button();
		build = new Button();
		engageAssistant = new Button();
		changeBPT = new Button();
		electAsQuickAction = new Button();
		additionalMainAction = new Button();
		foldQuickAction = new Button();
		timer = new Button();
		actions = new ArrayList<>();
		actions.addAll(Arrays.asList(acquire, buildKing, electAsMainAction, build, timer, engageAssistant, changeBPT, electAsQuickAction, additionalMainAction, foldQuickAction));
		for(Button b : actions) {
			b.setPrefWidth(200);
	        b.setPrefHeight(48);
	        b.setStyle("-fx-background-color: transparent");
		}

		
		AnchorPane.setLeftAnchor(timer, 0.0);
        AnchorPane.setBottomAnchor(timer, 0.0);
        AnchorPane.setLeftAnchor(build, 0.0);
        AnchorPane.setBottomAnchor(build, 50.0);
        AnchorPane.setLeftAnchor(electAsMainAction, 0.0);
        AnchorPane.setBottomAnchor(electAsMainAction, 100.0);
        AnchorPane.setLeftAnchor(buildKing, 0.0);
        AnchorPane.setBottomAnchor(buildKing, 152.0);
        AnchorPane.setLeftAnchor(acquire, 0.0);
        AnchorPane.setBottomAnchor(acquire, 204.5);
        AnchorPane.setRightAnchor(foldQuickAction, 0.0);
        AnchorPane.setBottomAnchor(foldQuickAction, 0.0);
        AnchorPane.setRightAnchor(additionalMainAction, 0.0);
        AnchorPane.setBottomAnchor(additionalMainAction, 50.0);
        AnchorPane.setRightAnchor(electAsQuickAction, 0.0);
        AnchorPane.setBottomAnchor(electAsQuickAction, 100.0);
        AnchorPane.setRightAnchor(changeBPT, 0.0);
        AnchorPane.setBottomAnchor(changeBPT, 152.0);
        AnchorPane.setRightAnchor(engageAssistant, 0.0);
        AnchorPane.setBottomAnchor(engageAssistant, 204.5);
        
        this.getChildren().addAll(timer, build, electAsMainAction, buildKing, acquire, foldQuickAction, additionalMainAction, electAsQuickAction, changeBPT, engageAssistant);
	}
	
	private void addButtonsEffects() {
		for(Button b : actions) {
			b.addEventHandler(MouseEvent.MOUSE_EXITED, e -> b.setStyle("-fx-background-color: transparent"));
		}

		EventHandler<InputEvent> acquireHandler = (InputEvent event) ->
			acquire.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/acquireMousePassing.png") + ");" +
				"-fx-background-position: center;" +
				"-fx-background-size: 100% 100%;" + 
				"-fx-background-color: transparent");
		acquire.addEventHandler(MouseEvent.MOUSE_ENTERED, acquireHandler);
		acquire.addEventHandler(MouseEvent.MOUSE_RELEASED, acquireHandler);
		acquire.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> 
		acquire.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/acquireMousePressed.png") + ");" +
                "-fx-background-position: center;" +
                "-fx-background-size: 100% 100%;" + 
                "-fx-background-color: transparent"));
		
		EventHandler<InputEvent> buildKingHandler = (InputEvent event) ->
			buildKing.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/buildKingMousePassing.png") + ");" +
				"-fx-background-position: center;" +
				"-fx-background-size: 100% 100%;" + 
				"-fx-background-color: transparent");
		buildKing.addEventHandler(MouseEvent.MOUSE_ENTERED, buildKingHandler);
		buildKing.addEventHandler(MouseEvent.MOUSE_RELEASED, buildKingHandler);
		buildKing.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> 
			buildKing.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/buildKingMousePressed.png") + ");" +
				"-fx-background-position: center;" +
				"-fx-background-size: 100% 100%;" + 
	            "-fx-background-color: transparent"));
		
		EventHandler<InputEvent> electAsMainActionHandler = (InputEvent event) ->
			electAsMainAction.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/electAsMainActionMousePassing.png") + ");" +
				"-fx-background-position: center;" +
				"-fx-background-size: 100% 100%;" + 
				"-fx-background-color: transparent");
		electAsMainAction.addEventHandler(MouseEvent.MOUSE_ENTERED, electAsMainActionHandler);
		electAsMainAction.addEventHandler(MouseEvent.MOUSE_RELEASED, electAsMainActionHandler);
		electAsMainAction.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> 
			electAsMainAction.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/electAsMainActionMousePressed.png") + ");" +
				"-fx-background-position: center;" +
				"-fx-background-size: 100% 100%;" + 
                "-fx-background-color: transparent"));
		
		EventHandler<InputEvent> buildHandler = (InputEvent event) ->
			build.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/buildMousePassing.png") + ");" +
				"-fx-background-position: center;" +
				"-fx-background-size: 100% 100%;" + 
				"-fx-background-color: transparent");
		build.addEventHandler(MouseEvent.MOUSE_ENTERED, buildHandler);
		build.addEventHandler(MouseEvent.MOUSE_RELEASED, buildHandler);
		build.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> 
			build.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/buildMousePressed.png") + ");" +
				"-fx-background-position: center;" +
				"-fx-background-size: 100% 100%;" + 
                "-fx-background-color: transparent"));
		
		EventHandler<InputEvent> timerHandler = (InputEvent event) ->
		timer.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/timerMousePassing.png") + ");" +
			"-fx-background-position: center;" +
			"-fx-background-size: 100% 100%;" + 
			"-fx-background-color: transparent");
	timer.addEventHandler(MouseEvent.MOUSE_ENTERED, timerHandler);
	timer.addEventHandler(MouseEvent.MOUSE_RELEASED, timerHandler);
	timer.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> 
		timer.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/timerMousePressed.png") + ");" +
			"-fx-background-position: center;" +
			"-fx-background-size: 100% 100%;" + 
            "-fx-background-color: transparent"));
	
		
		EventHandler<InputEvent> engageAssistantHandler = (InputEvent event) ->
			engageAssistant.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/engageAssistantMousePassing.png") + ");" +
				"-fx-background-position: center;" +
				"-fx-background-size: 100% 100%;" + 
				"-fx-background-color: transparent");
		engageAssistant.addEventHandler(MouseEvent.MOUSE_ENTERED, engageAssistantHandler);
		engageAssistant.addEventHandler(MouseEvent.MOUSE_RELEASED, engageAssistantHandler);
		engageAssistant.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> 
			engageAssistant.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/engageAssistantMousePressed.png") + ");" +
				"-fx-background-position: center;" +
				"-fx-background-size: 100% 100%;" + 
				"-fx-background-color: transparent"));
		
		EventHandler<InputEvent> changeBPTHandler = (InputEvent event) ->
			changeBPT.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/changeBPTMousePassing.png") + ");" +
				"-fx-background-position: center;" +
				"-fx-background-size: 100% 100%;" + 
				"-fx-background-color: transparent");
		changeBPT.addEventHandler(MouseEvent.MOUSE_ENTERED, changeBPTHandler);
		changeBPT.addEventHandler(MouseEvent.MOUSE_RELEASED, changeBPTHandler);
		changeBPT.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> 
			changeBPT.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/changeBPTMousePressed.png") + ");" +
				"-fx-background-position: center;" +
				"-fx-background-size: 100% 100%;" + 
				"-fx-background-color: transparent"));
		
		EventHandler<InputEvent> electAsQuickActionHandler = (InputEvent event) ->
			electAsQuickAction.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/electAsQuickActionMousePassing.png") + ");" +
				"-fx-background-position: center;" +
				"-fx-background-size: 100% 100%;" + 
				"-fx-background-color: transparent");
		electAsQuickAction.addEventHandler(MouseEvent.MOUSE_ENTERED, electAsQuickActionHandler);
		electAsQuickAction.addEventHandler(MouseEvent.MOUSE_RELEASED, electAsQuickActionHandler);
		electAsQuickAction.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> 
			electAsQuickAction.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/electAsQuickActionMousePressed.png") + ");" +
				"-fx-background-position: center;" +
				"-fx-background-size: 100% 100%;" + 
				"-fx-background-color: transparent"));
		
		EventHandler<InputEvent> additionalMainActionHandler = (InputEvent event) ->
			additionalMainAction.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/additionalMainActionMousePassing.png") + ");" +
				"-fx-background-position: center;" +
				"-fx-background-size: 100% 100%;" + 
				"-fx-background-color: transparent");
			additionalMainAction.addEventHandler(MouseEvent.MOUSE_ENTERED, additionalMainActionHandler);
		additionalMainAction.addEventHandler(MouseEvent.MOUSE_RELEASED, additionalMainActionHandler);
		additionalMainAction.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> 
			additionalMainAction.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/additionalMainActionMousePressed.png") + ");" +
				"-fx-background-position: center;" +
				"-fx-background-size: 100% 100%;" + 
				"-fx-background-color: transparent"));
		
		EventHandler<InputEvent> foldQuickActionHandler = (InputEvent event) ->
		foldQuickAction.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/foldQuickActionMousePassing.png") + ");" +
			"-fx-background-position: center;" +
			"-fx-background-size: 100% 100%;" + 
			"-fx-background-color: transparent");
	foldQuickAction.addEventHandler(MouseEvent.MOUSE_ENTERED, foldQuickActionHandler);
	foldQuickAction.addEventHandler(MouseEvent.MOUSE_RELEASED, foldQuickActionHandler);
	foldQuickAction.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> 
		foldQuickAction.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/foldQuickActionMousePressed.png") + ");" +
			"-fx-background-position: center;" +
			"-fx-background-size: 100% 100%;" + 
            "-fx-background-color: transparent"));
	}
	
	public Button getAcquire() {
		return this.acquire;
	}
	
	public Button getBuildKing() {
		return this.buildKing;
	}
	
	public Button getElectAsMainAction() {
		return this.electAsMainAction;
	}
	
	public Button getBuild() {
		return this.build;
	}
	
	public Button getTimer() {
		return this.timer;
	}
	
	
	public Button getEngageAssistant() {
		return this.engageAssistant;
	}
	
	public Button getChangeBPT() {
		return this.changeBPT;
	}
	
	public Button getElectAsQuickAction() {
		return this.electAsQuickAction;
	}
	
	public Button getAdditionalMainAction() {
		return this.additionalMainAction;
	}
	
	public Button getFoldQuickAction() {
		return this.foldQuickAction;
	}

    public void enable() {
        obscuration.setVisible(false);
    }

    public void disable() {
        obscuration.setVisible(true);
    }

}
