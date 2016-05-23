package it.polimi.ingsw.cg26.server.actions.quick;

import it.polimi.ingsw.cg26.common.state.RegionState;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;

/**
 *
 */
public class ChangeBPT extends Action {

    private final RegionState region;

    public ChangeBPT(RegionState region) {
        if (region == null)
            throw new NullPointerException();
        this.region = region;
    }

    /**
     * 
     */
    @Override
    public void apply(GameBoard gameBoard) {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        if (!currentPlayer.canPerformQuickAction())
    		throw new NoRemainingActionsException();
    	if(currentPlayer.getAssistantsNumber()<1)
    		throw new NoRemainingAssistantsException();
    	
    	gameBoard.getRegion(region).getBPTDeck().change();
    	
    	currentPlayer.takeAssistants(1);
    	currentPlayer.performQuickAction();
    }

}
