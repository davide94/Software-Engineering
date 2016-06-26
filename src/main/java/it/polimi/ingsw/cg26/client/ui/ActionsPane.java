package it.polimi.ingsw.cg26.client.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

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
	
	public ActionsPane() {
		DropShadow shadow = new DropShadow();
        shadow.setOffsetY(3.0f);
        shadow.setRadius(50.0);
        shadow.setColor(Color.BLACK);
        
        this.setPrefWidth(400.0);
        this.setPrefHeight(310.0);
        this.setVisible(false);
        this.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actions.jpg") + ");" +
                      "-fx-background-position: center;" +
                      "-fx-background-size: 100% 100%;");
        this.addEventHandler(MouseEvent.MOUSE_EXITED, e -> this.setVisible(false));
        addButtons();
        addButtonsEffects();
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
		actions = new ArrayList<>();
		actions.addAll(Arrays.asList(acquire, buildKing, electAsMainAction, build, engageAssistant, changeBPT, electAsQuickAction, additionalMainAction));
		for(Button b : actions) {
			b.setPrefWidth(200);
	        b.setPrefHeight(60);
	        b.setStyle("-fx-background-color: transparent");
		}

        AnchorPane.setLeftAnchor(build, 0.0);
        AnchorPane.setBottomAnchor(build, 0.0);
        AnchorPane.setLeftAnchor(electAsMainAction, 0.0);
        AnchorPane.setBottomAnchor(electAsMainAction, 60.0);
        AnchorPane.setLeftAnchor(buildKing, 0.0);
        AnchorPane.setBottomAnchor(buildKing, 120.0);
        AnchorPane.setLeftAnchor(acquire, 0.0);
        AnchorPane.setBottomAnchor(acquire, 186.5);
        AnchorPane.setRightAnchor(additionalMainAction, 0.0);
        AnchorPane.setBottomAnchor(additionalMainAction, 0.0);
        AnchorPane.setRightAnchor(electAsQuickAction, 0.0);
        AnchorPane.setBottomAnchor(electAsQuickAction, 60.0);
        AnchorPane.setRightAnchor(changeBPT, 0.0);
        AnchorPane.setBottomAnchor(changeBPT, 120.0);
        AnchorPane.setRightAnchor(engageAssistant, 0.0);
        AnchorPane.setBottomAnchor(engageAssistant, 186.5);
        
        this.getChildren().addAll(build, electAsMainAction, buildKing, acquire, additionalMainAction, electAsQuickAction, changeBPT, engageAssistant);
	}
	
	private void addButtonsEffects() {
		for(Button b : actions) {
			b.addEventHandler(MouseEvent.MOUSE_EXITED, e -> b.setStyle("-fx-background-color: transparent"));
		}
		acquire.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> 
			acquire.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/acquireMousePassing.png") + ");" +
                "-fx-background-position: center;" +
                "-fx-background-size: 100% 100%;" + 
                "-fx-background-color: transparent"));
		
		buildKing.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> 
			buildKing.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/buildKingMousePassing.png") + ");" +
				"-fx-background-position: center;" +
				"-fx-background-size: 100% 100%;" + 
	            "-fx-background-color: transparent"));
		
		electAsMainAction.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> 
			electAsMainAction.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/electAsMainActionMousePassing.png") + ");" +
				"-fx-background-position: center;" +
				"-fx-background-size: 100% 100%;" + 
                "-fx-background-color: transparent"));
		
		build.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> 
			build.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/buildMousePassing.png") + ");" +
				"-fx-background-position: center;" +
				"-fx-background-size: 100% 100%;" + 
                "-fx-background-color: transparent"));
		
		engageAssistant.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> 
			engageAssistant.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/engageAssistantMousePassing.png") + ");" +
				"-fx-background-position: center;" +
				"-fx-background-size: 100% 100%;" + 
				"-fx-background-color: transparent"));
		
		changeBPT.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> 
			changeBPT.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/changeBPTMousePassing.png") + ");" +
				"-fx-background-position: center;" +
				"-fx-background-size: 100% 100%;" + 
				"-fx-background-color: transparent"));
		
		electAsQuickAction.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> 
			electAsQuickAction.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/electAsQuickActionMousePassing.png") + ");" +
				"-fx-background-position: center;" +
				"-fx-background-size: 100% 100%;" + 
				"-fx-background-color: transparent"));
		
		additionalMainAction.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> 
			additionalMainAction.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actionsButtons/additionalMainActionMousePassing.png") + ");" +
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
}
