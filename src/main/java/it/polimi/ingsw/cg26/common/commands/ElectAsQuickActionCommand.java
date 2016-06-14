package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

/**
 *
 */
public class ElectAsQuickActionCommand implements Command {

    private static final long serialVersionUID = 1363842039097368981L;

    /**
     * The region in which the player wants to elect the councillor
     */
    private final RegionDTO region;

    /**
     * The councillor the player wants to elect
     */
    private final CouncillorDTO councillor;

    /**
     * Constructs a command to elect a councillor as quick action
     * @param region the region in which elect the councillor
     * @param councillor the councillor to elect
     * @throws NullPointerException if one or more arguments are null
     */
    public ElectAsQuickActionCommand(RegionDTO region, CouncillorDTO councillor) {
    	if(region == null || councillor == null)
    		throw new NullPointerException();
        this.region = region;
        this.councillor = councillor;
    }

    /**
     * Get the regionDTO in which elect
     * @return the region in which the player wants to elect
     */
    public RegionDTO getRegion() {
        return region;
    }

    /**
     * Get the councillor to elect
     * @return the councillor to elect
     */
    public CouncillorDTO getCouncillor() {
        return councillor;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
