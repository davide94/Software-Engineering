package it.polimi.ingsw.cg26.server.model.player;

import it.polimi.ingsw.cg26.common.dto.AssistantDTO;
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

    /**
     * Reassigns the assistant to his owner
     * @throws NullPointerException if the owner is not set
     */
    @Override
    public void backToOwner() {
    	this.getOwner().addAssistant(this);	
    }

	@Override
	public AssistantDTO getState() {
		Player owner = this.getOwner();
		String name = "none";
		if(owner != null){
			name = owner.getName();
		}
		return new AssistantDTO(this.getPrice(), name);
	}

	@Override
	public void giveToNewOwner(Player player) {
		player.addAssistant(this);
	}
}