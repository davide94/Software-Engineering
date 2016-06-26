package it.polimi.ingsw.cg26.client.ui;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.dto.BalconyDTO;
import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 *
 */
public class BalconyPane extends HBox implements Observer {

    private final Model model;

    private final int index;

    public BalconyPane(Point2D origin, double width, double height, Model model, int index) {
        this.model = model;
        this.index = index;
        AnchorPane.setLeftAnchor(this, origin.getX());
        AnchorPane.setTopAnchor(this, origin.getY());
        setPrefSize(width, height);
        setMaxSize(width, height);

        draw();
    }

    private void draw() {
        getChildren().clear();

        BalconyDTO balcony = model.getKingBalcony();
        if (index < model.getRegions().size())
            balcony = model.getRegions().get(index).getBalcony();

        List<CouncillorDTO> councillors = balcony.getCouncillors();
        Collections.reverse(councillors);
        for(CouncillorDTO c : councillors) {
            Pane councillor = new Pane();
            councillor.setPrefSize(getPrefWidth()/4, getPrefHeight());
            String resource = null;
            switch (c.getColor().getColor()) {
                case "white": resource = "White_Councillor.png";
                    break;
                case "violet": resource = "Violet_Councillor.png";
                    break;
                case "blue": resource = "Blue_Councillor.png";
                    break;
                case "orange": resource = "Orange_Councillor.png";
                    break;
                case "black": resource = "Black_Councillor.png";
                    break;
                case "pink": resource = "Pink_Councillor.png";
                    break;
                default:
                    break;
            }
            councillor.setStyle("-fx-background-image: url(" + getClass().getResource("/img/councillors/" + resource) + ");" +
                    "-fx-background-position: center;" +
                    "-fx-background-size: 100% 100%;");
            getChildren().add(councillor);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        draw();
    }
}
