package it.polimi.ingsw.cg26.client.ui.actions;

import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 */
public class AcquirePane extends AnchorPane {


    public AcquirePane(Point2D origin, double width, double height, Pane root) {
        AnchorPane.setTopAnchor(this, origin.getY());
        AnchorPane.setLeftAnchor(this, origin.getX());
        setPrefSize(width, height);

        DropShadow shadow = new DropShadow();
        shadow.setRadius(100.0);
        shadow.setColor(Color.BLACK);
        setEffect(shadow);

        setStyle("-fx-background-color: azure;-fx-opacity: 1.0;-fx-background-radius: 5px;");

        ScrollPane scrollPane = new ScrollPane();
        getChildren().add(scrollPane);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefSize(width, height);
        scrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.getDeltaX() != 0) event.consume();
        });

        AnchorPane contentPane = new AnchorPane();
        contentPane.setPrefWidth(3.0 * width);
        scrollPane.setContent(contentPane);

        Pane regionsPane = new Pane();
        contentPane.getChildren().add(regionsPane);
        regionsPane.setPrefSize(height * 0.75, height * 0.75);
        AnchorPane.setLeftAnchor(regionsPane, (width - height * 0.75) * 0.5);
        AnchorPane.setTopAnchor(regionsPane, height * 0.125);
        regionsPane.setStyle("-fx-background-color: red;");


        Pane corruptPane = new Pane();
        contentPane.getChildren().add(corruptPane);
        corruptPane.setPrefSize(height * 0.75, height * 0.75);
        AnchorPane.setLeftAnchor(corruptPane, (width - height * 0.75) * 0.5 + width);
        AnchorPane.setTopAnchor(corruptPane, height * 0.125);
        corruptPane.setStyle("-fx-background-color: red;");


        Button nextButton = new Button("next");
        AnchorPane.setBottomAnchor(nextButton, 20.0);
        AnchorPane.setRightAnchor(nextButton, 20.0);
        getChildren().add(nextButton);
        nextButton.setOnMouseClicked(me -> scrollPane.setHvalue(width));

        Button cancelButton = new Button("cancel");
        AnchorPane.setBottomAnchor(cancelButton, 20.0);
        AnchorPane.setRightAnchor(cancelButton, 80.0);
        getChildren().add(cancelButton);
        cancelButton.setOnMouseClicked(me -> root.getChildren().remove(this));
    }
}
