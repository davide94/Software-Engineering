package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.state.RegionState;

/**
 *
 */
public class ChangeBPTCommand extends Command {

    private static final long serialVersionUID = 8425145455367831160L;

    private final RegionState region;

    public ChangeBPTCommand(RegionState region) {
        this.region = region;
    }
}
