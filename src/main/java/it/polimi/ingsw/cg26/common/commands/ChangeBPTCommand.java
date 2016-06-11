package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

/**
 *
 */
public class ChangeBPTCommand implements Command {

    private static final long serialVersionUID = 8425145455367831160L;

    private final RegionDTO region;

    public ChangeBPTCommand(RegionDTO region) {
    	if(region == null)
    		throw new NullPointerException();
        this.region = region;
    }

    public RegionDTO getRegion() {
        return region;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
