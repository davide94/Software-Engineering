package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.state.RegionState;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

/**
 *
 */
public class ChangeBPTCommand extends Command {

    private static final long serialVersionUID = 8425145455367831160L;

    private final RegionState region;

    public ChangeBPTCommand(RegionState region) {
        this.region = region;
    }

    public RegionState getRegion() {
        return region;
    }

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
