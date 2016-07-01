package it.polimi.ingsw.cg26.server.actions;

import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.common.update.change.BalconyChange;
import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.change.Change;
import it.polimi.ingsw.cg26.common.update.change.CouncillorsPoolChange;
import it.polimi.ingsw.cg26.server.exceptions.CouncillorNotFoundException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.server.exceptions.NotYourTurnException;
import it.polimi.ingsw.cg26.server.model.board.Councillor;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;

/**
 *
 */
public abstract class ElectRegion extends Elect {

	/**
	 * The Region where the player wants to elect the councillor
	 */
	protected final RegionDTO region;

    /**
     * Construct an elect action
     * @param region is the region where the player wants to elect the councillor
     * @param councillor is the councillor that the player wants to elect
     * @throws NullPointerException if one or more arguments are null
     */
    public ElectRegion(RegionDTO region, CouncillorDTO councillor, long token) {
		super(councillor, token);
        if (region == null)
            throw new NullPointerException();
        this.region = region;
    }

    /**
     * @throws CouncillorNotFoundException if the councillor selected by the user doesn't exist in the councillor pool 
     */
    @Override
    public void apply(GameBoard gameBoard) throws NoRemainingActionsException, NoRemainingAssistantsException, CouncillorNotFoundException, NotYourTurnException {
		Councillor realCouncillor = getRealCouncillorFromPool(gameBoard.getCouncillorsPool());
    	Councillor droppedCouncillor = gameBoard.getRegion(region).getBalcony().elect(realCouncillor);
		gameBoard.removeCouncillor(realCouncillor);
		gameBoard.addCouncillor(droppedCouncillor);
    }
    
    @Override
	public void notifyChange(GameBoard gameBoard){
		RegionDTO realRegionState = gameBoard.getRegion(this.region).getState();
		Change change = new CouncillorsPoolChange(new BalconyChange(new BasicChange(), realRegionState.getBalcony(), realRegionState), gameBoard.getState().getCouncillorsPool());
		notifyDecoratingPlayersChange(gameBoard, change);
	}
}
