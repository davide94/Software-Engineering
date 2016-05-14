package it.polimi.ingsw.cg26.actions;

import it.polimi.ingsw.cg26.exceptions.*;
import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.model.player.Player;

import java.util.Collection;

/**
 *
 */
public class Corrupt extends Action {


    protected final Collection<PoliticColor> politicCardsColors;


    public Corrupt(String token, Collection<PoliticColor> politicCardsColors) {
        super(token);
        if (politicCardsColors == null)
            throw new NullPointerException();
        this.politicCardsColors = politicCardsColors;
    }

    /**
     *
     * @param politicCardsColors
     * @return
     */
    protected int necessaryCoins(Collection<PoliticColor> politicCardsColors){
        int multicolorCardsNumber = 0; // = numero di carte colore bonus (arcobaleno)
        int usedCoins = 0;
        switch(politicCardsColors.size()) {
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
        if (!currentPlayer.hasCards(this.politicCardsColors))
            throw new InvalidCardsException();

    }

}
