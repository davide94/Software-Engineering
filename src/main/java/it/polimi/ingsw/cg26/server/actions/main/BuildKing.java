package it.polimi.ingsw.cg26.server.actions.main;

import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.server.actions.Corrupt;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCardsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.server.exceptions.NotEnoughMoneyException;
import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.board.King;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.Collection;

/**
 *
 */
public class BuildKing extends Corrupt {

	/**
	 * The city where the player wants to move the king and to build the emporium
	 */
    private final CityDTO city;

    /**
     * Construct a "build emporium with the help of the king action" action 
     * @param city is the city in which the player wants to build his emporium
     * @param politicCards the cards the player wants to use to corrupt the king's balcony
     * @throws NullPointerException if one or more arguments are null
     */
    public BuildKing(CityDTO city, Collection<PoliticCardDTO> politicCards, long token) {
        super(politicCards, token);
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
        King king = gameBoard.getKing();
        coins += king.priceToMove(realCity);
        if (currentPlayer.getCoinsNumber() < coins)
            throw new NotEnoughMoneyException();
        if (!gameBoard.getKingBalcony().checkPoliticCards(politicCards))
            throw new InvalidCardsException();
        int empNumber = realCity.getEmporiumsNumber();
        if (currentPlayer.getAssistantsNumber() < empNumber)
            throw new NoRemainingAssistantsException();
        realCity.build(currentPlayer);
        king.move(realCity);
        currentPlayer.removeCoins(coins);
        currentPlayer.takeCards(politicCards);
        currentPlayer.takeAssistants(empNumber);
        currentPlayer.performMainAction();
    }

}
