package it.polimi.ingsw.cg26.actions.main;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.exceptions.*;
import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.model.cards.CouncillorColor;
import it.polimi.ingsw.cg26.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.model.player.Player;

import java.util.Collection;
import java.util.List;

import org.junit.runner.manipulation.NoTestsRemainException;

/**
 *
 */
public class Acquire extends Action {

    private final String region;

    private final Collection<CouncillorColor> politicCardsColors;

    private final int position;

    public Acquire(String region, Collection<CouncillorColor> politicCardsColors, int position) {
        if (region == null || politicCardsColors == null)
            throw new NullPointerException();
        this.region = region;
        this.politicCardsColors = politicCardsColors;
        this.position = position;
    }
    
    /**
     * 
     * @param politicCardsColors
     * @return
     */
    private int necessaryCoins(Collection<CouncillorColor> politicCardsColors, GameBoard gameBoard){
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
    	if(!gameBoard.getCurrentPlayer().canPerformMainAction()){
    		throw new NoRemainingActionsException();
    	}
    	int usedCoins = this.necessaryCoins(politicCardsColors, gameBoard);
    	Player currentPlayer = gameBoard.getCurrentPlayer();
    	List<PoliticCard> usedCards = currentPlayer.getCards(this.politicCardsColors);
    	if(!gameBoard.getRegion(this.region).getBalcony().checkPoliticCardsCouncillors(usedCards)){
    		throw new InvalidCardsException(); 
    	}
    	BusinessPermissionTile addedBPT = gameBoard.getRegion(this.region).getBPTDeck().draw();
    	currentPlayer.addPermissionTile(addedBPT);
    	currentPlayer.useCards(usedCards);
    	currentPlayer.removeCoins(usedCoins);
    	currentPlayer.performMainAction();
    }

}
