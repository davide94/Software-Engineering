package it.polimi.ingsw.cg26.common.commands;

import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.common.visitor.Visitor;

public class ChooseBPTCommand implements Command {

	private static final long serialVersionUID = -125023821650037451L;

	/**
	 * The region where take the BPT chosen by the player
	 */
	private final RegionDTO chosenRegion;

	/**
	 * The position where take the BPT chosen by the player
	 */
	private final int chosenPosition;
	
	/**
	 * Construct a choose BPT command
	 * @param chosenRegion the region where take the BPT
	 * @param chosenPosition the position where take the BPT
	 * @throws NullPointerException if the chosenRegion is null
	 */
	public ChooseBPTCommand(RegionDTO chosenRegion, int chosenPosition) {
		if(chosenRegion == null)
			throw new NullPointerException();
		this.chosenRegion = chosenRegion;
		this.chosenPosition = chosenPosition;
	}

	/**
	 * Get the region chosen by the player
	 * @return the chosenRegion
	 */
	public RegionDTO getChosenRegion() {
		return chosenRegion;
	}

	/**
	 * Get the position chosen by the player
	 * @return the chosenPosition
	 */
	public int getChosenPosition() {
		return chosenPosition;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
