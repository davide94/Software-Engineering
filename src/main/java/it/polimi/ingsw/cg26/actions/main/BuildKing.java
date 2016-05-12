package it.polimi.ingsw.cg26.actions.main;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.model.board.GameBoard;

import java.util.Collection;

/**
 *
 */
public class BuildKing extends Action {

    private final String city;

    private final Collection<String> politicCardsColors;

    public BuildKing(String city, Collection<String> politicCardsColors) {
        if (city == null || politicCardsColors == null)
            throw new NullPointerException();
        this.city = city;
        this.politicCardsColors = politicCardsColors;
    }

    @Override
    public void apply(GameBoard gameBoard) {
        // TODO
    }

}
