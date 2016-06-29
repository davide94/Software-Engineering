package it.polimi.ingsw.cg26.server.actions.quick;

import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.common.update.event.MessageUpdate;
import it.polimi.ingsw.cg26.server.actions.ElectKing;
import it.polimi.ingsw.cg26.server.exceptions.CouncillorNotFoundException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class ElectKingAsQuickAction extends ElectKing {

	/**
	 * Construct an Elect action in the king's balcony as quick action
	 * @param councillor is the councillor the player wants to elect
	 * @param token
	 */
	public ElectKingAsQuickAction(CouncillorDTO councillor, long token) {
		super(councillor, token);
	}

	/**
	 * @throws NoRemainingActionsException if the player has no more remaining actions to do
	 * @throws NoRemainingAssistantsException if the player doesn't have enough assistant to perform the action
	 */
	@Override
    public void apply(GameBoard gameBoard) throws NoRemainingActionsException, NoRemainingAssistantsException, CouncillorNotFoundException {
		Player currentPlayer = gameBoard.getCurrentPlayer();
		if (!currentPlayer.canPerformQuickAction())
    		throw new NoRemainingActionsException();
    	if(currentPlayer.getAssistantsNumber()<1){
    		throw new NoRemainingAssistantsException();
    	}
    	super.apply(gameBoard);
    	currentPlayer.takeAssistants(1);
    	currentPlayer.performQuickAction();
    	notifyChange(gameBoard);
        gameBoard.notifyObservers(new MessageUpdate(currentPlayer.getName(), "[Elected a " + councillor.getColor().getColor() + " Councillor in the King's Balcony as Quick Action]"));
	}
}
