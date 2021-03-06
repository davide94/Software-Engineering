package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.visitor.Visitor;

/**
 *
 */
public class AdditionalMainActionCommand implements Command {

    private static final long serialVersionUID = -8017972006953762490L;

    /**
     * Construct an additional main action command
     */
    public AdditionalMainActionCommand() {
    	//nothing to do here
    }

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
