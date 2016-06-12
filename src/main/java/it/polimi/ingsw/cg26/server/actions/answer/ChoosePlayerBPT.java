package it.polimi.ingsw.cg26.server.actions.answer;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.request.PlayerBPTRequest;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCardsException;
import it.polimi.ingsw.cg26.server.exceptions.InvalidTileException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class ChoosePlayerBPT extends Action {

	private final List<BusinessPermissionTileDTO> chosenBPT;
	
	private final List<Bonus> bonusesToApply;
	
	public ChoosePlayerBPT(List<BusinessPermissionTileDTO> chosenBPT, long token) {
		super(token);
		if(chosenBPT == null)
			throw new NullPointerException();
		this.chosenBPT = chosenBPT;
		this.bonusesToApply = new ArrayList<>();
	}
	
	private void checkList() throws InvalidTileException{
		for(int i = 0; i<chosenBPT.size(); i++){
			for(int j = 0; j<chosenBPT.size(); j++){
				if(chosenBPT.get(i).equals(chosenBPT.get(j)) && i!=j)
					throw new InvalidTileException(); 
			}
		}
	}

	@Override
	public void apply(GameBoard gameBoard) throws NoRemainingActionsException, InvalidTileException, InvalidCardsException, NoRemainingCardsException {
		checkList();
		Player currentPlayer = gameBoard.getCurrentPlayer();
		if(!currentPlayer.canPerformChooseAction())
			throw new NoRemainingActionsException();
		for(BusinessPermissionTileDTO tile : chosenBPT)
			bonusesToApply.add(currentPlayer.hasPermissionTileAlsoFaceDown(tile).getBonuses());
		for(Bonus b : bonusesToApply)
			b.apply(currentPlayer);
		currentPlayer.performChooseAction();
		currentPlayer.removePendingRequest(new PlayerBPTRequest(this.chosenBPT.size()));
		notifyChange(gameBoard);
	}

	@Override
	public void notifyChange(GameBoard gameBoard) {
		notifyDecoratingPlayersChange(gameBoard, new BasicChange());
	}

}
