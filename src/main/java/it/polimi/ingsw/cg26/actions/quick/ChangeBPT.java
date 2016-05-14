package it.polimi.ingsw.cg26.actions.quick;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.player.Player;

/**
 *
 */
public class ChangeBPT extends Action {

    private final String region;

    public ChangeBPT(String token, String region) {
        super(token);
        if (region == null)
            throw new NullPointerException();
        this.region = region;
    }

    /**
     * 
     */
    @Override
    public void apply(GameBoard gameBoard, Player currentPlayer) {
        if (!currentPlayer.canPerformQuickAction())
    		throw new NoRemainingActionsException();
    	if(currentPlayer.getAssistantsNumber()<1)
    		throw new NoRemainingAssistantsException();
    	
    	gameBoard.getRegion(region).getBPTDeck().change();
    	
    	currentPlayer.takeAssistants(1);
    	currentPlayer.performQuickAction();
    }

}
