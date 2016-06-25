package it.polimi.ingsw.cg26.client.view.ui;

import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 */
public class BPTPane extends GridPane {

    public BPTPane(double width, double height, BusinessPermissionTileDTO tile) {
        this.setAlignment(Pos.CENTER);
        //pane.setRotate((new Random().nextDouble() - 0.5) * 15.0);
        DropShadow shadow = new DropShadow();
        shadow.setRadius(4.0);
        shadow.setColor(Color.BLACK);
        this.setEffect(shadow);

        this.setPrefSize(width, height);
        this.setMaxSize(width, height);
        this.setStyle("-fx-background-image: url(" + getClass().getResource("/img/cards/bpt.png") + ");" +
                "-fx-background-position: center;" +
                "-fx-background-size: 100% 100%;");
        //System.out.println(tile.toString());
        String cities = "";
        for(String city: tile.getCities()) {
            cities += new String(new char[]{city.charAt(0)}).toUpperCase() + "/";
        }

        if (cities.length() > 0) {
            cities = cities.substring(0, cities.length()-1);
        }

        Label label = new Label(cities);
        label.setPrefWidth(width);
        label.setAlignment(Pos.CENTER);
        Font goudyMedieval = Font.loadFont(getClass().getResource("/fonts/goudy_medieval/Goudy_Mediaeval_DemiBold.ttf").toExternalForm(), 0.25 * width);
        label.setFont(goudyMedieval);

        this.add(label, 0, 0);

        Pane bonusPane = new BonusPane(width / 2.0, tile.getBonuses());
        this.add(bonusPane, 0, 1);
    }
}
