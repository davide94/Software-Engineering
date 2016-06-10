package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.visitor.Visitor;

/**
 *
 */
public class StaccahCommand extends Command {

    private static final long serialVersionUID = 1350174476395660166L;

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
