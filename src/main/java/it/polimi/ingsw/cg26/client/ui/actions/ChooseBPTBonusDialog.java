package it.polimi.ingsw.cg26.client.ui.actions;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.client.ui.actions.panes.BPTChoicePane;
import it.polimi.ingsw.cg26.common.commands.ChooseBPTCommand;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;

/**
 *
 */
public class ChooseBPTBonusDialog extends Dialog<ChooseBPTCommand> {

    public ChooseBPTBonusDialog(Model model) {
        VBox contentView = new VBox();
        getDialogPane().setContent(contentView);

        contentView.setSpacing(40.0);

        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(buttonTypeOk);

        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().add(buttonTypeCancel);

        BPTChoicePane tPane = new BPTChoicePane(model.getRegions());

        contentView.getChildren().add(tPane);

        setResultConverter(b -> {
            if (b == buttonTypeOk)
                return new ChooseBPTCommand(tPane.getSelectedRegion(), tPane.getPosition());
            return null;
        });
    }
}
