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

    /**
     * 
     * @param city
     * @param politicCards
     * @throws NullPointerException if one or more arguments are null
     */
    public BuildKing(CityState city, Collection<PoliticCardState> politicCards) {
        super(politicCards);
        if (city == null)
            throw new NullPointerException();
        this.city = city;
    }

    /**
     * @throws NotEnoughMoneyException if the player hasn't got enough money to do the action
     * @throws InvalidCardsException if the cards given by the user don't match the colors of the councillors in the balcony
     * @throws NoRemainingAssistantsException if the player doesn't have enough assistant to perform the action
     */
    @Override
    public void apply(GameBoard gameBoard) {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        super.apply(gameBoard);
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
