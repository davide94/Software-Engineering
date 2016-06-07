package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

/**
 *
 */
public class BuildCommand extends Command {

    private static final long serialVersionUID = -7169935530571666693L;

    private final CityDTO city;
    
    private final BusinessPermissionTileDTO bPTState;

    public BuildCommand(CityDTO city, BusinessPermissionTileDTO bPTState) {
        this.city = city;
        this.bPTState = bPTState; 
    }

    public CityDTO getCity() {
        return city;
    }
    
    public BusinessPermissionTileDTO getTile(){
    	return bPTState;
    }

	@Override
	public void accept(Visitor visitor, long token) {
		visitor.visit(this, token);
	}
}
