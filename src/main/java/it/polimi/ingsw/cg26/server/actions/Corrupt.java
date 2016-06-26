package it.polimi.ingsw.cg26.server.actions;

import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.server.exceptions.*;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.Collection;

/**
 *
 */
public abstract class Corrupt extends Action {

	/**
	 * The Politic Cards that the player wants to use to corrupt the balcony
	 */
    protected final Collection<PoliticCardDTO> politicCards;

    /**
     * Create a corrupt action
     * @param politicCards collection of PoliticCardDTO, the cards used to do the action
     * @throws NullPointerException if the argument is null
     */
    public Corrupt(Collection<PoliticCardDTO> politicCards, long token) {
        super(token);
        if (politicCards == null)
            throw new NullPointerException();
        this.politicCards = politicCards;
    }

    /**
     * Gives the number of coins necessary to do the action
     * @param cards to be used in the action
     * @return number of coins the player must have in order to do the action
     * @throws NullPointerException if the argument is null
     */
    protected int necessaryCoins(Collection<PoliticCardDTO> cards) throws InvalidCardsException {
        int multicolorCardsNumber = 0;
        for (PoliticCardDTO card: cards)
            if (card.getColor().equals(new PoliticColor("multicolor").getState()))
                multicolorCardsNumber++;
        int usedCoins;
        switch(cards.size()) {
            case 1 :
                usedCoins = 10;
                break;
            case 2 :
                usedCoins = 7;
                break;
            case 3 :
                usedCoins = 4;
                break;
            case 4 :
                usedCoins = 0;
                break;
            default :
                throw new InvalidCardsException();
        }
        return usedCoins + multicolorCardsNumber;
    }

    /**
     * @throws NoRemainingActionsException if the player has no more remaining actions to do
     * @throws InvalidCardsException if the cards given by the user doesn't match with the real cards of the player
     */
    @Override
    public void apply(GameBoard gameBoard) throws NotEnoughMoneyException, InvalidCardsException, NoRemainingActionsException, CityNotFoundException, NoRemainingAssistantsException, ExistingEmporiumException, NoRemainingCardsException {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        if (!currentPlayer.canPerformMainAction())
            throw new NoRemainingActionsException();
        if (!currentPlayer.hasCards(this.politicCards))
            throw new InvalidCardsException();
    }
}
