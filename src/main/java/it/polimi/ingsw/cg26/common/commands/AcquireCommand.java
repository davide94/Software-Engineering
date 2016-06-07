package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

import java.util.List;

/**
 *
 */
public class AcquireCommand extends Command {

    private static final long serialVersionUID = 4122987227120035880L;

    private final RegionDTO region;

    private final List<PoliticCardDTO> cards;

    private final int position;

    public AcquireCommand(RegionDTO region, List<PoliticCardDTO> cards, int position) {
        this.region = region;
        this.cards = cards;
        this.position = position;
    }

    public RegionDTO getRegion() {
        return region;
    }

    public List<PoliticCardDTO> getCards() {
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
