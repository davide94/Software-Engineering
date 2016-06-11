package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

public class ElectKingAsQuickActionCommand extends Command {

	private static final long serialVersionUID = -4060203026315719794L;
	
	private final CouncillorDTO councillor;
	
	public ElectKingAsQuickActionCommand(CouncillorDTO councillor) {
		if(councillor == null)
			throw new NullPointerException();
		this.councillor = councillor;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	/**
	 * @return the councillor
	 */
	public CouncillorDTO getCouncillor() {
		return councillor;
	}

}
