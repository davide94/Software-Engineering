package it.polimi.ingsw.cg26.client.ui.actions;

import it.polimi.ingsw.cg26.client.ui.BPTPane;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.*;

/**
 *
 */
public class BPTChoicePane extends HBox {

    public Map<RadioButton, Integer> tilesMap = new HashMap<>();

    public BPTChoicePane(List<RegionDTO> regions) {
        setAlignment(Pos.CENTER);
        setSpacing(30.0);
        double tileWidth = 100.0;
        double tileHeight = 100.0;
        ToggleGroup tilesToggleGroup = new ToggleGroup();
        int i = 0;
        for (RegionDTO r: regions) {
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
            getChildren().add(regionPane);
        }
    }
}
