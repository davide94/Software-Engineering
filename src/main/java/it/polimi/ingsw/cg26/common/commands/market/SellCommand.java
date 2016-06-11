package it.polimi.ingsw.cg26.common.commands.market;

import it.polimi.ingsw.cg26.common.commands.Command;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

public abstract class SellCommand extends Command {

	private static final long serialVersionUID = 9024298220774620465L;
	
	private final int price;
	
	public SellCommand(int price) {
		if(price < 1)
			throw new IllegalArgumentException();
		this.price = price;
	}
	
	@Override
	public abstract void accept(Visitor visitor);

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

}
