package it.polimi.ingsw.cg26.client.ui;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.dto.PlayerDTO;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 *
 */
public class FinalDialog extends Dialog {

    public FinalDialog(Model model) {
        VBox contentView = new VBox();
        getDialogPane().setContent(contentView);
        contentView.setSpacing(10.0);
        contentView.setAlignment(Pos.CENTER);

        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(buttonTypeOk);

        List<PlayerDTO> players = model.getPlayers();

        for (PlayerDTO p: players) {
            String text = players.indexOf(p) + 1 + ")         " + p.getName() + " with " + p.getVictoryPoints() + " points.";
            contentView.getChildren().add(new Label(text));
        }
    }
}
