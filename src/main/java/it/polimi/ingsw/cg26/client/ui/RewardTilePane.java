package it.polimi.ingsw.cg26.client.ui;

import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import javafx.scene.layout.AnchorPane;

public class RewardTilePane extends AnchorPane {

	public RewardTilePane(double width, double height, BonusDTO bonus) {
		this.setPrefSize(width, height);
		this.setRotate(45);
		BonusPane bonusPane = new BonusPane(this.getPrefHeight(), bonus);
		AnchorPane.setLeftAnchor(bonusPane, 0.55 * this.getPrefWidth());
		AnchorPane.setBottomAnchor(bonusPane, 0.1 * this.getPrefHeight());
		this.getChildren().add(bonusPane);
	}
}
