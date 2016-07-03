package it.polimi.ingsw.cg26.client.ui.actions;

import it.polimi.ingsw.cg26.client.ui.BPTPane;
import it.polimi.ingsw.cg26.common.commands.ChoosePlayerBPTCommand;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class ChoosePlayerBonusDialog extends Dialog<ChoosePlayerBPTCommand> {

    public ChoosePlayerBonusDialog(List<BusinessPermissionTileDTO> tiles) {
        VBox contentView = new VBox();
        getDialogPane().setContent(contentView);

        contentView.setSpacing(40.0);

        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(buttonTypeOk);

        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().add(buttonTypeCancel);

        List<RadioButton> buttons = new LinkedList<>();
        ToggleGroup toggleGroup = new ToggleGroup();

        for (BusinessPermissionTileDTO t: tiles) {
            VBox choicePane = new VBox(new BPTPane(10.0, 10.0, t));
            choicePane.setSpacing(5.0);
            choicePane.setAlignment(Pos.CENTER);
            RadioButton radioButton = new RadioButton();
            buttons.add(radioButton);
            radioButton.setToggleGroup(toggleGroup);
            choicePane.getChildren().addAll(radioButton);
            contentView.getChildren().add(choicePane);
        }

        setResultConverter(b -> {
            int i = 0;
            for (RadioButton rb: buttons)
                if (rb.isSelected())
                    i =  buttons.indexOf(rb);
            List<BusinessPermissionTileDTO> t = new LinkedList<>();
            t.add(tiles.get(i));
            if (b == buttonTypeOk)
                return new ChoosePlayerBPTCommand(t);
            return null;
        });
    }
}
