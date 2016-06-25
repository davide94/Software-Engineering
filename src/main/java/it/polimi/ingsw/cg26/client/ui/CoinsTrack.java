package it.polimi.ingsw.cg26.client.ui;

import it.polimi.ingsw.cg26.common.dto.PlayerDTO;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class CoinsTrack extends HBox {

    private static  final int len = 21;

    private List<GridPane> cells;

    public CoinsTrack(Point2D origin, double width, double height, List<PlayerDTO> players) {
        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);
        AnchorPane.setLeftAnchor(this, origin.getX());
        AnchorPane.setTopAnchor(this, origin.getY());

        //setStyle("-fx-background-color: red;-fx-opacity: 0.5;");

        double cellSize = width / len;
        cells = new LinkedList<>();
        for (int i = 0; i < len; i++) {
            GridPane pedinePane = new GridPane();
            pedinePane.setPrefSize(cellSize, height);
            pedinePane.setHgap(3.0);
            pedinePane.setVgap(3.0);
            pedinePane.setAlignment(Pos.CENTER);
            cells.add(pedinePane);
            getChildren().add(pedinePane);
        }

        DropShadow shadow = new DropShadow();
        shadow.setRadius(2.0);
        shadow.setColor(Color.rgb(0, 0, 0, 0.7));
        shadow.setOffsetY(3.0);
        shadow.setOffsetX(-1.0);

        List<String> colors = Arrays.asList("dodgerblue", "orangered", "limegreen", "yellow");
        for (PlayerDTO player: players) {
            Pane pedina = new Pane();
            pedina.setMinSize(0.5 * cellSize, 0.5 * cellSize);
            pedina.setMaxSize(0.5 * cellSize, 0.5 * cellSize);
            pedina.setStyle("-fx-background-color: " + colors.get(players.indexOf(player) % 4) + ";-fx-background-radius: 50%;");
            this.setEffect(shadow);
            pedina.setEffect(shadow);
            GridPane cell = cells.get(player.getCoins());
            cell.add(pedina, (cell.getChildren().size() / 2) % 2, cell.getChildren().size() % 2);
        }
    }
}
