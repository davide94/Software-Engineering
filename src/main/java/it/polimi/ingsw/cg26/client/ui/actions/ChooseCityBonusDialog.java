package it.polimi.ingsw.cg26.client.ui.actions;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.client.ui.BonusPane;
import it.polimi.ingsw.cg26.common.commands.ChooseCityCommand;
import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.dto.EmporiumDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
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
 *
 */
public class ChooseCityBonusDialog extends Dialog<ChooseCityCommand> {

    public ChooseCityBonusDialog(Model model) {
        VBox contentView = new VBox();
        getDialogPane().setContent(contentView);

        contentView.setSpacing(40.0);

        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(buttonTypeOk);

        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().add(buttonTypeCancel);

        List<CheckBox> buttons = new LinkedList<>();
        contentView.setAlignment(Pos.CENTER);
        contentView.setSpacing(10.0);
        double cardWidth = 50.0;
        List<CityDTO> cities = new LinkedList<>();
        for (RegionDTO r: model.getRegions())
            for (CityDTO c: r.getCities())
                for (EmporiumDTO e: c.getEmporiums())
                    if (e.belongsTo(model.getLocalPlayer()) && !c.getBonuses().toString().contains("obility")) {
                        VBox choicePane = new VBox(new BonusPane(cardWidth, c.getBonuses()));
                        choicePane.setSpacing(5.0);
                        choicePane.setAlignment(Pos.CENTER);
                        CheckBox checkBox = new CheckBox();
                        buttons.add(checkBox);
                        cities.add(c);
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
