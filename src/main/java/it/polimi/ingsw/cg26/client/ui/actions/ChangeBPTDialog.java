package it.polimi.ingsw.cg26.client.ui.actions;

import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.commands.ChangeBPTCommand;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


/**
 * Build and displays a dialog to choose a region where you can change BPT
 *
 */
public class ChangeBPTDialog extends Dialog<ChangeBPTCommand> {

	/**
	 * Match a region with the button
	 */
	private Map<RadioButton, RegionDTO> regionsMap;
	
	
	/**
	 * Default constructor
	 * @param model of the client
	 */
	public ChangeBPTDialog(Model model) {
		regionsMap = new HashMap<>();
		VBox contentView = new VBox();
        getDialogPane().setContent(contentView);

        contentView.setSpacing(40.0);

        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(buttonTypeOk);

        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().add(buttonTypeCancel);
        
        HBox regions = new HBox();
        regions.setAlignment(Pos.CENTER);
        regions.setSpacing(30.0);
        ToggleGroup tilesToggleGroup = new ToggleGroup();
        for (RegionDTO r : model.getRegions()) {
            VBox choicePane = new VBox();
            Pane regionCard = new Pane();
            regionCard.setStyle("-fx-background-image: url(" + getClass().getResource("/img/coveredBPT/BPT" + r.getName() + ".png") + ");" +
                    "-fx-background-position: center;" +
                    "-fx-background-size: 100% 100%;");
            regionCard.setPrefSize(100, 100);
            choicePane.getChildren().add(regionCard);
            choicePane.setSpacing(5.0);
            choicePane.setAlignment(Pos.CENTER);
            RadioButton radioButton = new RadioButton();
            regionsMap.put(radioButton, r);
            radioButton.setToggleGroup(tilesToggleGroup);
            choicePane.getChildren().addAll(radioButton);
            regions.getChildren().add(choicePane);
        }
        contentView.getChildren().add(regions);
        
        setResultConverter(b -> {
        	for(Map.Entry<RadioButton, RegionDTO> r : regionsMap.entrySet()) {
        		if(r.getKey().isSelected() && b == buttonTypeOk)
        			return new ChangeBPTCommand(r.getValue());
        	}
        	return null;
        });
	}
}
