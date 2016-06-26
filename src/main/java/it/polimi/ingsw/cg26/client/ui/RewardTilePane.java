package it.polimi.ingsw.cg26.client.ui;

import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class RewardTilePane extends AnchorPane {

	public RewardTilePane(double width, double height, BonusDTO bonus) {
		this.setPrefSize(width, height);
		BonusPane bonusPane = new BonusPane(this.getPrefHeight(), bonus);
		AnchorPane.setLeftAnchor(bonusPane, 0.55 * this.getPrefWidth());
		AnchorPane.setBottomAnchor(bonusPane, 0.1 * this.getPrefHeight());
		DropShadow shadow = new DropShadow();
        shadow.setRadius(4.0);
        shadow.setColor(Color.BLACK);
        this.setEffect(shadow);
		this.getChildren().add(bonusPane);
	}
}
