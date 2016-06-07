package it.polimi.ingsw.cg26.server.actions.main;

import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.server.actions.ElectKing;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class ElectKingAsMainAction extends ElectKing {

	/**
	 * Construct an Elect action in the king's balcony as main action
	 * @param councillor is the councillor the player wants to elect
	 * @param token
	 */
	public ElectKingAsMainAction(CouncillorDTO councillor, long token) {
		super(councillor, token);
	}

	/**
	 * @throws NoRemainingActionsException if the player has no more remaining actions to do
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
