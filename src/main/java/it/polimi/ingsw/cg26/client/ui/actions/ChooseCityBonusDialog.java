package it.polimi.ingsw.cg26.client.ui.actions;

import it.polimi.ingsw.cg26.client.ui.BonusPane;
import it.polimi.ingsw.cg26.common.commands.ChooseCityCommand;
import it.polimi.ingsw.cg26.common.dto.CityDTO;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Build and displays a dialog to choose a bonus on a city where you have an emporium
 */
public class ChooseCityBonusDialog extends Dialog<ChooseCityCommand> {

	
	/**
	 * Default constructor
	 * @param cities is the list of cities in which you have an emporium
	 */
    public ChooseCityBonusDialog(List<CityDTO> cities, int multiplicity) {
        VBox contentView = new VBox();
        getDialogPane().setContent(contentView);

        contentView.setSpacing(40.0);

        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(buttonTypeOk);

        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().add(buttonTypeCancel);

        final int[] selected = {0};


        List<CheckBox> buttons = new LinkedList<>();
        contentView.setAlignment(Pos.CENTER);
        contentView.setSpacing(10.0);
        double cardWidth = 50.0;
        for (CityDTO c: cities) {
            VBox choicePane = new VBox(new BonusPane(cardWidth, c.getBonuses()));
            choicePane.setSpacing(5.0);
            choicePane.setAlignment(Pos.CENTER);
            CheckBox checkBox = new CheckBox();
            checkBox.setOnMouseClicked(e -> {
                if (checkBox.isSelected()) {
                    if (selected[0] < multiplicity) {
                        selected[0]++;
                    } else {
                        checkBox.setSelected(false);
                    }
                } else {
                    if (selected[0] > 1) {
                        selected[0]--;
                    } else {
                        checkBox.setSelected(true);
                    }
                }
            });
            buttons.add(checkBox);
            choicePane.getChildren().addAll(checkBox);
            contentView.getChildren().add(choicePane);
        }

        setResultConverter(b -> {
            if (b == buttonTypeOk)
                return new ChooseCityCommand(buttons.stream().filter(CheckBox::isSelected).map(cb ->
                        cities.get(buttons.indexOf(cb))).collect(Collectors.toList()));
            return null;
        });
    }
}
