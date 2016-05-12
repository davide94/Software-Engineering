package it.polimi.ingsw.cg26.actions.main;

import it.polimi.ingsw.cg26.actions.Corrupt;
import it.polimi.ingsw.cg26.exceptions.InvalidCardsException;
import it.polimi.ingsw.cg26.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.exceptions.NotEnoughMoneyException;
import it.polimi.ingsw.cg26.model.board.City;
import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.model.player.Player;

import java.util.Collection;

/**
 *
 */
public class BuildKing extends Corrupt {

    private final String cityName;

    public BuildKing(String city, Collection<PoliticColor> politicCardsColors) {
        super(politicCardsColors);
        if (city == null)
            throw new NullPointerException();
        this.cityName = city;
    }

    @Override
    public void apply(GameBoard gameBoard) {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        int coins = super.necessaryCoins(politicCardsColors, gameBoard);
        City city = gameBoard.getCity(this.cityName);
        coins += gameBoard.getKing().priceToMove(city);
        if (currentPlayer.getCoinsNumber() < coins)
            throw new NotEnoughMoneyException();
        if (!gameBoard.getKingBalcony().checkPoliticCards(this.politicCardsColors))
            throw new InvalidCardsException();
        int empNumber = city.getEmporiumsNumber();
        if (currentPlayer.getAssistantsNumber() < empNumber)
            throw new NoRemainingAssistantsException();
        city.build(currentPlayer);
        currentPlayer.takeAssistants(empNumber);
        currentPlayer.performMainAction();
    }

}
