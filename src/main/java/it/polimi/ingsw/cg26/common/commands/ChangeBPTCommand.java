package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

/**
 *
 */
public class ChangeBPTCommand implements Command {

    private static final long serialVersionUID = 8425145455367831160L;

    /**
     * The region in which the player wants to change the tiles
     */
    private final RegionDTO region;

    /**
     * Construct a command to change the faced up tiles in a region
     * @param region the region where change the tiles
     * @throws NullPointerException if the region is null
     */
    public ChangeBPTCommand(RegionDTO region) {
    	if(region == null)
    		throw new NullPointerException();
        this.region = region;
    }

    /**
     * Get the regionDTO
     * @return the region where change the tiles
     */
    public RegionDTO getRegion() {
        return region;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
