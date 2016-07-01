package it.polimi.ingsw.cg26.client.ui;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.dto.AssistantDTO;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.dto.MarketDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticColorDTO;
import it.polimi.ingsw.cg26.common.dto.SellableDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.CoinBonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.EmptyBonusDTO;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class MarketPane extends AnchorPane implements Observer{
	
	private Map<Pane, Label> onSalePaneLabels;
	
	private List<Pane> playerSellables;
	
	private Model model;
	
	public MarketPane(double width, double height, Model model) {
		this.model = model;
		this.onSalePaneLabels = new HashMap<>();
		this.playerSellables = new ArrayList<>();
		this.setPrefSize(width, height);
		this.setStyle("-fx-background-image: url(" + getClass().getResource("/img/marketBackground.png") + ");" +
                "-fx-background-position: center;" +
                "-fx-background-size: 100% 100%;");

		DropShadow shadow = new DropShadow();
		shadow.setOffsetY(3.0f);
		shadow.setRadius(50.0);
		shadow.setColor(Color.BLACK);
		setEffect(shadow);

		/*List<SellableDTO> onSale = new ArrayList<>();
		onSale.add(new PoliticCardDTO(new PoliticColorDTO("orange"), 2, "Marco"));
		onSale.add(new AssistantDTO(5, "Davide"));
		List<String> cities = new ArrayList<>();
		cities.add("Milano");
		cities.add("Ancona");
		onSale.add(new BusinessPermissionTileDTO(cities, new CoinBonusDTO(new EmptyBonusDTO(), 2), 2, "Luca"));
		model.setMarket(new MarketDTO(onSale));*/
		
		draw();
	}
	
	private void draw() {
		this.getChildren().clear();
		drawTitle();
		buildSellables();
		buildPlayerSellables();
	}
	
	private void drawTitle() {
		DropShadow shadow = new DropShadow();
        shadow.setRadius(2.0);
        shadow.setColor(Color.WHITE);
		Font goudyMedieval = Font.loadFont(getClass().getResource("/fonts/goudy_medieval/Goudy_Mediaeval_DemiBold.ttf").toExternalForm(), 50.0);
		Label title = new Label();
		title.setFont(goudyMedieval);
		title.setText("Market");
		title.setTextFill(Color.RED);
		title.setEffect(shadow);
		Font goudyMedieval2 = Font.loadFont(getClass().getResource("/fonts/goudy_medieval/Goudy_Mediaeval_DemiBold.ttf").toExternalForm(), 25.0);
		Label subTitle1 = new Label();
		subTitle1.setFont(goudyMedieval2);
		subTitle1.setText("Objects on sale");
		subTitle1.setTextFill(Color.BLACK);
		Label subTitle2 = new Label();
		subTitle2.setFont(goudyMedieval2);
		subTitle2.setText("Your sellable objects");
		subTitle2.setTextFill(Color.BLACK);
		AnchorPane.setTopAnchor(title, 0.0);
		AnchorPane.setLeftAnchor(title, this.getWidth() * 0.02);
		AnchorPane.setTopAnchor(subTitle1, this.getHeight() * 0.085);
		AnchorPane.setLeftAnchor(subTitle1, this.getWidth() * 0.02);
		AnchorPane.setTopAnchor(subTitle2, this.getHeight() * 0.565);
		AnchorPane.setLeftAnchor(subTitle2, this.getWidth() * 0.02);
		this.getChildren().add(title);
		this.getChildren().add(subTitle1);
		this.getChildren().add(subTitle2);
	}
	
	private void buildSellables() {
		onSalePaneLabels.clear();
		for(SellableDTO sellable : model.getMarket().getOnSale()) {
			Pane sellablePane = null;
			if(sellable.toString().contains("Assistant")) {
				sellablePane = new Pane();
				sellablePane.setPrefSize(60, 100);
				sellablePane.setStyle("-fx-background-image: url(" + getClass().getResource("/img/assistant.png") + ");" +
	                    "-fx-background-position: center;" +
	                    "-fx-background-size: 100% 100%;");
			}
			else if(sellable.toString().contains("PoliticCard")) {
				sellablePane = new PoliticCardPane(90.0, 160.0, (PoliticCardDTO) sellable);
			}
			else if(sellable.toString().contains("BusinessPermissionTile")) {
				sellablePane = new BPTPane(100, 100, (BusinessPermissionTileDTO) sellable);
			}
			double offset = this.getWidth() * 0.05;
			for(Map.Entry<Pane, Label> pl : onSalePaneLabels.entrySet()) {
				offset = offset + Math.max(pl.getKey().getPrefWidth(), pl.getValue().getPrefWidth()) + 25.0;
			}
			AnchorPane.setTopAnchor(sellablePane, this.getHeight() * 0.17);
			AnchorPane.setLeftAnchor(sellablePane, offset);
			Label label = drawPriceAndOwner(offset, sellablePane.getPrefHeight() + this.getHeight() * 0.17, sellable);
			this.getChildren().add(sellablePane);
			onSalePaneLabels.put(sellablePane, label);
		}
	}
	
	private void buildPlayerSellables() {
		playerSellables.clear();
		for(PoliticCardDTO card : model.getLocalPlayer().getCards()) {
			playerSellables.add(new PoliticCardPane(90.0, 160.0, card));
		}
		for(BusinessPermissionTileDTO tile : model.getLocalPlayer().getTiles()) {
			playerSellables.add(new BPTPane(100, 100, tile));
		}
		AnchorPane assistantPane = new AnchorPane();
		assistantPane.setPrefSize(60, 100);
		assistantPane.setStyle("-fx-background-image: url(" + getClass().getResource("/img/assistant.png") + ");" +
			"-fx-background-position: center;" +
			"-fx-background-size: 100% 100%;");
		Label multiplicityLabel = new Label();
        multiplicityLabel.setTextFill(Color.WHITE);
        multiplicityLabel.setFont(Font.font(15.0));
        multiplicityLabel.setText(Integer.toString(model.getLocalPlayer().getAssistantsNumber()));
        AnchorPane.setLeftAnchor(multiplicityLabel, 27.0);
        AnchorPane.setTopAnchor(multiplicityLabel, 43.0);
        assistantPane.getChildren().add(multiplicityLabel);
        playerSellables.add(assistantPane);
        
        double offset = this.getWidth() * 0.05;
        for(Pane p : playerSellables) {
        	AnchorPane.setTopAnchor(p, this.getHeight() * 0.65);
        	AnchorPane.setLeftAnchor(p, offset);
        	Button sellButton = new Button("Sell");
    		AnchorPane.setTopAnchor(sellButton, this.getHeight() * 0.65 + p.getPrefHeight() + 8.0);
    		AnchorPane.setLeftAnchor(sellButton, offset);
    		this.getChildren().add(sellButton);
        	this.getChildren().add(p);
        	offset = offset + p.getPrefWidth() + 25.0;
        }
	}
	
	private Label drawPriceAndOwner(double offsetX, double offsetY, SellableDTO sellable) {
		Font goudyMedieval = Font.loadFont(getClass().getResource("/fonts/goudy_medieval/Goudy_Mediaeval_DemiBold.ttf").toExternalForm(), 18.0);
		Label label = new Label();
		label.setText("Price: " + sellable.getPrice() + "\nOwner: " + sellable.getOwner());
		label.setTextFill(Color.BLACK);
		label.setFont(goudyMedieval);
		Text text = new Text("Price: " + sellable.getPrice() + "\nOwner: " + sellable.getOwner());
	    text.setFont(goudyMedieval);
	    label.setPrefWidth(text.getLayoutBounds().getWidth());
		AnchorPane.setTopAnchor(label, offsetY);
		AnchorPane.setLeftAnchor(label, offsetX);
		Button buyButton = new Button("Buy");
		AnchorPane.setTopAnchor(buyButton, offsetY + text.getLayoutBounds().getHeight() + 8.0);
		AnchorPane.setLeftAnchor(buyButton, offsetX);
		this.getChildren().add(buyButton);
		this.getChildren().add(label);
		return label;
	}

	@Override
	public void update(Observable o, Object arg) {
		draw();
	}
}
