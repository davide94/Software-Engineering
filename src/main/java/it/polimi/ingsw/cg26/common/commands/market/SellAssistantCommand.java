package it.polimi.ingsw.cg26.common.commands.market;

import it.polimi.ingsw.cg26.common.visitor.Visitor;

public class SellAssistantCommand extends SellCommand {

	private static final long serialVersionUID = 3771532427850868629L;
	
	/**
	 * Constructs a command for sell a Assistant
	 * @param price the price to set to the Assistant
	 * @throws IllegalArgumentException if price is less than 1
	 */
	public SellAssistantCommand(int price) {
		super(price);
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);

	}
}
