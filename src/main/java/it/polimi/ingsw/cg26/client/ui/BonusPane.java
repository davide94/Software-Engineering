package it.polimi.ingsw.cg26.client.ui;

import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class BonusPane extends GridPane {

    public BonusPane(double size, BonusDTO bonus) {
        this.setPrefWidth(size);
        this.setPrefHeight(size);
        this.setAlignment(Pos.CENTER);
        List<String> bonusesStrings = Arrays.asList(bonus.toString().split(","));
        int i = 0;
        for (String bonusString: bonusesStrings) {
            GridPane bonusPane = new GridPane();
            if (bonusString.isEmpty())
                break;
            double bonusSize = 0.75 * size /(double) bonusesStrings.size();
            //double bonusSize = bonusesStrings.size() == 1 ? size * 0.75 : size /(double) bonusesStrings.size();
            bonusPane.setPrefSize(bonusSize, bonusSize);

            String multiplicity = "";
            int j = 0;
            boolean found = false;
            while (true) {
                if (bonusString.charAt(j) > 47 && bonusString.charAt(j) < 58) {
                    found = true;
                    multiplicity += new String(new char[]{bonusString.charAt(j)});
                } else if (found)
                    break;
                j++;
            }

            String styleString = "";
            if (bonusString.contains("Assistants"))
                styleString += "-fx-background-image: url(" + getClass().getResource("/img/bonuses/assistants.png") + ");";
            if (bonusString.contains("Cards"))
                styleString += "-fx-background-image: url(" + getClass().getResource("/img/bonuses/cards.png") + ");";
            if (bonusString.contains("Coins"))
                styleString += "-fx-background-image: url(" + getClass().getResource("/img/bonuses/coins.png") + ");";
            if (bonusString.contains("Main"))
                styleString += "-fx-background-image: url(" + getClass().getResource("/img/bonuses/main.png") + ");";
            if (bonusString.contains("Nobility"))
                styleString += "-fx-background-image: url(" + getClass().getResource("/img/bonuses/nobility.png") + ");";
            if (bonusString.contains("Take BPT")) {
                multiplicity = "";
                styleString += "-fx-background-image: url(" + getClass().getResource("/img/bonuses/TakeBPT.png") + ");";
            }
            if (bonusString.contains("Take Your")) {
                if (multiplicity.equals("1"))
                    styleString += "-fx-background-image: url(" + getClass().getResource("/img/bonuses/TakeCityBonus.png") + ");";
                else
                    styleString += "-fx-background-image: url(" + getClass().getResource("/img/bonuses/TakeCity2.png") + ");";
                multiplicity = "";
            }
            if (bonusString.contains("Take Player")) {
                multiplicity = "";
                styleString += "-fx-background-image: url(" + getClass().getResource("/img/bonuses/TakeYour.png") + ");";
            }
            if (bonusString.contains("Victory"))
                styleString += "-fx-background-image: url(" + getClass().getResource("/img/bonuses/victory.png") + ");";
            styleString += "-fx-background-position: center;-fx-background-size: contain; -fx-background-repeat: no-repeat;";

            //styleString += "-fx-background-color: red;";
            //styleString += "-fx-border-width: 1px;-fx-border-color: black;";
            bonusPane.setStyle(styleString);
            bonusPane.setAlignment(Pos.CENTER);

            Label multiplicityLabel = new Label();
            multiplicityLabel.setTextFill(Color.WHITE);
            multiplicityLabel.setFont(Font.font(9.0));
            multiplicityLabel.setText(multiplicity);
            bonusPane.add(multiplicityLabel, 0, 0);
            this.add(bonusPane, i, 1);
            i++;
        }
    }
}
