package it.polimi.ingsw.cg26.server.model.bonus;

import it.polimi.ingsw.cg26.server.model.player.Player;

/**
 * 
 */
public class MainActionBonus extends Bonus {

    /**
     * Create a MainActionBonus
     * @param multilplicity of the bonus
     */
    public MainActionBonus(int multilplicity) {
    	super(multilplicity);
    }

    /**
     * gives to a player the number of extra main action equal to the multiplicity
     * @param player the player to apply the bonus
     */
    @Override
    public void apply(Player player) {
        player.addRemainingMainActions(getMultiplicity());
    }

    @Override
    public String toString() {
        return "MainActionBonus{" +
                "multiplicity=" + super.getMultiplicity() +
                "}";
    }

}