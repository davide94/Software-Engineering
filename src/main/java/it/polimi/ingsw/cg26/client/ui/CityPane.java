package it.polimi.ingsw.cg26.client.ui;

import it.polimi.ingsw.cg26.common.dto.CityColorDTO;
import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.dto.EmporiumDTO;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Arrays;
import java.util.List;

public class CityPane extends AnchorPane {
	
	private Label nameLabel;
	
	private Pane king;

    private List<Pane> emporiums;

    private List<Point2D> emporiumsOrigins = Arrays.asList(new Point2D(0.2, 0.7), new Point2D(0.35, 0.725), new Point2D(0.5, 0.75), new Point2D(0.65, 0.775));
	
	public CityPane(Point2D origin, double size, CityDTO city) {
        AnchorPane.setLeftAnchor(this, origin.getX());
        AnchorPane.setTopAnchor(this, origin.getY());
        this.setPrefSize(size, size);
        // create city bonus
        if (!city.getBonuses().toString().isEmpty()) {
            Pane bonusPane = new BonusPane(0.3 * size, city.getBonuses());//constructBonus(0.04 * root.getHeight(), 0.04 * root.getHeight(), city.getBonuses());
            AnchorPane.setLeftAnchor(bonusPane, 0.150 * size);
            AnchorPane.setTopAnchor(bonusPane, 0.075 * size);
            //bonusPane.setRotate((new Random().nextDouble() - 0.5) * 60.0);
            this.getChildren().add(bonusPane);
        }

        //add name
		DropShadow shadow = new DropShadow();
        shadow.setRadius(2.0);
        shadow.setColor(Color.BLACK);
		Font goudyMedieval = Font.loadFont(getClass().getResource("/fonts/goudy_medieval/Goudy_Mediaeval_DemiBold.ttf").toExternalForm(), 0.175 * size);
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
        king.setPrefSize(0.25 * size, 0.25 * size);
        king.setVisible(false);
        getChildren().add(king);

        //emporiums
        int i = 0;
        for (EmporiumDTO e: city.getEmporiums()) {
            Pane emporiumPane = new Pane();
            AnchorPane.setLeftAnchor(emporiumPane, size * emporiumsOrigins.get(i).getX());
            AnchorPane.setTopAnchor(emporiumPane, size * emporiumsOrigins.get(i).getY());
            emporiumPane.setStyle("-fx-background-image: url(" + getClass().getResource("/img/emporiums/emporium" + (i+1) + ".png") + ");" +
                    "-fx-background-position: center;" +
                    "-fx-background-size: cover");
            emporiumPane.setPrefSize(0.15 * size, 0.15 * size);
            getChildren().add(emporiumPane);
            i = (i + 1) % 4;
        }

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
        //style += "-fx-background-color: red;-fx-opacity: 0.5;";
        this.setStyle(style);
	}
	
	public Pane getKing() {
		return this.king;
	}
}
