package it.polimi.ingsw.cg26.common.change;

import java.util.List;

import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;

public class CouncillorsPoolChange extends ChangeDecorator {

	private static final long serialVersionUID = 2109057430783266087L;

	private  List<CouncillorDTO> councillorsPoolState;
	
	public CouncillorsPoolChange(Change decoratedChange, List<CouncillorDTO> councillorsPoolState) {
		super(decoratedChange);
		this.councillorsPoolState = councillorsPoolState;
	}
	
	@Override
	public void apply(GameBoardDTO gameGameBoardDTO) {
		super.apply(gameGameBoardDTO);
		gameGameBoardDTO.setCouncillorsPool(councillorsPoolState);
	}

}
