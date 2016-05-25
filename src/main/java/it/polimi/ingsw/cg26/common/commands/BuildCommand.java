package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.state.BusinessPermissionTileState;
import it.polimi.ingsw.cg26.common.state.CityState;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

/**
 *
 */
public class BuildCommand extends Command {

    private static final long serialVersionUID = -7169935530571666693L;

    private final CityState city;
    
    private final BusinessPermissionTileState bPTState;

    public BuildCommand(CityState city, BusinessPermissionTileState bPTState) {
        this.city = city;
        this.bPTState = bPTState; 
    }

    public CityState getCity() {
        return city;
    }
    
    public BusinessPermissionTileState getTile(){
    	return bPTState;
    }

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
