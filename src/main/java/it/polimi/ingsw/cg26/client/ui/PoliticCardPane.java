package it.polimi.ingsw.cg26.client.ui;

import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import javafx.scene.layout.Pane;

/**
 *
 */
public class PoliticCardPane extends Pane {

    public PoliticCardPane(double width, double height, PoliticCardDTO card) {
        setPrefSize(width, height);
        String cardString = card.toString();
        String resource;
        if(cardString.contains("orange"))
            resource = "orange.png";
        else if(cardString.contains("white"))
            resource = "white.png";
        else if(cardString.contains("black"))
            resource = "black.png";
        else if(cardString.contains("violet"))
            resource = "violet.png";
        else if(cardString.contains("blue"))
            resource = "blue.png";
        else if(cardString.contains("pink"))
            resource = "pink.png";
        else
            resource = "multicolor.png";
        setStyle("-fx-background-image: url(" + getClass().getResource("/img/politicCards/" + resource) + ");" +
                "-fx-background-position: center;" +
                "-fx-background-size: 100% 100%;");
    }
}
