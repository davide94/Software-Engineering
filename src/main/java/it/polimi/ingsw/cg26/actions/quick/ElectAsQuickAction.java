package it.polimi.ingsw.cg26.actions.quick;

import it.polimi.ingsw.cg26.actions.Elect;
import it.polimi.ingsw.cg26.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.model.player.Player;

/**
 *
 */
public class ElectAsQuickAction extends Elect {

	/**
	 * 
	 * @param region
	 * @param councillorColor
	 */
	public ElectAsQuickAction(String region, PoliticColor councillorColor) {
		super(region, councillorColor);
	}

	/**
	 * 
	 */
	@Override
    public void apply(GameBoard gameBoard) {
        
    	Player currentPlayer = gameBoard.getCurrentPlayer();
    	if (!currentPlayer.canPerformQuickAction())
    		throw new NoRemainingActionsException();
    	if(currentPlayer.getAssistantsNumber()<1){
    		throw new NoRemainingAssistantsException();
    	}
    	
    	super.apply(gameBoard);
    	
    	currentPlayer.takeAssistants(1);
    	currentPlayer.performQuickAction();
    }
}
