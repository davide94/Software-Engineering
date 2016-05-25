package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.state.PoliticCardState;
import it.polimi.ingsw.cg26.common.state.RegionState;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

import java.util.List;

/**
 *
 */
public class AcquireCommand extends Command {

    private static final long serialVersionUID = 4122987227120035880L;

    private final RegionState region;

    private final List<PoliticCardState> cards;

    private final int position;

    public AcquireCommand(RegionState region, List<PoliticCardState> cards, int position) {
        this.region = region;
        this.cards = cards;
        this.position = position;
    }

    public RegionState getRegion() {
        return region;
    }

    public List<PoliticCardState> getCards() {
        return cards;
    }

    public int getPosition() {
        return position;
    }

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
