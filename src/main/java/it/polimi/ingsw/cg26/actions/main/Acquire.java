package it.polimi.ingsw.cg26.actions.main;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.model.GameLogic;

import java.util.Collection;

/**
 *
 */
public class Acquire extends Action {

    private final String region;

    private final Collection<String> politicCardsColors;

    private final int position;

    public Acquire(String region, Collection<String> politicCardsColors, int position) {
        if (region == null || politicCardsColors == null)
            throw new NullPointerException();
        this.region = region;
        this.politicCardsColors = politicCardsColors;
        this.position = position;
    }

    @Override
    public void apply(GameLogic gameLogic) {
        gameLogic.acquireBPT(this.politicCardsColors, this.region, this.position);
        //gameLogic.log("Acquired " + (this.position == 0 ? "right" : "left") + " tile in " + this.region + " with " + this.politicCardsColors.size() + " cards");
    }

}
