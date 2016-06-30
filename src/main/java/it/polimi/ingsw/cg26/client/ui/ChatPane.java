package it.polimi.ingsw.cg26.client.ui;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.client.view.OutView;
import it.polimi.ingsw.cg26.common.commands.Message;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 *
 */
public class ChatPane extends VBox implements Observer {

    private final Model model;

    private VBox display;

    private VBox preview;

    public ChatPane(double width, double height, Model model, OutView outView, Pane root) {
        this.model = model;
        DropShadow shadow = new DropShadow();
        shadow.setOffsetY(3.0f);
        shadow.setRadius(50.0);
        shadow.setColor(Color.BLACK);

        AnchorPane.setBottomAnchor(this, 50.0);
        AnchorPane.setRightAnchor(this, 50.0);
        setPrefWidth(width);
        setVisible(false);
        setEffect(shadow);
        setStyle("-fx-background-color: azure;-fx-opacity: 0.5;-fx-background-radius: 5px;");
        addEventHandler(MouseEvent.MOUSE_EXITED, e -> setVisible(false));

        setSpacing(10.0);
        setPadding(new Insets(10.0));

        display = new VBox();
        //display.setPrefWidth(width);
        //display.setSpacing(5.0);
        display.setPadding(new Insets(0.0, 5.0, 0.0, 5.0));
        display.maxHeight(50.0);

        ScrollPane scrollPane = new ScrollPane(display) {
            @Override public void requestFocus() {}
        };

        scrollPane.setPrefWidth(width);
        scrollPane.setPrefHeight(200.0);
        getChildren().add(scrollPane);

        display.heightProperty().addListener((observable, oldvalue, newValue) -> {
            scrollPane.setVvalue(1.0);
        });

        TextField input = new TextField();
        //input.setStyle("-fx-background-color: red;-fx-opacity: 0.5;");
        input.setPrefWidth(width);
        getChildren().add(input);
        input.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER) && !input.getText().isEmpty()) {
                outView.writeObject(new Message(model.getLocalPlayer().getName(), input.getText()));
                input.clear();
            }
        });

        preview = new VBox();
        AnchorPane.setBottomAnchor(preview, 50.0);
        AnchorPane.setRightAnchor(preview, 50.0);
        preview.setPrefWidth(width);
        preview.visibleProperty().bind(visibleProperty().not());
        preview.setStyle("-fx-background-color: azure;-fx-opacity: 0.5;-fx-background-radius: 5px;");
        root.getChildren().add(preview);
        preview.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> setVisible(true));
    }

    @Override
    public void update(Observable o, Object arg) {
        if (isVisible()) {
            display.getChildren().clear();
            for (String message : model.getMessages()) {
                Label messageLabel = new Label(message);
                display.getChildren().add(messageLabel);
            }
        } else {
            List<String> messages = model.getRecentMessages();
            preview.getChildren().clear();
            for (String message : messages) {
                Label messageLabel = new Label(message);
                preview.getChildren().add(messageLabel);
            }
        }
    }
}
