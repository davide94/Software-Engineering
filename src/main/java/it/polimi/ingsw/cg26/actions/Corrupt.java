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
    protected int necessaryCoins(Collection<PoliticColor> politicCardsColors, GameBoard gameBoard){
        int i = 0; //i=numero di carte colore bonus (arcobaleno)
        int usedCoins;
        int playerCoins = gameBoard.getCurrentPlayer().getCoinsNumber();
        switch(politicCardsColors.size()) {
            case 1 : if(playerCoins<10+i) {
                throw new NotEnoughMoneyException();
            }
                usedCoins = 10+i;
                break;
            case 2 : if(playerCoins<7+i) {
                throw new NotEnoughMoneyException();
            }
                usedCoins = 7+i;
                break;
            case 3 : if(playerCoins<4+i) {
                throw new NotEnoughMoneyException();
            }
                usedCoins = 4+i;
                break;
            case 4 : if(playerCoins<i) {
                throw new NotEnoughMoneyException();
            }
                usedCoins = i;
                break;
            default : throw new InvalidCardsException();
        }
        return usedCoins;
    }

    /**
     *
     */
    @Override
    public void apply(GameBoard gameBoard) {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        if (!currentPlayer.canPerformMainAction())
            throw new NoRemainingActionsException();
        if (!currentPlayer.hasCards(this.politicCardsColors))
            throw new InvalidCardsException();

    }

}
