package it.polimi.ingsw.cg26.client.ui;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import javafx.geometry.Point2D;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.*;

/**
 *
 */
public class BPTDeckPane extends HBox implements Observer {

    private Model model;

    private int index;

    private static final List<String> bptUrls = Arrays.asList("BPTcoast.png", "BPThills.png", "BPTmountains.png");

    public BPTDeckPane(Point2D origin, Double width, double height, Model model, int index) {
        this.model = model;
        this.index = index;
        AnchorPane.setLeftAnchor(this, origin.getX());
        AnchorPane.setTopAnchor(this, origin.getY());
        setPrefSize(width, height);
        setSpacing(width * 0.05);
        draw();
    }

    private void draw() {
        double cardW = getPrefWidth() * 0.3;
        //double offset = getPrefWidth() * 0.05;

        getChildren().clear();

        Pane coveredBPT = new Pane();
        getChildren().add(coveredBPT);
        DropShadow shadow = new DropShadow();
        shadow.setRadius(4.0);
        shadow.setColor(Color.BLACK);
        coveredBPT.setEffect(shadow);
        coveredBPT.setPrefSize(cardW, getPrefHeight());
        coveredBPT.setMaxSize(cardW, getPrefHeight());
        coveredBPT.setStyle("-fx-background-image: url(" + getClass().getResource("/img/coveredBPT/" + bptUrls.get(index)) + ");" +
                "-fx-background-position: center;" +
                "-fx-background-size: 100% 100%;");
        int i = 0;
        List<Pane> panes = new LinkedList<>();
        for (BusinessPermissionTileDTO t: model.getRegions().get(index).getDeck().getOpenCards()) {
            panes.add(new BPTPane(cardW, getPrefHeight(), model.getRegions().get(index).getDeck().getOpenCards().get(i)));
            i++;
        }
        Collections.reverse(panes);
        getChildren().addAll(panes);
    }

    @Override
    public void update(Observable o, Object arg) {
        draw();
    }
}
