package it.polimi.ingsw.cg26.server.actions;

import it.polimi.ingsw.cg26.server.exceptions.NotExistingCouncillorException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.board.Councillor;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.io.Serializable;

/**
 *
 */
public abstract class Elect extends Action {

	private static final long serialVersionUID = -5720689551302863319L;

	private final String region;

    private final PoliticColor councillorColor;

    /**
     * 
     * @param region
     * @param councillorColor
     */
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
    public void apply(GameBoard gameBoard, Player currentPlayer) {
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
    }

}
