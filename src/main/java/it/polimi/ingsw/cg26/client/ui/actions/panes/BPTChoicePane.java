package it.polimi.ingsw.cg26.client.ui.actions.panes;

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

    private List<RadioButton> buttons;

    private int step;

    private List<RegionDTO> regions;

    public static BPTChoicePane bptChoicePaneWithTiles(List<BusinessPermissionTileDTO> tiles) {
        return new BPTChoicePane(tiles, tiles.size());
    }

    public static BPTChoicePane bptChoicePaneWithRegions(List<RegionDTO> regions) {
        List<BusinessPermissionTileDTO> tiles = new LinkedList<>();
        regions.forEach(r -> tiles.addAll(r.getDeck().getOpenCards()));
        BPTChoicePane pane = new BPTChoicePane(tiles, 2);
        pane.regions = regions;
        return pane;
    }

    private BPTChoicePane(List<BusinessPermissionTileDTO> tiles, int step) {
        this.step = step;
        buttons = new LinkedList<>();
        setAlignment(Pos.CENTER);
        setSpacing(30.0);
        double tileWidth = 100.0;
        double tileHeight = 100.0;
        ToggleGroup tilesToggleGroup = new ToggleGroup();
        for (int i = 0; i < tiles.size() / step; i++) {
            HBox regionPane = new HBox();
            regionPane.setSpacing(10.0);
            List<Pane> tilesPanes = new LinkedList<>();
            for (int j = 0; j < step; j++) {
                VBox choicePane = new VBox(new BPTPane(tileWidth, tileHeight, tiles.get(i * step + j)));
                choicePane.setSpacing(5.0);
                choicePane.setAlignment(Pos.CENTER);
                RadioButton radioButton = new RadioButton();
                buttons.add(radioButton);
                radioButton.setToggleGroup(tilesToggleGroup);
                choicePane.getChildren().addAll(radioButton);
                tilesPanes.add(choicePane);
            }
            Collections.reverse(tilesPanes);
            regionPane.getChildren().addAll(tilesPanes);
            getChildren().add(regionPane);
        }

    }

    private int selectedIndex() {
        for (RadioButton b: buttons)
            if (b.isSelected())
                return buttons.indexOf(b);
        return 0;
    }

    public RegionDTO getSelectedRegion() {
        return (regions == null ? null :regions.get(selectedIndex() / step));
    }

    public int getPosition() {
        return selectedIndex() % step;
    }
}
