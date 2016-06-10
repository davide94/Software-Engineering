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

import java.util.ArrayList;
import java.util.List;

public class ChooseCity extends Action {

	private final List<CityDTO> chosenCities;
	
	private final List<Bonus> bonusesToApply;
	
	public ChooseCity(List<CityDTO> chosenCities, long token) {
		super(token);
		if(chosenCities == null)
			throw new NullPointerException();
		this.chosenCities = chosenCities;
		this.bonusesToApply = new ArrayList<>();
	}
	
	private void checkList() throws InvalidCityException{
		for(int i = 0; i<chosenCities.size(); i++){
			for(int j = 0; j<chosenCities.size(); j++){
				if(chosenCities.get(i).equals(chosenCities.get(j)) && i!=j)
					throw new InvalidCityException();
			}
		}
	}

	@Override
	public void apply(GameBoard gameBoard) throws NoRemainingActionsException, CityNotFoundException, InvalidCityException, NoRemainingCardsException {
		checkList();
		Player currentPlayer = gameBoard.getCurrentPlayer();
		if(!currentPlayer.canPerformChooseAction())
			throw new NoRemainingActionsException();
		for(CityDTO c : chosenCities){
			Bonus bonuses = gameBoard.getCity(c).getBonuses();
			if(bonuses.getBonusNames().contains("Nobility"))
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
