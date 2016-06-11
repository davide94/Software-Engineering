package it.polimi.ingsw.cg26.common.commands.market;

import it.polimi.ingsw.cg26.common.commands.Command;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

public class FoldSellCommand implements Command {

	private static final long serialVersionUID = -8650499982069073839L;

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
