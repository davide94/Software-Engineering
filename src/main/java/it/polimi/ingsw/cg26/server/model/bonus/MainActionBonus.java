package it.polimi.ingsw.cg26.server.model.bonus;

import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.MainActionBonsDTO;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.player.Player;

/**
 * 
 */
public class MainActionBonus extends BonusDecorator {

    /**
     * Create a MainActionBonus
     * @param decoratedBonus the bonus to be decorated
     * @param multilplicity of the bonus
     * @throws IllegalArgumentException if the multiplicity is less than 1
     */
    public MainActionBonus(Bonus decoratedBonus, int multilplicity) {
    	super(decoratedBonus, multilplicity);
    }

    /**
     * gives to a player the number of extra main action equal to the multiplicity
     * @param player the player to apply the bonus
     */
    @Override
    public void apply(Player player) throws NoRemainingCardsException {
    	super.apply(player);
        player.addRemainingMainActions(getMultiplicity());
    }

    @Override
    public BonusDTO getState() {
        return new MainActionBonsDTO(super.getState(), getMultiplicity());
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString()+"\nMainActionBonus{" +
                "multiplicity=" + super.getMultiplicity() +
                "}";
    }

}