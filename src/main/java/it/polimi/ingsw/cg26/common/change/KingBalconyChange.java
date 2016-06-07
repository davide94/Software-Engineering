package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.dto.BalconyDTO;
import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;

public class KingBalconyChange extends ChangeDecorator {

	private static final long serialVersionUID = -2091081956592374355L;

	private BalconyDTO kingBalcony;
	
	/**
	 * Construct a change of the king's balcony
	 * @param decoratedChange the change to decorate
	 * @param kingBalcony the new state of the balcony
	 */
	public KingBalconyChange(Change decoratedChange, BalconyDTO kingBalcony) {
		super(decoratedChange);
		if(kingBalcony == null)
			throw new NullPointerException();
		this.kingBalcony = kingBalcony;
	}
	
	@Override
	public void apply(GameBoardDTO gameBoardDTO){
		super.apply(gameBoardDTO);
		gameBoardDTO.setKingBalcony(kingBalcony);
	}

}