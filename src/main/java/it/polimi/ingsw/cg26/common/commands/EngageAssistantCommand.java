package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.visitor.Visitor;

/**
 *
 */
public class EngageAssistantCommand implements Command {

    private static final long serialVersionUID = -4571957079293475835L;

    /**
     * Constructs a command to engage a new assistant
     */
    public EngageAssistantCommand() {
    	//nothing to do here
    }

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
