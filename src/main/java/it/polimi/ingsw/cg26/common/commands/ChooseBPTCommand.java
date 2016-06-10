package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

public class ChooseBPTCommand extends Command {

	private static final long serialVersionUID = -125023821650037451L;

	private final RegionDTO chosenRegion;

	private final int chosenPosition;
	
	public ChooseBPTCommand(RegionDTO chosenRegion, int chosenPosition) {
		if(chosenRegion == null)
			throw new NullPointerException();
		this.chosenRegion = chosenRegion;
		this.chosenPosition = chosenPosition;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	/**
	 * @return the chosenRegion
	 */
	public RegionDTO getChosenRegion() {
		return chosenRegion;
	}

	/**
	 * @return the chosenPosition
	 */
	public int getChosenPosition() {
		return chosenPosition;
	}

}
