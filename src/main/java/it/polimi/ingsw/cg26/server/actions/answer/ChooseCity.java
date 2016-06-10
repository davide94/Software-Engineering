package it.polimi.ingsw.cg26.server.actions.answer;

import java.util.List;

import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.CityNotFoundException;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCityException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.player.Player;

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
			City realCity = gameBoard.getCity(c);
			Bonus bonuses = realCity.getBonuses();
			if(bonuses.getBonusNames().contains("Nobility") || bonuses.getBonusNames().contains("TakeYourCity"))
				throw new InvalidCityException();
			bonusesToApply.add(bonuses);
		}
		for(Bonus b : bonusesToApply)
			b.apply(currentPlayer);
		currentPlayer.performChooseAction();
		currentPlayer.setPendingRequest(null);
	}

	@Override
	public void notifyChange(GameBoard gameBoard) {
		notifyDecoratingPlayersChange(gameBoard, new BasicChange());
	}

}
