package it.polimi.ingsw.cg26.server.actions.main;

import it.polimi.ingsw.cg26.common.state.CityState;
import it.polimi.ingsw.cg26.common.state.PoliticCardState;
import it.polimi.ingsw.cg26.server.actions.Corrupt;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCardsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.server.exceptions.NotEnoughMoneyException;
import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.Collection;

/**
 *
 */
public class BuildKing extends Corrupt {

    private final CityState city;

    public BuildKing(CityState city, Collection<PoliticCardState> politicCards) {
        super(politicCards);
        if (city == null)
            throw new NullPointerException();
        this.city = city;
    }

    @Override
    public void apply(GameBoard gameBoard, Player currentPlayer) {
        super.apply(gameBoard, currentPlayer);
    	int coins = super.necessaryCoins(politicCards);
        City realCity = gameBoard.getCity(city);
        coins += gameBoard.getKing().priceToMove(realCity);
        if (currentPlayer.getCoinsNumber() < coins)
            throw new NotEnoughMoneyException();
        if (!gameBoard.getKingBalcony().checkPoliticCards(politicCards))
            throw new InvalidCardsException();
        int empNumber = realCity.getEmporiumsNumber();
        if (currentPlayer.getAssistantsNumber() < empNumber)
            throw new NoRemainingAssistantsException();
        realCity.build(currentPlayer);
        currentPlayer.removeCoins(coins);
        currentPlayer.takeCards(politicCards);
        currentPlayer.takeAssistants(empNumber);
        currentPlayer.performMainAction();
    }

}
