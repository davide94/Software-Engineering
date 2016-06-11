package it.polimi.ingsw.cg26.common.commands.market;

import it.polimi.ingsw.cg26.common.commands.Command;
import it.polimi.ingsw.cg26.common.dto.SellableDTO;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

public class BuyCommand extends Command {

	private static final long serialVersionUID = 923150246513621227L;
	
	private final SellableDTO sellable;
	
	public BuyCommand(SellableDTO sellable) {
		if(sellable == null)
			throw new NullPointerException();
		this.sellable = sellable;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	/**
	 * @return the sellable
	 */
	public SellableDTO getSellable() {
		return sellable;
	}

}
