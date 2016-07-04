package it.polimi.ingsw.cg26.client.ui;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.dto.PlayerDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Observable;
import java.util.Observer;

/**
 *
 */
public class PlayersPane extends Pane implements Observer {

    private Model model;

    private VBox vbox;

    public PlayersPane(double width, double height, Model model) {
        this.model = model;
        DropShadow shadow = new DropShadow();
        shadow.setOffsetY(3.0f);
        shadow.setRadius(50.0);
        shadow.setColor(Color.BLACK);
        setEffect(shadow);
        setStyle("-fx-background-color: azure;-fx-opacity: 0.5;-fx-background-radius: 5px;");

        AnchorPane.setTopAnchor(this, 50.0);
        AnchorPane.setLeftAnchor(this, 50.0);
        setVisible(false);
        setPrefSize(width, height);
        setStyle("-fx-background-color: azure");
        addEventHandler(MouseEvent.MOUSE_EXITED, e -> setVisible(false));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(width, height);
        getChildren().add(scrollPane);

        vbox = new VBox();
        vbox.prefWidth(width);
        vbox.setPadding(new Insets(15.0));
        vbox.setSpacing(10.0);
        scrollPane.setContent(vbox);
    }

    private void draw() {
        vbox.getChildren().clear();
        vbox.getChildren().add(new Label("You: "));
        vbox.getChildren().add(buildPlayerPane(model.getLocalPlayer()));
        vbox.getChildren().add(new Label("Other Players: "));
        for (PlayerDTO p: model.getPlayers())
            if (!p.getName().equals(model.getLocalPlayer().getName()))
                vbox.getChildren().add(buildPlayerPane(p));

    }

    /**
     * Builds and returns a pane displaying the Player's state
     * @param player is a PlayerDTO
     * @return the pane
     */
    private Pane buildPlayerPane(PlayerDTO player) {
        VBox playerPane = new VBox();
        playerPane.setPadding(new Insets(15.0));
        GridPane gridPane = new GridPane();
        playerPane.getChildren().add(gridPane);
        gridPane.add(new Label("Name: "), 0, 0);
        gridPane.add(new Label(player.getName()), 1, 0);
        gridPane.add(new Label("State: "), 0, 1);
        gridPane.add(new Label(player.isOnline()? "online" : "offline"), 1, 1);
        gridPane.add(new Label("Victory Points number: "), 0, 2);
        gridPane.add(new Label(Integer.toString(player.getVictoryPoints())), 1, 2);
        gridPane.add(new Label("Coins number: "), 0, 3);
        gridPane.add(new Label(Integer.toString(player.getCoins())), 1, 3);
        gridPane.add(new Label("Position in Nobility Track: "), 0, 4);
        gridPane.add(new Label(Integer.toString(player.getNobilityCell())), 1, 4);
        gridPane.add(new Label("Assistants number: "), 0, 5);
        gridPane.add(new Label(Integer.toString(player.getAssistantsNumber())), 1, 5);
        gridPane.add(new Label("Used BPT number: "), 0, 6);
        gridPane.add(new Label(Integer.toString(player.getDiscardedTiles().size())), 1, 6);
        gridPane.add(new Label("Remaining Main Actions: "), 0, 7);
        gridPane.add(new Label(Integer.toString(player.getRemainingMainActions())), 1, 7);
        gridPane.add(new Label("Remaining Quick Actions: "), 0, 8);
        gridPane.add(new Label(Integer.toString(player.getRemainingQuickActions())), 1, 8);

        if(!player.getCards().isEmpty()) {
            playerPane.getChildren().add(new Label("Politic Cards: "));
            HBox cardsBox = new HBox();
            cardsBox.setSpacing(5.0);
            playerPane.getChildren().add(cardsBox);
            int i = 0;
            for (PoliticCardDTO c : player.getCards()) {
                if (i == 3) {
                    i = 0;
                    cardsBox = new HBox();
                    cardsBox.setSpacing(5.0);
                    playerPane.getChildren().add(cardsBox);
                }
                cardsBox.getChildren().add(new PoliticCardPane(90.0, 160.0, c));
                i++;
            }
        }
        if (!player.getTiles().isEmpty()) {
            playerPane.getChildren().add(new Label("BPT: "));
            HBox tilesBox = new HBox();
            tilesBox.setSpacing(5.0);
            playerPane.getChildren().add(tilesBox);
            int i = 0;
            for (BusinessPermissionTileDTO t : player.getTiles()) {
                if (i == 3) {
                    i = 0;
                    tilesBox = new HBox();
                    tilesBox.setSpacing(5.0);
                    playerPane.getChildren().add(tilesBox);
                }
                tilesBox.getChildren().add(new BPTPane(100.0, 100.0, t));
                i++;
            }
        }
        return playerPane;
    }

    @Override
    public void update(Observable o, Object arg) {
        draw();
    }
}
