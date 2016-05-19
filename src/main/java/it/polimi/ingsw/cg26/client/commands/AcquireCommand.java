package it.polimi.ingsw.cg26.client.commands;

import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.actions.main.Acquire;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;

import java.util.Collection;

/**
 *
 */
public class AcquireCommand extends Command {

    private static final long serialVersionUID = -5946799721284803769L;

    private final String region;

    private final Collection<PoliticColor> politicCardsColors;

    private final int position;

    public AcquireCommand(String region, Collection<PoliticColor> politicCardsColors, int position) {
        if (region == null || politicCardsColors == null)
            throw new NullPointerException();
        this.politicCardsColors = politicCardsColors;
        this.region = region;
        this.position = position;
    }

    @Override
    public Action generateAction() {
        return new Acquire(this.region, this.politicCardsColors, this.position);
    }
}
