package it.polimi.ingsw.cg26.client.ui.actions;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.client.ui.BPTPane;
import it.polimi.ingsw.cg26.client.ui.actions.panes.CityChoicePane;
import it.polimi.ingsw.cg26.common.commands.BuildCommand;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Build and displays a dialog to choose and confirm where to build
 *
 */
public class BuildDialog extends Dialog<BuildCommand> {
	
	
	/**
	 * Default constructor
	 * @param model is the client model
	 */
	public BuildDialog(Model model) {
		VBox contentView = new VBox();
		getDialogPane().setContent(contentView);

		contentView.setSpacing(40.0);

		ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().add(buttonTypeOk);

		ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
		getDialogPane().getButtonTypes().add(buttonTypeCancel);
		
		List<CityChoicePane> citiesPanes = new ArrayList<>();
        List<CityDTO> cities = new ArrayList<>();
        for(RegionDTO r : model.getRegions()) {
        	for(CityDTO c : r.getCities()) {
        		cities.add(c);
        		if(cities.size() == 5) {
        			CityChoicePane p = new CityChoicePane(cities, model);
        			citiesPanes.add(p);
        			contentView.getChildren().add(p);
        			cities.clear();
        		}
        	}	
        }
        if(!cities.isEmpty()) {
        	CityChoicePane p = new CityChoicePane(cities, model);
        	contentView.getChildren().add(p);
        	citiesPanes.add(p);
        }
        
        Map<RadioButton, BusinessPermissionTileDTO> tileMap = new HashMap<>();
        HBox tilesBox = new HBox();
        tilesBox.setAlignment(Pos.CENTER);
        tilesBox.setSpacing(30.0);
        ToggleGroup tilesToggleGroup = new ToggleGroup();
        for(BusinessPermissionTileDTO t : model.getLocalPlayer().getTiles()) {
        	VBox choicePane = new VBox(new BPTPane(100, 100, t));
        	choicePane.setSpacing(5.0);
            choicePane.setAlignment(Pos.CENTER);
            RadioButton radioButton = new RadioButton();
            tileMap.put(radioButton, t);
            radioButton.setToggleGroup(tilesToggleGroup);
            choicePane.getChildren().addAll(radioButton);
            tilesBox.getChildren().add(choicePane);
        }
        contentView.getChildren().add(tilesBox);
        
        setResultConverter(b -> {
        	CityDTO chosenCity = null;
        	for(CityChoicePane c : citiesPanes)
        		for(Map.Entry<RadioButton, CityDTO> m : c.mapCities.entrySet()) {
        			if(m.getKey().isSelected()) {
        				chosenCity = m.getValue();
        				break;
        			}
        		}
        	BusinessPermissionTileDTO chosenTile = null;
        	for(Map.Entry<RadioButton, BusinessPermissionTileDTO> t : tileMap.entrySet()) {
        		if(t.getKey().isSelected()) {
        			chosenTile = t.getValue();
        			break;
        		}
        	}
        	if(b == buttonTypeOk)
        		return new BuildCommand(chosenCity, chosenTile);
        	return null;
        });
	}
}
