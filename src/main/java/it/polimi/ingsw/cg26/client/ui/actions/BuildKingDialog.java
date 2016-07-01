package it.polimi.ingsw.cg26.client.ui.actions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.commands.BuildKingCommand;
import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;

public class BuildKingDialog extends Dialog<BuildKingCommand> {

	public BuildKingDialog(Model model) {
		VBox contentView = new VBox();
        getDialogPane().setContent(contentView);

        contentView.setSpacing(40.0);

        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(buttonTypeOk);

        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().add(buttonTypeCancel);
        
        //CityChoicePane cities1;
        //CityChoicePane cities2;
        //CityChoicePane cities3;
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
        
        PoliticCardsChoicePane cPane = new PoliticCardsChoicePane(new LinkedList<>(model.getLocalPlayer().getCards()));
        contentView.getChildren().add(cPane);
        
        setResultConverter(b -> {
        	List<PoliticCardDTO> cards = cPane.getCards();
        	/*for(Map.Entry<CheckBox, PoliticCardDTO> m : cPane.cardsMap.entrySet()) {
        		if(m.getKey().isSelected())
        			cards.add(m.getValue());
        	}*/
        	CityDTO chosenCity = null;
        	for(CityChoicePane c : citiesPanes)
        		for(Map.Entry<RadioButton, CityDTO> m : c.mapCities.entrySet()) {
        			if(m.getKey().isSelected()) {
        				chosenCity = m.getValue();
        				break;
        			}
        		}
        	if(b == buttonTypeOk)
        		return new BuildKingCommand(chosenCity, cards);
        	return null;
        });
	}
}
