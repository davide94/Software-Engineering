package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.visitor.Visitor;

/**
 *
 */
public class AdditionalMainActionCommand extends Command {

    private static final long serialVersionUID = -8017972006953762490L;

    public AdditionalMainActionCommand() {

    }

	@Override
	public void accept(Visitor visitor, long token) {
		visitor.visit(this, token);
	}

}
