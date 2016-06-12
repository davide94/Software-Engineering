package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

import java.util.List;

public class ChoosePlayerBPTCommand implements Command {

	private static final long serialVersionUID = -6646811266003256236L;
	
	private final List<BusinessPermissionTileDTO> chosenBPT;
	
	public ChoosePlayerBPTCommand(List<BusinessPermissionTileDTO> chosenBPT) {
		if(chosenBPT == null)
			throw new NullPointerException();
		this.chosenBPT = chosenBPT;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);

	}

	/**
	 * @return the chosenBPT
	 */
	public List<BusinessPermissionTileDTO> getChosenBPT() {
		return chosenBPT;
	}

}
