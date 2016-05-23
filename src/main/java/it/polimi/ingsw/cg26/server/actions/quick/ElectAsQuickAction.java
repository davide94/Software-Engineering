package it.polimi.ingsw.cg26.server.actions.quick;

import it.polimi.ingsw.cg26.common.state.CouncillorState;
import it.polimi.ingsw.cg26.common.state.RegionState;
import it.polimi.ingsw.cg26.server.actions.Elect;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.server.model.player.Player;

/**
 *
 */
public class ElectAsQuickAction extends Elect {

	/**
	 * 
	 * @param region
	 * @param councillor
	 */
	public ElectAsQuickAction(RegionState region, CouncillorState councillor) {
		super(region, councillor);
	}

	/**
	 * 
	 */
	@Override
    public void apply(GameBoard gameBoard, Player currentPlayer) {
		if (!currentPlayer.canPerformQuickAction())
    		throw new NoRemainingActionsException();
    	if(currentPlayer.getAssistantsNumber()<1){
    		throw new NoRemainingAssistantsException();
    	}
    	
    	super.apply(gameBoard, currentPlayer);
    	
    	currentPlayer.takeAssistants(1);
    	currentPlayer.performQuickAction();
    }
}
