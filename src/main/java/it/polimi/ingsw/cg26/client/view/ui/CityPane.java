package it.polimi.ingsw.cg26.client.view.ui;

import it.polimi.ingsw.cg26.common.dto.CityColorDTO;
import it.polimi.ingsw.cg26.common.dto.CityDTO;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CityPane extends AnchorPane {
	
	private Label nameLabel;
	
	private Pane king;
	
	private HBox emporiums;
	
	public CityPane(Pane root, CityDTO city) {
		//add name
		DropShadow shadow = new DropShadow();
        shadow.setRadius(2.0);
        shadow.setColor(Color.BLACK);
		Font goudyMedieval = Font.loadFont(getClass().getResource("/fonts/goudy_medieval/Goudy_Mediaeval_DemiBold.ttf").toExternalForm(), 0.025 * root.getHeight());
		nameLabel = new Label(city.getName().substring(0, 1).toUpperCase() + city.getName().substring(1));
		nameLabel.setEffect(shadow);
		nameLabel.setFont(goudyMedieval);
		nameLabel.setTextFill(Color.rgb(137, 135, 143));
		nameLabel.setRotate(45.0);
		AnchorPane.setRightAnchor(nameLabel, 10.0);
		AnchorPane.setTopAnchor(nameLabel, 10.0);
		this.getChildren().add(nameLabel);
		
		//add king
        king = new Pane();
        king.setStyle("-fx-background-image: url(" + getClass().getResource("/img/cities/king.png") + ");" +
                "-fx-background-position: center;" +
                "-fx-background-size: 100% 100%;");
        AnchorPane.setRightAnchor(king, 15.0);
        AnchorPane.setBottomAnchor(king, 45.0);
        king.setPrefSize(0.04 * root.getHeight(), 0.04 * root.getHeight());
        king.setVisible(false);
        this.getChildren().add(king);
        
        //add background
        addBackground(city);
	}
	
	private void addBackground(CityDTO city) {
		String style = "-fx-background-image: url(" + getClass().getResource("/img/cities/violet.png") + ");";
        if (city.getColor().equals(new CityColorDTO("iron"))) {
            style = "-fx-background-image: url(" + getClass().getResource("/img/cities/iron.png") + ");";
            nameLabel.setTextFill(Color.rgb(62, 171, 182));
        }
        if (city.getColor().equals(new CityColorDTO("bronze"))) {
            style = "-fx-background-image: url(" + getClass().getResource("/img/cities/bronze.png") + ");";
            nameLabel.setTextFill(Color.rgb(196, 112, 81));
        }
        if (city.getColor().equals(new CityColorDTO("silver"))) {
            style = "-fx-background-image: url(" + getClass().getResource("/img/cities/silver.png") + ");";
            nameLabel.setTextFill(Color.rgb(150, 155, 159));
        }
        if (city.getColor().equals(new CityColorDTO("gold"))) {
            style = "-fx-background-image: url(" + getClass().getResource("/img/cities/gold.png") + ");";
            nameLabel.setTextFill(Color.rgb(186, 148, 38));
        }

        style += "-fx-background-position: center;-fx-background-size: 100% 100%;";
        this.setStyle(style);
	}
	
	public Pane getKing() {
		return this.king;
	}
}
