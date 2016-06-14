package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

/**
 *
 */
public class BuildCommand implements Command {

    private static final long serialVersionUID = -7169935530571666693L;

    /**
     * The city in which the player wants to build
     */
    private final CityDTO city;
    
    /**
     * The tile the player wants to use
     */
    private final BusinessPermissionTileDTO tile;

    /**
     * Construct a Command to build an emporium
     * @param city the city to use in the command
     * @param tile the tile to use in the command
     * @throws NullPointerException if one or more arguments are null
     */
    public BuildCommand(CityDTO city, BusinessPermissionTileDTO tile) {
    	if(city == null || tile == null)
    		throw new NullPointerException();
        this.city = city;
        this.tile = tile; 
    }

    /**
     * Get CityDTO
     * @return the city of the command
     */
    public CityDTO getCity() {
        return city;
    }
    
    /**
     * Get TileDTO
     * @return the tile of the command
     */
    public BusinessPermissionTileDTO getTile(){
    	return tile;
    }

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
