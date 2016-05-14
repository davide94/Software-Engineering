package it.polimi.ingsw.cg26.actions;

import it.polimi.ingsw.cg26.exceptions.NotExistingCouncillorException;
import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.board.Councillor;
import it.polimi.ingsw.cg26.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.model.player.Player;

/**
 *
 */
public class Elect extends Action {

    private final String region;

    private final PoliticColor councillorColor;

    /**
     * 
     * @param region
     * @param councillorColor
     */
    public Elect(String token, String region, PoliticColor councillorColor) {
		super(token);
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
