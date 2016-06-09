package it.polimi.ingsw.cg26.server.actions;

import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.change.Change;
import it.polimi.ingsw.cg26.common.update.change.KingBalconyChange;
import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.server.exceptions.CouncillorNotFoundException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.server.model.board.Councillor;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;

public abstract class ElectKing extends Elect {
	
	/**
	 * Construct an elect action in the king's balcony
	 * @param councillor is the councillor that the player wants to elect
	 * @param token
	 */
	public ElectKing(CouncillorDTO councillor, long token) {
		super(councillor, token);
	}

	@Override
	public void apply(GameBoard gameBoard) throws NoRemainingActionsException, NoRemainingAssistantsException, CouncillorNotFoundException {
		Councillor realCouncillor = getRealCouncillorFromPool(gameBoard.getCouncillorsPool());
		Councillor droppedCouncillor = gameBoard.getKingBalcony().elect(realCouncillor);
		gameBoard.getCouncillorsPool().remove(realCouncillor);
		gameBoard.getCouncillorsPool().add(droppedCouncillor);
	}

	@Override
	public void notifyChange(GameBoard gameBoard) {
		Change change = new KingBalconyChange(new BasicChange(), gameBoard.getKingBalcony().getState());
		notifyDecoratingPlayersChange(gameBoard, change);
	}

}
