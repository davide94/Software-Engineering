package it.polimi.ingsw.cg26.common.commands.market;

import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

public class SellBPTCommand extends SellCommand {

	private static final long serialVersionUID = -6227720265530863357L;
	
	/**
	 * The tile to sell
	 */
	private final BusinessPermissionTileDTO tile;
	
	/**
	 * Constructs a command for sell a BPT
	 * @param price the price to set to the BPT
	 * @param tile the BPT to sell
	 * @throws IllegalArgumentException if price is less than 1
	 * @throws NullPointerException if the tile is null
	 */
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
	 * @return the tile to sell
	 */
	public BusinessPermissionTileDTO getTile() {
		return tile;
	}

}
