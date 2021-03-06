package it.polimi.ingsw.cg26.client.ui.actions.panes;

import it.polimi.ingsw.cg26.client.ui.PoliticCardPane;
import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class PoliticCardsChoicePane extends HBox {

    private List<CheckBox> buttons;

    private List<PoliticCardDTO> cards;

    public PoliticCardsChoicePane(List<PoliticCardDTO> cards) {
        buttons = new LinkedList<>();
        this.cards = new LinkedList<>(cards);
        setAlignment(Pos.CENTER);
        setSpacing(10.0);
        double cardWidth = 90.0;
        double cardHeight = 160.0;

        final int[] selected = {0};

        for (PoliticCardDTO c: cards) {
            VBox choicePane = new VBox(new PoliticCardPane(cardWidth, cardHeight, c));
            choicePane.setSpacing(5.0);
            choicePane.setAlignment(Pos.CENTER);
            CheckBox checkBox = new CheckBox();
            checkBox.setOnMouseClicked(e -> {
                if (checkBox.isSelected()) {
                    if (selected[0] < 4) {
                        selected[0]++;
                    } else {
                        checkBox.setSelected(false);
                    }
                } else {
                    if (selected[0] > 1) {
                        selected[0]--;
                    } else {
                        checkBox.setSelected(true);
                    }
                }
            });
            buttons.add(checkBox);
            choicePane.getChildren().addAll(checkBox);
            getChildren().add(choicePane);
        }
    }

    public List<PoliticCardDTO> getCards() {
        return buttons.stream().filter(CheckBox::isSelected).map(b -> cards.get(buttons.indexOf(b))).collect(Collectors.toList());
    }
}
