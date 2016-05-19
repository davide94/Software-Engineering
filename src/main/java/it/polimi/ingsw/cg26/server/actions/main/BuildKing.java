package it.polimi.ingsw.cg26.server.actions.main;

import it.polimi.ingsw.cg26.server.actions.Corrupt;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCardsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.server.exceptions.NotEnoughMoneyException;
import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.server.model.player.Player;

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
    public void apply(GameBoard gameBoard, Player currentPlayer) {
        int coins = super.necessaryCoins(politicCardsColors);
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
        currentPlayer.removeCoins(coins);
        currentPlayer.takeCards(super.politicCardsColors);
        currentPlayer.takeAssistants(empNumber);
        currentPlayer.performMainAction();
    }

}
