package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

import java.util.List;

public class ChoosePlayerBPTCommand implements Command {

	private static final long serialVersionUID = -6646811266003256236L;
	
	/**
	 * List of tiles chosen by the player from which get the bonuses
	 */
	private final List<BusinessPermissionTileDTO> chosenBPT;
	
	/**
	 * Constructs a choose player BPT action 
	 * @param chosenBPT the tiles chosen by the player from which take the bonuses
	 * @throws NullPointerException if the chosenBPT is null
	 */
	public ChoosePlayerBPTCommand(List<BusinessPermissionTileDTO> chosenBPT) {
		if(chosenBPT == null)
			throw new NullPointerException();
		this.chosenBPT = chosenBPT;
	}

	/**
	 * Get the tiles
	 * @return the chosenBPT
	 */
	public List<BusinessPermissionTileDTO> getChosenBPT() {
		return chosenBPT;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);

	}
}
