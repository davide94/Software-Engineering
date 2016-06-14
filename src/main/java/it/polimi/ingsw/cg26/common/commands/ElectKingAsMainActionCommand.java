package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

public class ElectKingAsMainActionCommand implements Command {

	private static final long serialVersionUID = -707093014887120864L;
	
	/**
	 * The councillor to elect in the king's balcony
	 */
	private final CouncillorDTO councillor;
	
	/**
	 * Construct a command to elect in the king's balcony as main action
	 * @param councillor the councillor to elect
	 * @throws NullPointerException if the councillor is null
	 */
	public ElectKingAsMainActionCommand(CouncillorDTO councillor) {
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
