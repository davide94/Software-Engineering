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
     * @param politicCards
     * @throws NullPointerException
     */
    public Corrupt(Collection<PoliticCardState> politicCards) throws NullPointerException {
        if (politicCards == null)
            throw new NullPointerException();
        this.politicCards = politicCards;
    }

    /**
     *
     * @param cards
     * @return
     */
    protected int necessaryCoins(Collection<PoliticCardState> cards){
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
     *
     */
    @Override
    public void apply(GameBoard gameBoard, Player currentPlayer) {
        if (!currentPlayer.canPerformMainAction())
            throw new NoRemainingActionsException();
        if (!currentPlayer.hasCards(this.politicCards))
            throw new InvalidCardsException();

    }

}
