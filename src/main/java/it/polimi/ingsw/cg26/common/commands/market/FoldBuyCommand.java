package it.polimi.ingsw.cg26.common.commands.market;

import it.polimi.ingsw.cg26.common.commands.Command;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

public class FoldBuyCommand extends Command {

	private static final long serialVersionUID = 8008641550545084239L;

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
