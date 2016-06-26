package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

public class ElectKingAsQuickActionCommand implements Command {

	private static final long serialVersionUID = -4060203026315719794L;
	
	/**
	 * The councillor to elect in the king's balcony
	 */
	private final CouncillorDTO councillor;
	
	/**
	 * Construct a command to elect in the king's balcony as quick action
	 * @param councillor the councillor to elect
	 * @throws NullPointerException if the councillor is null
	 */
	public ElectKingAsQuickActionCommand(CouncillorDTO councillor) {
		if(councillor == null)
			throw new NullPointerException();
		this.councillor = councillor;
	}

	/**
	 * Get the councillorDTO to elect
	 * @return the councillor to elect
	 */
	public CouncillorDTO getCouncillor() {
		return councillor;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
