package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.visitor.Visitor;

/**
 *
 */
public class FoldQuickActionCommand implements Command {

    private static final long serialVersionUID = -1745676362472524590L;

    /**
     * Construct a command to fold the quick action
     */
    public FoldQuickActionCommand() {
    	//nothing to do here
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
