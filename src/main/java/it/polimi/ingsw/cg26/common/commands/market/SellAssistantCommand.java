package it.polimi.ingsw.cg26.common.commands.market;

import it.polimi.ingsw.cg26.common.visitor.Visitor;

public class SellAssistantCommand extends SellCommand {

	private static final long serialVersionUID = 3771532427850868629L;
	
	public SellAssistantCommand(int price) {
		super(price);
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);

	}
}
