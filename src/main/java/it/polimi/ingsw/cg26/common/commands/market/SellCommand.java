package it.polimi.ingsw.cg26.common.commands.market;

import it.polimi.ingsw.cg26.common.commands.Command;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

public abstract class SellCommand implements Command {

	private static final long serialVersionUID = 9024298220774620465L;
	
	/**
	 * The price to set to the sellable
	 */
	private final int price;
	
	/**
	 * Construct a simple sell command
	 * @param price the price to set to the object to put on sale
	 * @throws IllegalArgumentException if the price is less than 1
	 */
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
