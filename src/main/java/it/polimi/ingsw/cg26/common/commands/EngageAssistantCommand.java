package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.visitor.Visitor;

/**
 *
 */
public class EngageAssistantCommand extends Command {

    private static final long serialVersionUID = -4571957079293475835L;

    public EngageAssistantCommand() {

    }

	@Override
	public void accept(Visitor visitor, long token) {
		visitor.visit(this, token);
	}

}
