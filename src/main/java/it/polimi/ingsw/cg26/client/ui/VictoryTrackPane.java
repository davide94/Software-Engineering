package it.polimi.ingsw.cg26.client.ui;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.dto.PlayerDTO;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.*;

/**
 *
 */
public class VictoryTrackPane extends AnchorPane implements Observer {

    private Model model;

    private static  final int w = 27;

    private static  final int h = 21;

    private List<GridPane> cells;

    private double cellSize;

    public VictoryTrackPane(double width, double height, double thickness, Model model) {
        this.model = model;

        setPrefSize(width, height - 0.5 * thickness);

        HBox top = new HBox();
        top.setPrefSize(0.98 * width, thickness);
        AnchorPane.setLeftAnchor(top, 0.01 * width);
        AnchorPane.setTopAnchor(top, 0.0);

        VBox right = new VBox();
        right.setPrefSize(thickness, height - 2.6 *thickness);
        AnchorPane.setRightAnchor(right, 0.0);
        AnchorPane.setTopAnchor(right, thickness);

        HBox bottom = new HBox();
        bottom.setPrefSize(0.98 * width, thickness);

        AnchorPane.setLeftAnchor(bottom, 0.01 * width);
        AnchorPane.setTopAnchor(bottom, height - 1.6 * thickness);

        VBox left = new VBox();
        left.setPrefSize(thickness, height - 2.6 * thickness);
        AnchorPane.setLeftAnchor(left, 0.0);
        AnchorPane.setTopAnchor(left, thickness);

        /*top.setStyle("-fx-background-color: red;-fx-opacity: 0.5;");
        left.setStyle("-fx-background-color: red;-fx-opacity: 0.5;");
        bottom.setStyle("-fx-background-color: red;-fx-opacity: 0.5;");
        right.setStyle("-fx-background-color: red;-fx-opacity: 0.5;");
        */
        getChildren().addAll(top, right, bottom, left);

        cells = new LinkedList<>();
        cellSize = width / w;
        for (int i = 0; i <= w; i++) {
            GridPane pedinePane = new GridPane();
            //pedinePane.setStyle("-fx-border-width: 1px;-fx-border-color: black;");
            pedinePane.setPrefSize(cellSize, thickness);
            pedinePane.setHgap(3.0);
            pedinePane.setVgap(3.0);
            pedinePane.setAlignment(Pos.CENTER);
            cells.add(pedinePane);
            top.getChildren().add(pedinePane);
        }

        cellSize = (height - 2.6 * thickness) / h;
        for (int i = 0; i <= h; i++) {
            GridPane pedinePane = new GridPane();
            //pedinePane.setStyle("-fx-border-width: 1px;-fx-border-color: black;");
            pedinePane.setPrefSize(thickness, cellSize);
            pedinePane.setHgap(3.0);
            pedinePane.setVgap(3.0);
            pedinePane.setAlignment(Pos.CENTER);
            cells.add(pedinePane);
            right.getChildren().add(pedinePane);
        }

        List<GridPane> tmp = new LinkedList<>();
        cellSize = width / w;
        for (int i = 0; i <= w; i++) {
            GridPane pedinePane = new GridPane();
            //pedinePane.setStyle("-fx-border-width: 1px;-fx-border-color: black;");
            pedinePane.setPrefSize(cellSize, thickness);
            pedinePane.setHgap(3.0);
            pedinePane.setVgap(3.0);
            pedinePane.setAlignment(Pos.CENTER);
            cells.add(pedinePane);
            tmp.add(pedinePane);
        }
        Collections.reverse(tmp);
        bottom.getChildren().addAll(tmp);

        tmp = new LinkedList<>();
        cellSize = (height - 2.6 * thickness) / h;
        for (int i = 0; i <= h; i++) {
            GridPane pedinePane = new GridPane();
            //pedinePane.setStyle("-fx-border-width: 1px;-fx-border-color: black;");
            pedinePane.setPrefSize(thickness, cellSize);
            pedinePane.setHgap(3.0);
            pedinePane.setVgap(3.0);
            pedinePane.setAlignment(Pos.CENTER);
            cells.add(pedinePane);
            tmp.add(pedinePane);
        }
        Collections.reverse(tmp);
        left.getChildren().addAll(tmp);

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
            pedina.setMinSize(0.5 * cellSize, 0.5 * cellSize);
            pedina.setMaxSize(0.5 * cellSize, 0.5 * cellSize);
            pedina.setStyle("-fx-background-color: " + colors.get(i % 4) + ";-fx-background-radius: 50%;");
            this.setEffect(shadow);
            pedina.setEffect(shadow);
            GridPane cell = cells.get(player.getVictoryPoints() % 100);
            cell.add(pedina, (cell.getChildren().size() / 2) % 2, cell.getChildren().size() % 2);
            i++;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        draw();
    }
}
