package it.polimi.ingsw.cg26.client.ui.actions;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PriceDialog extends Dialog<Integer> {

	public PriceDialog() {
		VBox contentView = new VBox();
        getDialogPane().setContent(contentView);

        contentView.setSpacing(40.0);

        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(buttonTypeOk);

        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().add(buttonTypeCancel);
		
        HBox priceBox = new HBox();
		Label label = new Label("Price:");
		TextField priceText = new TextField();
		//priceText.setPrefSize(10, 5);
		priceBox.getChildren().addAll(label, priceText);
		priceBox.setSpacing(10);
		contentView.getChildren().add(priceBox);
		
		setResultConverter(b -> { 
			Integer price = Integer.parseInt(priceText.getText());
			if(b == buttonTypeOk)
				return price;
			return null;
		});
	}
}
