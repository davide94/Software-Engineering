package it.polimi.ingsw.cg26.server.actions.main;

import it.polimi.ingsw.cg26.common.state.CouncillorState;
import it.polimi.ingsw.cg26.common.state.RegionState;
import it.polimi.ingsw.cg26.server.actions.Elect;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class ElectAsMainAction extends Elect {

	/**
	 * Construct an elect action, done as main action
	 * @param region is the region where the player wants to elect the councillor
	 * @param councillor is the councillor the player wants to elect 
	 */
	public ElectAsMainAction(RegionState region, CouncillorState councillor) {
		super(region, councillor);
	}
	
	/**
	 * @throws NoRemainingActionsException if the player has no more remaining actions to do
	 */
	@Override
	public void apply(GameBoard gameBoard) {
		Player currentPlayer = gameBoard.getCurrentPlayer();
		if (!currentPlayer.canPerformMainAction())
    		throw new NoRemainingActionsException();
    	super.apply(gameBoard);
    	currentPlayer.addCoins(4);
    	currentPlayer.performMainAction();
	}

}
