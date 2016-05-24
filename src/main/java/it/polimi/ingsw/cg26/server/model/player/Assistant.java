package it.polimi.ingsw.cg26.server.model.player;

import it.polimi.ingsw.cg26.common.state.SellableState;
import it.polimi.ingsw.cg26.server.model.market.Sellable;

/**
 * The Assistant class models a single assistant
 */
public class Assistant extends Sellable {

    /**
     * Constructs an Assistant
     */
    public Assistant() {
        // Nothing to do here
    }
    
    @Override
    public void backToOwner() {
    	this.getOwner().addAssistant(this);	
    }

	@Override
	public SellableState getState() {
		return null;
	}

}