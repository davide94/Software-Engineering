package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.state.CouncillorState;
import it.polimi.ingsw.cg26.common.state.RegionState;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

/**
 *
 */
public class ElectAsMainActionCommand extends Command {

    private static final long serialVersionUID = -8327299437343230527L;

    private final RegionState region;

    private final CouncillorState councillor;

    public ElectAsMainActionCommand(RegionState region, CouncillorState councillor) {
        this.region = region;
        this.councillor = councillor;
    }

    public RegionState getRegion() {
        return region;
    }

    public CouncillorState getCouncillor() {
        return councillor;
    }

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
