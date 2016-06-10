package it.polimi.ingsw.cg26.server.actions.answer;

import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.common.update.change.BPTDeckChange;
import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.change.Change;
import it.polimi.ingsw.cg26.common.update.request.BPTRequest;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.InvalidTileException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.board.Region;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class ChooseBPT extends Action {

	private final RegionDTO chosenRegion;
	
	private final int chosenPosition;
	
	public ChooseBPT(RegionDTO chosenRegion, int chosenPosition, long token) {
		super(token);
		if(chosenRegion == null)
			throw new NullPointerException();
		this.chosenRegion = chosenRegion;
		this.chosenPosition = chosenPosition;
	}

	@Override
	public void apply(GameBoard gameBoard) throws NoRemainingActionsException, NoRemainingCardsException, InvalidTileException {
		Player currentPlayer = gameBoard.getCurrentPlayer();
		if(!currentPlayer.canPerformChooseAction())
			throw new NoRemainingActionsException();
		BusinessPermissionTile addedBPT = gameBoard.getRegion(this.chosenRegion).getBPTDeck().draw(this.chosenPosition);
		currentPlayer.addPermissionTile(addedBPT);
		addedBPT.getReward(currentPlayer);
		currentPlayer.performChooseAction();
		currentPlayer.removePendingRequest(new BPTRequest());
	}

	@Override
	public void notifyChange(GameBoard gameBoard) {
		Region realRegion = gameBoard.getRegion(this.chosenRegion);
    	Change bPTChange = new BPTDeckChange(new BasicChange(), realRegion.getBPTDeck().getState(), realRegion.getState());
    	notifyDecoratingPlayersChange(gameBoard, bPTChange);
	}

}