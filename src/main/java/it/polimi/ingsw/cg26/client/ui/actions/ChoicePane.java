package it.polimi.ingsw.cg26.client.ui.actions;

import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Created by davide on 01/07/16.
 */
public class ChoicePane extends VBox {

    private ChoicePane(ToggleGroup toggleGroup, Pane content) {
        getChildren().add(content);
        RadioButton radioButton = new RadioButton("GUI");
        radioButton.setToggleGroup(toggleGroup);
        radioButton.setAlignment(Pos.CENTER);
        getChildren().addAll(radioButton);
    }
}
