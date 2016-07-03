package it.polimi.ingsw.cg26.client.ui;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.dto.NobilityCellDTO;
import it.polimi.ingsw.cg26.common.dto.PlayerDTO;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.*;

/**
 *
 */
public class NobilityTrackPane extends HBox implements Observer {

    private Model model;

    private List<GridPane> cells;

    private double cellSize;

    public NobilityTrackPane(Point2D origin, double width, double height, Model model) {
        this.model = model;

        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);
        AnchorPane.setLeftAnchor(this, origin.getX());
        AnchorPane.setTopAnchor(this, origin.getY());

        //setStyle("-fx-background-color: red;-fx-opacity: 0.5;");

        cellSize = width / model.getNobilityTrack().getCells().size();
        cells = new LinkedList<>();
        for (NobilityCellDTO cell: model.getNobilityTrack().getCells()) {
            GridPane cellPane = new GridPane();
            cellPane.setAlignment(Pos.CENTER);
            cellPane.add(new BonusPane(cellSize, cell.getBonuses()), 0, 0);
            GridPane pedinePane = new GridPane();
            pedinePane.setHgap(3.0);
            pedinePane.setVgap(3.0);
            pedinePane.setAlignment(Pos.CENTER);
            cells.add(pedinePane);
            cellPane.add(pedinePane, 0, 0);
            getChildren().add(cellPane);
        }

        draw();
    }

    private void draw() {
        DropShadow shadow = new DropShadow();
        shadow.setRadius(2.0);
        shadow.setColor(Color.rgb(0, 0, 0, 0.7));
        shadow.setOffsetY(3.0);
        shadow.setOffsetX(-1.0);

        for (Pane c: cells)
            c.getChildren().clear();

        List<String> colors = Arrays.asList("dodgerblue", "orangered", "limegreen", "yellow");
        int i = 0;
        for (PlayerDTO player: model.getPlayers()) {
            Pane pedina = new Pane();
            pedina.setPrefSize(0.5 * cellSize, 0.5 * cellSize);
            pedina.setMaxSize(0.5 * cellSize, 0.5 * cellSize);
            pedina.setStyle("-fx-background-color: " + colors.get(i % 4) + ";-fx-background-radius: 50%;");
            this.setEffect(shadow);
            pedina.setEffect(shadow);
            GridPane cell = cells.get(Math.min(player.getNobilityCell(), 20));
            cell.add(pedina, (cell.getChildren().size() / 2) % 2, cell.getChildren().size() % 2);
            i++;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        draw();
    }
}