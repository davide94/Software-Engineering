package it.polimi.ingsw.cg26.client.ui.actions;

import it.polimi.ingsw.cg26.client.ui.actions.panes.BPTChoicePane;
import it.polimi.ingsw.cg26.common.commands.ChooseBPTCommand;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 *
 */
public class ChooseBPTDialog extends Dialog<ChooseBPTCommand> {

    public ChooseBPTDialog(List<BusinessPermissionTileDTO> tiles) {
        VBox contentView = new VBox();
        getDialogPane().setContent(contentView);

        contentView.setSpacing(40.0);

        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(buttonTypeOk);

        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().add(buttonTypeCancel);

        BPTChoicePane tPane = BPTChoicePane.bptChoicePaneWithTiles(tiles);

        contentView.getChildren().add(tPane);

        setResultConverter(b -> {
            if (b == buttonTypeOk)
                return new ChooseBPTCommand(tPane.getSelectedRegion(), tPane.getPosition());
            return null;
        });
    }
}
