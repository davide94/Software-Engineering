package it.polimi.ingsw.cg26.client.ui.actions;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.client.ui.actions.panes.BPTChoicePane;
import it.polimi.ingsw.cg26.client.ui.actions.panes.PoliticCardsChoicePane;
import it.polimi.ingsw.cg26.common.commands.AcquireCommand;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.LinkedList;

/**
 *Build and displays a dialog to confirm or cancel the decision to acquire
 */
public class AcquireDialog extends Dialog<AcquireCommand> {

	
	/**
	 * Default constructor
	 * @param root of the guy
	 * @param model is the client model
	 */
    public AcquireDialog(Pane root, Model model) {
        VBox contentView = new VBox();
        getDialogPane().setContent(contentView);

        contentView.setSpacing(40.0);

        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(buttonTypeOk);

        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().add(buttonTypeCancel);

        BPTChoicePane tPane = BPTChoicePane.bptChoicePaneWithRegions(model.getRegions());
        PoliticCardsChoicePane cPane = new PoliticCardsChoicePane(new LinkedList<>(model.getLocalPlayer().getCards()));

        contentView.getChildren().addAll(tPane, cPane);

        setResultConverter(b -> {
            if (b == buttonTypeOk)
                return new AcquireCommand(tPane.getSelectedRegion(), cPane.getCards(), tPane.getPosition());
            return null;
        });
    }
}
