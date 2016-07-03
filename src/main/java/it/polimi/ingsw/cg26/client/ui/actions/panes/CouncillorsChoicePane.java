package it.polimi.ingsw.cg26.client.ui.actions.panes;

import it.polimi.ingsw.cg26.client.ui.CouncillorPane;
import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class CouncillorsChoicePane extends HBox {

    private List<RadioButton> buttons;

    private List<CouncillorDTO> councillors;

    public CouncillorsChoicePane(Collection<CouncillorDTO> councillorsPool) {
        buttons = new LinkedList<>();
        councillors = new LinkedList<>();
        setAlignment(Pos.CENTER);
        ToggleGroup toggleGroup = new ToggleGroup();

        for (CouncillorDTO c: councillorsPool) {
            VBox choicePane = new VBox(new CouncillorPane(30.0, 50.0, c));
            choicePane.setSpacing(5.0);
            choicePane.setAlignment(Pos.CENTER);
            RadioButton radioButton = new RadioButton();
            buttons.add(radioButton);
            councillors.add(c);
            radioButton.setToggleGroup(toggleGroup);
            choicePane.getChildren().addAll(radioButton);
            getChildren().add(choicePane);
        }
    }

    public CouncillorDTO getSelectedCouncillor() {
        for (RadioButton b: buttons)
            if (b.isSelected())
                return councillors.get(buttons.indexOf(b));
        return councillors.get(0);
    }
}
