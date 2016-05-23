package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.state.CouncillorState;
import it.polimi.ingsw.cg26.common.state.RegionState;

/**
 *
 */
public class ElectAsQuickActionCommand extends Command {

    private static final long serialVersionUID = 1363842039097368981L;

    private final RegionState region;

    private final CouncillorState councillor;

    public ElectAsQuickActionCommand(RegionState region, CouncillorState councillor) {
        this.region = region;
        this.councillor = councillor;
    }

    public RegionState getRegion() {
        return region;
    }

    public CouncillorState getCouncillor() {
        return councillor;
    }

}
