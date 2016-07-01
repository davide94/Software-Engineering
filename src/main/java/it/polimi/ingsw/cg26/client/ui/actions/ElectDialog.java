package it.polimi.ingsw.cg26.client.ui.actions;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.commands.*;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;

/**
 *
 */
public class ElectDialog extends Dialog<Command> {

    public ElectDialog(boolean asMain, Model model) {
        VBox contentView = new VBox();
        getDialogPane().setContent(contentView);

        contentView.setSpacing(40.0);

        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(buttonTypeOk);

        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().add(buttonTypeCancel);

        BalconyChoicePane bPane = new BalconyChoicePane(model);
        CouncillorsChoicePane cPane = new CouncillorsChoicePane(model.getCouncillorsPool());
        contentView.getChildren().addAll(bPane, cPane);

        setResultConverter(b -> {
            if (b == buttonTypeOk) {
                if (bPane.selectedIndex() <= 3)
                    if (asMain)
                        return new ElectAsMainActionCommand(model.getRegions().get(bPane.selectedIndex()), cPane.getSelectedCouncillor());
                    else
                        return new ElectAsQuickActionCommand(model.getRegions().get(bPane.selectedIndex()), cPane.getSelectedCouncillor());
                else
                    if (asMain)
                        return new ElectKingAsMainActionCommand(cPane.getSelectedCouncillor());
                    else
                        return new ElectKingAsQuickActionCommand(cPane.getSelectedCouncillor());
            }
            return null;
        });
    }
}
