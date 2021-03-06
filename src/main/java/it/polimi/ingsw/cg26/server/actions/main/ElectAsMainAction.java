package it.polimi.ingsw.cg26.server.actions.main;

import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.common.update.event.MessageUpdate;
import it.polimi.ingsw.cg26.server.actions.ElectRegion;
import it.polimi.ingsw.cg26.server.exceptions.CouncillorNotFoundException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.server.exceptions.NotYourTurnException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class ElectAsMainAction extends ElectRegion {

	/**
	 * Construct an elect action, done as main action
	 * @param region is the region where the player wants to elect the councillor
	 * @param councillor is the councillor the player wants to elect
	 */
	public ElectAsMainAction(RegionDTO region, CouncillorDTO councillor, long token) {
		super(region, councillor, token);
	}
	
	/**
	 * @param gameBoard is the board
	 * @throws NoRemainingActionsException if the player has no more remaining actions to do
	 */
	@Override
	public void apply(GameBoard gameBoard) throws NoRemainingActionsException, NoRemainingAssistantsException, CouncillorNotFoundException, NotYourTurnException {
		Player currentPlayer = gameBoard.getCurrentPlayer();
		if(currentPlayer.getToken() != this.getToken())
			throw new NotYourTurnException("You can not perform an action now.");
		if (!currentPlayer.canPerformMainAction())
    		throw new NoRemainingActionsException("You can not perform an action now.");
    	super.apply(gameBoard);
    	currentPlayer.addCoins(4);
    	currentPlayer.performMainAction();
    	notifyChange(gameBoard);
		gameBoard.notifyObservers(new MessageUpdate(currentPlayer.getName(), "[Elected a " + councillor.getColor().getColor() + " Councillor in the " + region.getName() + "'s Balcony]"));
	}
}
