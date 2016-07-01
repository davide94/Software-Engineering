package it.polimi.ingsw.cg26.client.ui.actions;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.commands.AcquireCommand;
import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
public class AcquireDialog extends Dialog<AcquireCommand> {


    public AcquireDialog(Pane root, Model model) {
        VBox contentView = new VBox();
        getDialogPane().setContent(contentView);

        contentView.setSpacing(40.0);

        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(buttonTypeOk);

        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().add(buttonTypeCancel);

        BPTChoicePane tPane = new BPTChoicePane(model.getRegions());
        PoliticCardsChoicePane cPane = new PoliticCardsChoicePane(new LinkedList<>(model.getLocalPlayer().getCards()));

        contentView.getChildren().addAll(tPane, cPane);

        setResultConverter(b -> {
            List<PoliticCardDTO> cards = cPane.cardsMap.entrySet().stream().filter(e -> e.getKey().isSelected()).map(Map.Entry::getValue).collect(Collectors.toList());
            int tileNumber = 0;
            int n = 0;
            for (Map.Entry<RadioButton, Integer> entry: tPane.tilesMap.entrySet()) {
                if (entry.getKey().isSelected())
                    tileNumber = n;
                n++;
            }
            RegionDTO region = model.getRegions().get(tileNumber / 2);
            final int position = tileNumber % 2;
            if (b == buttonTypeOk)
                return new AcquireCommand(region, cards, position);
            return null;
        });
    }
}
