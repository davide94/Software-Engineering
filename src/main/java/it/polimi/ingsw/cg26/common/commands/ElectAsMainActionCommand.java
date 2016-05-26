package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

/**
 *
 */
public class ElectAsMainActionCommand extends Command {

    private static final long serialVersionUID = -8327299437343230527L;

    private final RegionDTO region;

    private final CouncillorDTO councillor;

    public ElectAsMainActionCommand(RegionDTO region, CouncillorDTO councillor) {
        this.region = region;
        this.councillor = councillor;
    }

    public RegionDTO getRegion() {
        return region;
    }

    public CouncillorDTO getCouncillor() {
        return councillor;
    }

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
