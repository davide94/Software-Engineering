package it.polimi.ingsw.cg26.model.bonus;

import it.polimi.ingsw.cg26.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.model.player.Player;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 
 */
public abstract class Bonus {

    /**
     * Default constructor
     */
    public Bonus(int multiplicity) {
    	this.multiplicity=multiplicity;
    }

    /**
     * number of times that the bonus has to be applied
     */
    private final int multiplicity;

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


}