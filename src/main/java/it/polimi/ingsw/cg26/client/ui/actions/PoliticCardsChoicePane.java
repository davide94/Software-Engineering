package it.polimi.ingsw.cg26.client.ui.actions;

import it.polimi.ingsw.cg26.client.ui.PoliticCardPane;
import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class PoliticCardsChoicePane extends HBox {

    public Map<CheckBox, PoliticCardDTO> cardsMap = new HashMap<>();

    public PoliticCardsChoicePane(List<PoliticCardDTO> cards) {
        setAlignment(Pos.CENTER);
        setSpacing(10.0);
        double cardWidth = 90.0; // TODO: make this parametric
        double cardHeight = 160.0;//tileHeight;
        for (PoliticCardDTO c: cards) {
            VBox choicePane = new VBox(new PoliticCardPane(cardWidth, cardHeight, c));
            choicePane.setSpacing(5.0);
            choicePane.setAlignment(Pos.CENTER);
            CheckBox checkBox = new CheckBox();
            cardsMap.put(checkBox, c);
            choicePane.getChildren().addAll(checkBox);
            getChildren().add(choicePane);
        }
    }
}
