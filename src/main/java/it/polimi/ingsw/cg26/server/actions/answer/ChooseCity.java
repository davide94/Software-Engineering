package it.polimi.ingsw.cg26.server.actions.answer;

import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.request.CityBonusRequest;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.CityNotFoundException;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCityException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.List;

public class ChooseCity extends Action {

	private List<CityDTO> chosenCities;
	
	private List<Bonus> bonusesToApply;
	
	public ChooseCity(List<CityDTO> chosenCities, long token) {
		super(token);
		this.chosenCities = chosenCities;
	}

	@Override
	public void apply(GameBoard gameBoard) throws NoRemainingActionsException, CityNotFoundException, InvalidCityException, NoRemainingCardsException {
		Player currentPlayer = gameBoard.getCurrentPlayer();
		if(!currentPlayer.canPerformChooseAction())
			throw new NoRemainingActionsException();
		for(CityDTO c : chosenCities){
			Bonus bonuses = gameBoard.getCity(c).getBonuses();
			List<String> bonusNames = bonuses.getBonusNames();
			if(bonusNames.contains("Nobility"))
				throw new InvalidCityException();
			bonusesToApply.add(bonuses);
		}
		for(Bonus b : bonusesToApply)
			b.apply(currentPlayer);
		currentPlayer.performChooseAction();
		currentPlayer.removePendingRequest(new CityBonusRequest(chosenCities.size()));
	}

	@Override
	public void notifyChange(GameBoard gameBoard) {
		notifyDecoratingPlayersChange(gameBoard, new BasicChange());
	}

}
