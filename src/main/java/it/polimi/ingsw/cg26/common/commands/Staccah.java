package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.visitor.Visitor;

/**
 *
 */
public class Staccah extends Command {

    private static final long serialVersionUID = 1350174476395660166L;

	@Override
	public void accept(Visitor visitor, long token) {
		visitor.visit(this, token);
	}

}
