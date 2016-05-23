package it.polimi.ingsw.cg26.server.actions;

import it.polimi.ingsw.cg26.common.state.PoliticCardState;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCardsException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.server.model.player.Player;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;

import java.util.Collection;

/**
 *
 */
public abstract class Corrupt extends Action {

    protected final Collection<PoliticCardState> politicCards;

    /**
     * 
     * @param politicCards collection of PoliticCardState, the cards used to do the action
     * @throws NullPointerException if the argument is null
     */
    public Corrupt(Collection<PoliticCardState> politicCards) {
        if (politicCards == null)
            throw new NullPointerException();
        this.politicCards = politicCards;
    }

    /**
     *
     * @param cards to be used in the action
     * @return number of coins the player must have in order to do the action
     * @throws NullPointerException if the argument is null
     */
    protected int necessaryCoins(Collection<PoliticCardState> cards){
    	if(cards == null)
    		throw new NullPointerException();
        int multicolorCardsNumber = 0;
        for (PoliticCardState card: cards)
            if (card.getColor().equals(new PoliticColor("multicolor").getState()))
                multicolorCardsNumber++;
        int usedCoins = 0;
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
                break;
        }
        return usedCoins + multicolorCardsNumber;
    }

    /**
     * @throws NoRemainingActionsException if the player has no more remaining actions to do
     * @throws InvalidCardsException if the cards given by the user doesn't match with the real cards of the player
     */
    @Override
    public void apply(GameBoard gameBoard, Player currentPlayer) {
        if (!currentPlayer.canPerformMainAction())
            throw new NoRemainingActionsException();
        if (!currentPlayer.hasCards(this.politicCards))
            throw new InvalidCardsException();

    }

}
