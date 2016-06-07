package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
import it.polimi.ingsw.cg26.common.dto.MarketDTO;

public class MarketChange extends ChangeDecorator {

	private static final long serialVersionUID = 6096169655114721979L;
	
	private MarketDTO marketDTO;

	/**
	 * Construct a change for the market
	 * @param decoratedChange the change to decorate
	 */
	public MarketChange(Change decoratedChange, MarketDTO marketDTO) {
		super(decoratedChange);
		this.marketDTO = marketDTO;
	}
	
	@Override
	public void apply(GameBoardDTO gameBoard){
		super.apply(gameBoard);
		gameBoard.setMarket(marketDTO);
	}

}
