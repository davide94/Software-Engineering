package it.polimi.ingsw.cg26.client.ui;

import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import javafx.scene.layout.Pane;

/**
 *
 */
public class CouncillorPane extends Pane {

    public CouncillorPane(double width, double height, CouncillorDTO councillor) {
        setPrefSize(width, height);
        String resource = null;
        switch (councillor.getColor().getColor()) {
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
        setStyle("-fx-background-image: url(" + getClass().getResource("/img/councillors/" + resource) + ");" +
                "-fx-background-position: center;" +
                "-fx-background-size: 100% 100%;");
    }
}
