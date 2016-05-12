package it.polimi.ingsw.cg26.actions.main;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.exceptions.NotExistingCouncillorException;
import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.cards.Councillor;
import it.polimi.ingsw.cg26.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.model.player.Player;

/**
 *
 */
public class Elect extends Action {

    private final String region;

    private final PoliticColor councillorColor;

    public Elect(String region, PoliticColor councillorColor) {
        if (region == null || councillorColor == null)
            throw new NullPointerException();
        this.region = region;
        this.councillorColor = councillorColor;
    }

    /**
     * 
     */
    @Override
    public void apply(GameBoard gameBoard) {
        
    	Player currentPlayer = gameBoard.getCurrentPlayer();
    	
    	if (!currentPlayer.canPerformMainAction())
    		throw new NoRemainingActionsException();
    	Councillor addCouncillor=null;
    	for(Councillor iterCouncillor : gameBoard.getCouncillorsPool()){
    		if(iterCouncillor.getColor().equals(this.councillorColor)){
    			addCouncillor = iterCouncillor;
    			break;
    		}
    	}
    	if(addCouncillor == null){
    		throw new NotExistingCouncillorException();
    	}
    	Councillor droppedCouncillor;
    	if("king".equalsIgnoreCase(this.region)){
    		droppedCouncillor = gameBoard.getKingBalcony().elect(addCouncillor);
    	} else {
    		droppedCouncillor = gameBoard.getRegion(this.region).getBalcony().elect(addCouncillor);
    	}
    	gameBoard.getCouncillorsPool().remove(addCouncillor);
    	gameBoard.getCouncillorsPool().add(droppedCouncillor);
    	currentPlayer.addCoins(4);
    	currentPlayer.performMainAction();
    }

}
