package it.polimi.ingsw.cg26.client.ui.actions.panes;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.client.ui.CityPane;
import it.polimi.ingsw.cg26.common.dto.CityDTO;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityChoicePane extends HBox {

	public Map<RadioButton, CityDTO> mapCities;
	
	public CityChoicePane(List<CityDTO> cities, Model model){
		mapCities = new HashMap<>();
		setAlignment(Pos.CENTER);
        setSpacing(30.0);
        ToggleGroup citiesToggleGroup = new ToggleGroup();
        for(CityDTO c : cities) {
        	AnchorPane ap = new AnchorPane();
        	ap.getChildren().add(new CityPane(new Point2D(0, 0), 130, c, model));
        	VBox choicePane = new VBox(ap);
        	choicePane.setSpacing(5.0);
            choicePane.setAlignment(Pos.CENTER);
            RadioButton radioButton = new RadioButton();
            mapCities.put(radioButton, c);
            radioButton.setToggleGroup(citiesToggleGroup);
            choicePane.getChildren().add(radioButton);
            this.getChildren().add(choicePane);
        }

	}
}
