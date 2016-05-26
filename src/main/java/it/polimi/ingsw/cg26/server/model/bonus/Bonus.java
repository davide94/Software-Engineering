package it.polimi.ingsw.cg26.server.model.bonus;

import it.polimi.ingsw.cg26.server.model.player.Player;
import it.polimi.ingsw.cg26.common.state.BonusState;

/**
 * 
 */
public abstract class Bonus {

    /**
     * Number of times that the bonus has to be applied
     */
    private final int multiplicity;
	
    /**
     * Create the bonus
     * @param multiplicity of the bonus
     * @throws IllegalArgumentException if the multiplicity is less than 1
     */
    public Bonus(int multiplicity) {
    	if(multiplicity < 1)
    		throw new IllegalArgumentException();
    	this.multiplicity=multiplicity;
    }

	/**
	 * @return the multiplicity of the bonus
	 */
	public int getMultiplicity() {
		return multiplicity;
	}
	
    /**
     * @param player the player to apply the bonus
     */
    public abstract void apply(Player player);

    public abstract BonusState getState();
}