package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.visitor.Visitor;

/**
 *
 */
public class FoldQuickActionCommand extends Command {

    private static final long serialVersionUID = -1745676362472524590L;

    public FoldQuickActionCommand() {
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
