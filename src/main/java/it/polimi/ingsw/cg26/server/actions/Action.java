package it.polimi.ingsw.cg26.server.actions;

import it.polimi.ingsw.cg26.common.change.BasicChange;
import it.polimi.ingsw.cg26.common.change.Change;
import it.polimi.ingsw.cg26.common.change.PlayersChange;
import it.polimi.ingsw.cg26.common.change.PrivateChange;
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
    public abstract void apply(GameBoard gameBoard);
    
    public abstract void notifyChange(GameBoard gameBoard);
    
    protected void notifyDecoratingPlayersChange(GameBoard gameBoard, Change change){
    	gameBoard.notifyObservers(new PlayersChange(change, gameBoard.getCurrentPlayer().getState()));
    	Change privatePlayerChange = new PlayersChange(new BasicChange(), gameBoard.getCurrentPlayer().getFullState());
    	gameBoard.notifyObservers(new PrivateChange(privatePlayerChange, gameBoard.getCurrentPlayer().getToken()));	
    }

}
