package it.polimi.ingsw.cg26.client.ui;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.dto.BalconyDTO;
import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 *
 */
public class BalconyPane extends HBox implements Observer {

    private final Model model;

    private final int index;

    public BalconyPane(Point2D origin, double width, double height, Model model, int index) {
        this.model = model;
        this.index = index;
        AnchorPane.setLeftAnchor(this, origin.getX());
        AnchorPane.setTopAnchor(this, origin.getY());
        setPrefSize(width, height);
        setMaxSize(width, height);

        draw();
    }

    private void draw() {
        getChildren().clear();

        BalconyDTO balcony = model.getKingBalcony();
        if (index < model.getRegions().size())
            balcony = model.getRegions().get(index).getBalcony();

        List<CouncillorDTO> councillors = balcony.getCouncillors();
        Collections.reverse(councillors);
        for(CouncillorDTO c : councillors) {
            getChildren().add(new CouncillorPane(getPrefWidth()/4, getPrefHeight(), c));
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        draw();
    }
}
