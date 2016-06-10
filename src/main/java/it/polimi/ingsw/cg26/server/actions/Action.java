package it.polimi.ingsw.cg26.server.actions;

import it.polimi.ingsw.cg26.common.update.PrivateUpdate;
import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.change.Change;
import it.polimi.ingsw.cg26.common.update.change.LocalPlayerChange;
import it.polimi.ingsw.cg26.common.update.change.PlayersChange;
import it.polimi.ingsw.cg26.common.update.request.Request;
import it.polimi.ingsw.cg26.server.exceptions.*;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;

/**
 *
 */
public abstract class Action {

    private final long token;

	/**
	 * Build a simple action
	 */
    public Action(long token) {
        this.token = token;
    }

    public long getToken() {
        return token;
    }

    /**
     * apply the action into the gameBoard specified to the player specified
     * @param gameBoard the gameBoard where the action is applied
     * @throws NullPointerException if the parameter is null
     */
    public abstract void apply(GameBoard gameBoard) throws NoRemainingAssistantsException, NoRemainingActionsException, InvalidCardsException, CouncillorNotFoundException, NotEnoughMoneyException, CityNotFoundException, ExistingEmporiumException, SellableNotFoundException, NoRemainingCardsException, InvalidTileException, InvalidCityException;
    
    public abstract void notifyChange(GameBoard gameBoard);
    
    protected void notifyDecoratingPlayersChange(GameBoard gameBoard, Change change){
    	gameBoard.notifyObservers(new PlayersChange(change, gameBoard.getCurrentPlayer().getState()));
    	Change privatePlayerChange = new LocalPlayerChange(new BasicChange(), gameBoard.getCurrentPlayer().getFullState());
    	gameBoard.notifyObservers(new PrivateUpdate(privatePlayerChange, gameBoard.getCurrentPlayer().getToken()));
    }
    
    public void checkPendingRequest(GameBoard gameBoard){
    	if(gameBoard.getCurrentPlayer().canPerformChooseAction()){
    		Request request = gameBoard.getCurrentPlayer().getPendingRequest();
    		if(request == null)
    			throw new IllegalStateException();
    		gameBoard.notifyObservers(request);
    	}
    }

}
