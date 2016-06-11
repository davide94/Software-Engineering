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
    
    private final BusinessPermissionTileDTO tile;

    public BuildCommand(CityDTO city, BusinessPermissionTileDTO tile) {
    	if(city == null || tile == null)
    		throw new NullPointerException();
        this.city = city;
        this.tile = tile; 
    }

    public CityDTO getCity() {
        return city;
    }
    
    public BusinessPermissionTileDTO getTile(){
    	return tile;
    }

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
