package it.polimi.ingsw.cg26.server.actions;

import it.polimi.ingsw.cg26.common.change.BasicChange;
import it.polimi.ingsw.cg26.common.change.Change;
import it.polimi.ingsw.cg26.common.change.KingBalconyChange;
import it.polimi.ingsw.cg26.common.change.PlayersChange;
import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.server.model.board.Councillor;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;

public abstract class ElectKing extends Elect {
	
	public ElectKing(CouncillorDTO councillor, long token) {
		super(councillor, token);
	}

	@Override
	public void apply(GameBoard gameBoard) {
		Councillor realCouncillor = super.getRealCouncillorFromPool(gameBoard);
		Councillor droppedCouncillor = gameBoard.getKingBalcony().elect(realCouncillor);
		gameBoard.getCouncillorsPool().remove(realCouncillor);
		gameBoard.getCouncillorsPool().add(droppedCouncillor);
	}

	@Override
	public void notifyChange(GameBoard gameBoard) {
		Change change = new KingBalconyChange(new BasicChange(), gameBoard.getKingBalcony().getState());
		gameBoard.notifyObservers(new PlayersChange(change, gameBoard.getCurrentPlayer().getState()));

	}

}
