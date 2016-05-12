package it.polimi.ingsw.cg26.actions.main;

import it.polimi.ingsw.cg26.actions.Elect;
import it.polimi.ingsw.cg26.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.model.player.Player;

public class ElectAsMainAction extends Elect {

	/**
	 * 
	 * @param region
	 * @param councillorColor
	 */
	public ElectAsMainAction(String region, PoliticColor councillorColor) {
		super(region, councillorColor);
	}
	
	/**
	 * 
	 */
	@Override
	public void apply(GameBoard gameBoard){
		
		Player currentPlayer = gameBoard.getCurrentPlayer();
    	if (!currentPlayer.canPerformMainAction())
    		throw new NoRemainingActionsException();
		
    	super.apply(gameBoard);
    	
    	currentPlayer.addCoins(4);
    	currentPlayer.performMainAction();
	}

}
