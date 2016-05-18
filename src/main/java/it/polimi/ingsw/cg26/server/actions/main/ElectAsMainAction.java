package it.polimi.ingsw.cg26.server.actions.main;

import it.polimi.ingsw.cg26.server.actions.Elect;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.server.model.player.Player;

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
