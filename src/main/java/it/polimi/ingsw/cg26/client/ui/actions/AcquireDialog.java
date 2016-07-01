package it.polimi.ingsw.cg26.client.ui.actions;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.client.ui.BPTPane;
import it.polimi.ingsw.cg26.client.ui.PoliticCardPane;
import it.polimi.ingsw.cg26.common.commands.AcquireCommand;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.*;
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

        HBox tilesPane = new HBox();
        contentView.getChildren().add(tilesPane);
        tilesPane.setAlignment(Pos.CENTER);
        tilesPane.setSpacing(30.0);
        double tileWidth = 0.0645 * root.getWidth();
        double tileHeight = 0.085 * root.getHeight();
        ToggleGroup tilesToggleGroup = new ToggleGroup();
        Map<RadioButton, Integer> tilesMap = new HashMap<>();
        int i = 0;
        for (RegionDTO r: model.getRegions()) {
            HBox regionPane = new HBox();
            regionPane.setSpacing(10.0);
            List<Pane> tiles = new LinkedList<>();
            for (BusinessPermissionTileDTO t: r.getDeck().getOpenCards()) {
                VBox choicePane = new VBox(new BPTPane(tileWidth, tileHeight, t));
                choicePane.setSpacing(5.0);
                choicePane.setAlignment(Pos.CENTER);
                RadioButton radioButton = new RadioButton();
                tilesMap.put(radioButton, i);
                radioButton.setToggleGroup(tilesToggleGroup);
                choicePane.getChildren().addAll(radioButton);
                tiles.add(choicePane);
                i++;
            }
            Collections.reverse(tiles);
            regionPane.getChildren().addAll(tiles);
            tilesPane.getChildren().add(regionPane);
        }

        HBox cardsnPane = new HBox();
        cardsnPane.setAlignment(Pos.CENTER);
        contentView.getChildren().add(cardsnPane);
        cardsnPane.setSpacing(10.0);
        double cardWidth = 90.0; // TODO: make this parametric
        double cardHeight = 160.0;//tileHeight;
        Map<CheckBox, PoliticCardDTO> cardsMap = new HashMap<>();
        for (PoliticCardDTO c: model.getLocalPlayer().getCards()) {
            VBox choicePane = new VBox(new PoliticCardPane(cardWidth, cardHeight, c));
            choicePane.setSpacing(5.0);
            choicePane.setAlignment(Pos.CENTER);
            CheckBox checkBox = new CheckBox();
            cardsMap.put(checkBox, c);
            choicePane.getChildren().addAll(checkBox);
            cardsnPane.getChildren().add(choicePane);
        }



        setResultConverter(b -> {
            List<PoliticCardDTO> cards = cardsMap.entrySet().stream().filter(e -> e.getKey().isSelected()).map(Map.Entry::getValue).collect(Collectors.toList());
            int tileNumber = 0;
            int n = 0;
            for (Map.Entry<RadioButton, Integer> entry: tilesMap.entrySet()) {
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
