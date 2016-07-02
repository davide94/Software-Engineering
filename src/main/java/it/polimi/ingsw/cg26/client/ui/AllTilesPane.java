package it.polimi.ingsw.cg26.client.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.dto.CityColorDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AllTilesPane extends AnchorPane implements Observer {

	private static final int KING_DECK_SIZE = 5;
	
	private Model model;
	
	public AllTilesPane(Model model, Pane root) {
		this.setPrefSize(root.getWidth(), root.getHeight());
		this.model = model;
		draw();
	}
	
	private void draw() {
		this.getChildren().clear();
		buildColorBonuses();
		buildKingRewardTile();
		buildRegionTileBonuses();
	}
	
	private void buildColorBonuses() {
		List<Point2D> colorBonusesOrigins = Arrays.asList(new Point2D(0.749, 0.85), new Point2D(0.798, 0.842), new Point2D(0.847, 0.836), new Point2D(0.895, 0.83));

        for(Map.Entry<CityColorDTO, BonusDTO> t : model.getColorBonuses().entrySet()) {
    		String resource = null;
    		RewardTilePane rewardTile = new RewardTilePane(0.058 * this.getWidth(), 0.035 * this.getHeight(), t.getValue());
    		rewardTile.setRotate(45);
    		switch (t.getKey().getColor()) {
			case "iron":
				resource ="ironCityTile.png";
				AnchorPane.setLeftAnchor(rewardTile, colorBonusesOrigins.get(0).getX() * this.getWidth());
				AnchorPane.setTopAnchor(rewardTile, colorBonusesOrigins.get(0).getY() * this.getHeight());
				break;
			case "bronze":
				resource = "bronzeCityTile.png";
				AnchorPane.setLeftAnchor(rewardTile, colorBonusesOrigins.get(1).getX() * this.getWidth());
				AnchorPane.setTopAnchor(rewardTile, colorBonusesOrigins.get(1).getY() * this.getHeight());
				break;
			case "silver":
				resource = "silverCityTile.png";
				AnchorPane.setLeftAnchor(rewardTile, colorBonusesOrigins.get(2).getX() * this.getWidth());
				AnchorPane.setTopAnchor(rewardTile, colorBonusesOrigins.get(2).getY() * this.getHeight());
				break;
			case "gold":
				resource = "goldCityTile.png";
				AnchorPane.setLeftAnchor(rewardTile, colorBonusesOrigins.get(3).getX() * this.getWidth());
				AnchorPane.setTopAnchor(rewardTile, colorBonusesOrigins.get(3).getY() * this.getHeight());
				break;
			default:
				break;
			}
    		rewardTile.setStyle("-fx-background-image: url(" + getClass().getResource("/img/rewardTiles/" + resource) + ");" +
                    "-fx-background-position: center;" +
                    "-fx-background-size: 100% 100%;");
    		this.getChildren().add(rewardTile);
        }
    }
	
	private void buildKingRewardTile() {
    	int index = KING_DECK_SIZE - model.getKingDeck().getTiles().size() + 1;
    	RewardTilePane rewardTile = new RewardTilePane(0.058 * this.getWidth(), 0.037 * this.getHeight(), model.getKingDeck().getTiles().get(0).getBonuses());
    	rewardTile.setRotate(45);
    	AnchorPane.setLeftAnchor(rewardTile, 0.884 * this.getWidth());
    	AnchorPane.setTopAnchor(rewardTile, 0.751 * this.getHeight());
    	rewardTile.setStyle("-fx-background-image: url(" + getClass().getResource("/img/rewardTiles/kingTile.png") + ");" +
                "-fx-background-position: center;" +
                "-fx-background-size: 100% 100%;");
		Label indexLabel = new Label();
        indexLabel.setTextFill(Color.WHITE);
        indexLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25.0));
        indexLabel.setText(Integer.toString(index) + "°");
        AnchorPane.setLeftAnchor(indexLabel, 0.2 * rewardTile.getPrefWidth());
        AnchorPane.setBottomAnchor(indexLabel, 0.2 * rewardTile.getPrefHeight());
        rewardTile.getChildren().add(indexLabel);
        this.getChildren().add(rewardTile);
    }
	
	private void buildRegionTileBonuses() {
    	for(RegionDTO r : model.getRegions()) {
    		String resource = null;
    		double offset = 0;
    		RewardTilePane rewardTile = new RewardTilePane(0.058 * this.getWidth(), 0.037 * this.getHeight(), r.getBonus());
    		if(r.getBonus().toString().isEmpty())
    			rewardTile.setVisible(false);
    		switch (r.getName()) {
			case "coast":
				resource = "coast";
				offset = 0;
				break;
			case "hills":
				resource = "hills";
				offset = 0.3;
				break;
			case "mountains":
				resource = "mountains";
				offset = 0.63;
				break;
			default:
				break;
			}
    		AnchorPane.setLeftAnchor(rewardTile, (0.250 + offset) * this.getWidth());
    		AnchorPane.setTopAnchor(rewardTile, 0.51 * this.getHeight());
    		rewardTile.setStyle("-fx-background-image: url(" + getClass().getResource("/img/rewardTiles/"+ resource +"RewardTile.png") + ");" +
                    "-fx-background-position: center;" +
                    "-fx-background-size: 100% 100%;");
    		this.getChildren().add(rewardTile);
    	}
    }
	
	@Override
	public void update(Observable o, Object arg) {
		draw();
	}

}
