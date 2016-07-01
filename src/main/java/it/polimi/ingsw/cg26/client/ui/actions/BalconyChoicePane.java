package it.polimi.ingsw.cg26.client.ui.actions;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.client.ui.BalconyPane;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class BalconyChoicePane extends HBox {

    private List<RadioButton> buttons;

    public BalconyChoicePane(Model model) {
        setSpacing(40.0);
        buttons = new LinkedList<>();

        ToggleGroup toggleGroup = new ToggleGroup();
        for (int i = 0; i < 4; i++) {
            Pane arrow = new Pane();
            arrow.setPrefSize(40.0, 40.0);
            arrow.setStyle("-fx-background-image: url(" + getClass().getResource("/img/bonuses/nobility.png") + ")" +
                    ";-fx-background-position: center;-fx-background-size: contain; -fx-background-repeat: no-repeat;");
            getChildren().add(arrow);
            VBox choicePane = new VBox(new BalconyPane(new Point2D(0.0, 0.0), 120.0, 50.0, model, i));
            choicePane.setSpacing(5.0);
            choicePane.setAlignment(Pos.CENTER);
            RadioButton radioButton = new RadioButton();
            buttons.add(radioButton);
            radioButton.setToggleGroup(toggleGroup);
            choicePane.getChildren().addAll(radioButton);
            getChildren().add(choicePane);
        }
    }

    public int selectedIndex() {
        for (RadioButton b: buttons)
            if (b.isSelected())
                return buttons.indexOf(b);
        return 0;
    }
}
