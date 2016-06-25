package it.polimi.ingsw.cg26.client.ui;

import it.polimi.ingsw.cg26.common.dto.NobilityCellDTO;
import it.polimi.ingsw.cg26.common.dto.NobilityTrackDTO;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class NobilityTrackPane extends HBox {

    private List<Pane> cells;

    public NobilityTrackPane(Point2D origin, double width, double height, NobilityTrackDTO nobilityTrack) {
        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);
        AnchorPane.setLeftAnchor(this, origin.getX());
        AnchorPane.setTopAnchor(this, origin.getY());

        //setStyle("-fx-background-color: red;-fx-opacity: 0.5;");

        double cellSize = width / nobilityTrack.getCells().size();
        cells = new LinkedList<>();
        for (NobilityCellDTO cell: nobilityTrack.getCells()) {
            cells.add(new BonusPane(cellSize, cell.getBonuses()));
        }

        getChildren().addAll(cells);
    }

    private Pane createCellPane(NobilityCellDTO cell, double size) {
        Pane pane = new BonusPane(size, cell.getBonuses());
        return pane;
    }
}
