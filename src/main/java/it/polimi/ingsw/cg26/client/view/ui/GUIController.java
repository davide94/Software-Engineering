package it.polimi.ingsw.cg26.client.view.ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class GUIController {

	@FXML private ImageView buyBPTMousePassing;
	@FXML private ImageView buyBPTMouseClicked;
	@FXML private ImageView engageAssistantMousePassing;
	@FXML private ImageView engageAssistantMouseClicked;
	@FXML private ImageView electKingMousePassing;
	@FXML private ImageView electKingMouseClicked;
	@FXML private ImageView changeBPTMousePassing;
	@FXML private ImageView changeBPTMouseClicked;
	@FXML private ImageView electMainMousePassing;
	@FXML private ImageView electMainMouseClicked;
	@FXML private ImageView electQuickMousePassing;
	@FXML private ImageView electQuickMouseClicked;
	@FXML private ImageView buildMousePassing;
	@FXML private ImageView buildMouseClicked;
	@FXML private ImageView additionalMainMousePassing;
	@FXML private ImageView additionalMainMouseClicked;
	
	
	@FXML protected void handleMouseClickOnTheMap(MouseEvent e) {
		System.out.println(e.getSceneX());
		System.out.println(e.getSceneY());
		/*if(e.getSceneX()>88 && e.getSceneX()<210 && e.getSceneY()>94 && e.getSceneY()<200)
			System.out.println("A");
		if(e.getSceneX()>260 && e.getSceneX()<400 && e.getSceneY()>150 && e.getSceneY()<250)
			System.out.println("C");*/
		
	}
	
	@FXML protected void handleMouseEnteredBuyBPT() {
		buyBPTMousePassing.setVisible(true);
	}
	
	@FXML protected void handleMouseExitedBuyBPT() {
		buyBPTMousePassing.setVisible(false);
	}
	
	@FXML protected void handleMousePressedBuyBPT() {
		buyBPTMousePassing.setVisible(false);
		buyBPTMouseClicked.setVisible(true);
	}
	
	@FXML protected void handleMouseReleasedBuyBPT() {
		buyBPTMouseClicked.setVisible(false);
		buyBPTMousePassing.setVisible(true);
		//TODO create command
	}
	
	@FXML protected void handleMouseEnteredEngageAssistant() {
		engageAssistantMousePassing.setVisible(true);
	}
	
	@FXML protected void handleMouseExitedEngageAssistant() {
		engageAssistantMousePassing.setVisible(false);
	}
	
	@FXML protected void handleMousePressedEngageAssistant() {
		engageAssistantMousePassing.setVisible(false);
		engageAssistantMouseClicked.setVisible(true);
	}
	
	@FXML protected void handleMouseReleasedEngageAssistant() {
		engageAssistantMouseClicked.setVisible(false);
		engageAssistantMousePassing.setVisible(true);
		//TODO create command
	}
	
	@FXML protected void handleMouseEnteredElectKing() {
		electKingMousePassing.setVisible(true);
	}
	
	@FXML protected void handleMouseExitedElectKing() {
		electKingMousePassing.setVisible(false);
	}
	
	@FXML protected void handleMousePressedElectKing() {
		electKingMousePassing.setVisible(false);
		electKingMouseClicked.setVisible(true);
	}
	
	@FXML protected void handleMouseReleasedElectKing() {
		electKingMouseClicked.setVisible(false);
		electKingMousePassing.setVisible(true);
		//TODO create command
	}
	
	@FXML protected void handleMouseEnteredChangeBPT() {
		changeBPTMousePassing.setVisible(true);
	}
	
	@FXML protected void handleMouseExitedChangeBPT() {
		changeBPTMousePassing.setVisible(false);
	}
	
	@FXML protected void handleMousePressedChangeBPT() {
		changeBPTMousePassing.setVisible(false);
		changeBPTMouseClicked.setVisible(true);
	}
	
	@FXML protected void handleMouseReleasedChangeBPT() {
		changeBPTMouseClicked.setVisible(false);
		changeBPTMousePassing.setVisible(true);
		//TODO create command
	}
	
	@FXML protected void handleMouseEnteredElectMain() {
		electMainMousePassing.setVisible(true);
	}
	
	@FXML protected void handleMouseExitedElectMain() {
		electMainMousePassing.setVisible(false);
	}
	
	@FXML protected void handleMousePressedElectMain() {
		electMainMousePassing.setVisible(false);
		electMainMouseClicked.setVisible(true);
	}
	
	@FXML protected void handleMouseReleasedElectMain() {
		electMainMouseClicked.setVisible(false);
		electMainMousePassing.setVisible(true);
		//TODO create command
	}
	
	@FXML protected void handleMouseEnteredElectQuick() {
		electQuickMousePassing.setVisible(true);
	}
	
	@FXML protected void handleMouseExitedElectQuick() {
		electQuickMousePassing.setVisible(false);
	}
	
	@FXML protected void handleMousePressedElectQuick() {
		electQuickMousePassing.setVisible(false);
		electQuickMouseClicked.setVisible(true);
	}
	
	@FXML protected void handleMouseReleasedElectQuick() {
		electQuickMouseClicked.setVisible(false);
		electQuickMousePassing.setVisible(true);
		//TODO create command
	}
	
	@FXML protected void handleMouseEnteredBuild() {
		buildMousePassing.setVisible(true);
	}
	
	@FXML protected void handleMouseExitedBuild() {
		buildMousePassing.setVisible(false);
	}
	
	@FXML protected void handleMousePressedBuild() {
		buildMousePassing.setVisible(false);
		buildMouseClicked.setVisible(true);
	}
	
	@FXML protected void handleMouseReleasedBuild() {
		buildMouseClicked.setVisible(false);
		buildMousePassing.setVisible(true);
		//TODO create command
	}
	
	@FXML protected void handleMouseEnteredAdditionalMain() {
		additionalMainMousePassing.setVisible(true);
	}
	
	@FXML protected void handleMouseExitedAdditionalMain() {
		additionalMainMousePassing.setVisible(false);
	}
	
	@FXML protected void handleMousePressedAdditionalMain() {
		additionalMainMousePassing.setVisible(false);
		additionalMainMouseClicked.setVisible(true);
	}
	
	@FXML protected void handleMouseReleasedAdditionalMain() {
		additionalMainMouseClicked.setVisible(false);
		additionalMainMousePassing.setVisible(true);
		//TODO create command
	}
}
