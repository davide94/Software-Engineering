package it.polimi.ingsw.cg26.server.actions;

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

    protected final Collection<PoliticColor> politicCardsColors;

    public Corrupt(Collection<PoliticColor> politicCardsColors) {
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
        int multicolorCardsNumber = 0;
        PoliticColor multicolor = new PoliticColor("multicolor");
        for (PoliticColor color: politicCardsColors)
            if (color.equals(multicolor))
                multicolorCardsNumber++;
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
