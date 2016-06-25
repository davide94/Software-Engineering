package it.polimi.ingsw.cg26.client.ui;

import it.polimi.ingsw.cg26.common.dto.BalconyDTO;
import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 *
 */
public class BalconyPane extends HBox {

    public BalconyPane(Point2D origin, double width, double height, BalconyDTO balcony) {
        AnchorPane.setLeftAnchor(this, origin.getX());
        AnchorPane.setTopAnchor(this, origin.getY());
        this.setPrefSize(width, height);
        this.setMaxSize(width, height);
        for(CouncillorDTO c : balcony.getCouncillors()) {
            Pane councillor = new Pane();
            councillor.setPrefSize(width/4, height);
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
            this.getChildren().add(councillor);
        }
    }
}
