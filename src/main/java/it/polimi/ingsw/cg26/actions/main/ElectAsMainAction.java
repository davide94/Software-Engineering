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
	public ElectAsMainAction(String token, String region, PoliticColor councillorColor) {
		super(token, region, councillorColor);
	}
	
	/**
	 * 
	 */
	@Override
	public void apply(GameBoard gameBoard, Player currentPlayer){
		if (!currentPlayer.canPerformMainAction())
    		throw new NoRemainingActionsException();
    	super.apply(gameBoard, currentPlayer);
    	currentPlayer.addCoins(4);
    	currentPlayer.performMainAction();
	}

}
