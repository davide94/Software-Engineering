package it.polimi.ingsw.cg26.server.model.bonus;

import it.polimi.ingsw.cg26.server.model.player.Player;
import it.polimi.ingsw.cg26.server.model.state.BonusState;

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
     */
    public Bonus(int multiplicity) {
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

    public BonusState getState() {
        // TODO make this abstract and re-implement in each subclass
        return new BonusState("Bonus");
    }
}