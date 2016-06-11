package it.polimi.ingsw.cg26.common.commands.market;

import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

public class SellBPTCommand extends SellCommand {

	private static final long serialVersionUID = -6227720265530863357L;
	
	private final BusinessPermissionTileDTO tile;
	
	public SellBPTCommand(int price, BusinessPermissionTileDTO tile) {
		super(price);
		if(tile == null)
			throw new NullPointerException();
		this.tile = tile;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	/**
	 * @return the tile
	 */
	public BusinessPermissionTileDTO getTile() {
		return tile;
	}

}
