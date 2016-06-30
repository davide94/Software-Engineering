package it.polimi.ingsw.cg26.client.ui;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.dto.SellableDTO;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class MarketPane extends AnchorPane implements Observer{
	
	private Map<Pane, Label> onSalePaneLabels;
	
	private Model model;
	
	public MarketPane(Model model) {
		this.model = model;
		onSalePaneLabels = new HashMap<>();
		this.setPrefSize(1000.0, 700.0);
		this.setStyle("-fx-background-color: white");
		this.addEventHandler(MouseEvent.MOUSE_EXITED, e -> this.setVisible(false));

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
		
		drawTitle();
		buildSellables();
	}
	
	private void draw() {
		this.getChildren().clear();
		drawTitle();
		buildSellables();
	}
	
	private void drawTitle() {
		DropShadow shadow = new DropShadow();
        shadow.setRadius(2.0);
        shadow.setColor(Color.BLACK);
		Font goudyMedieval = Font.loadFont(getClass().getResource("/fonts/goudy_medieval/Goudy_Mediaeval_DemiBold.ttf").toExternalForm(), 50.0);
		Label title = new Label();
		title.setFont(goudyMedieval);
		title.setText("Market");
		title.setTextFill(Color.RED);
		title.setEffect(shadow);
		Font goudyMedieval2 = Font.loadFont(getClass().getResource("/fonts/goudy_medieval/Goudy_Mediaeval_DemiBold.ttf").toExternalForm(), 25.0);
		Label subTitle = new Label();
		subTitle.setFont(goudyMedieval2);
		subTitle.setText("Objects on sale");
		subTitle.setTextFill(Color.BLACK);
		AnchorPane.setTopAnchor(title, 0.0);
		AnchorPane.setLeftAnchor(title, 20.0);
		AnchorPane.setTopAnchor(subTitle, 70.0);
		AnchorPane.setLeftAnchor(subTitle, 20.0);
		this.getChildren().add(title);
		this.getChildren().add(subTitle);
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
			double offset = 20.0;
			for(Map.Entry<Pane, Label> pl : onSalePaneLabels.entrySet()) {
				offset = offset + Math.max(pl.getKey().getPrefWidth(), pl.getValue().getPrefWidth()) + 25.0;
			}
			AnchorPane.setTopAnchor(sellablePane, 120.0);
			AnchorPane.setLeftAnchor(sellablePane, offset);
			Label label = drawPriceAndOwner(offset, sellablePane.getPrefHeight() + 120.0, sellable);
			this.getChildren().add(sellablePane);
			onSalePaneLabels.put(sellablePane, label);
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
