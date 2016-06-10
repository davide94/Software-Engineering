package it.polimi.ingsw.cg26.server.model.bonus;

import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.List;

/**
 * 
 */
public abstract class BonusDecorator implements Bonus{

    /**
     * Number of times that the bonus has to be applied
     */
    private final int multiplicity;
    
    /**
     * The bonus to decorate
     */
    private Bonus decoratedBonus;
	
    /**
     * Create the bonus
     * @param multiplicity of the bonus
     * @throws IllegalArgumentException if the multiplicity is less than 1
     * @throws NullPointerException if the decorated bonus is null
     */
    public BonusDecorator(Bonus decoratedBonus, int multiplicity) {
    	if(multiplicity < 1)
    		throw new IllegalArgumentException();
    	if(decoratedBonus == null)
    		throw new NullPointerException();
    	this.multiplicity=multiplicity;
    	this.decoratedBonus = decoratedBonus;
    }

	/**
	 * @return the multiplicity of the bonus
	 */
	public int getMultiplicity() {
		return multiplicity;
	}
	
	@Override
	public List<String> getBonusNames(){
		return decoratedBonus.getBonusNames();
	}
	
    /**
     * @param player the player to apply the bonus
     */
	@Override
    public void apply(Player player) throws NoRemainingCardsException {
		decoratedBonus.apply(player);
	}

    /**
     * Get the state of the bonus 
     * @return Bonus DTO of the bonus
     */
	@Override
    public BonusDTO getState(){
		return decoratedBonus.getState();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) 
			return true;
		if (o == null || getClass() != o.getClass()) 
			return false;

		BonusDecorator bonus = (BonusDecorator) o;

		return multiplicity == bonus.multiplicity;

	}

	@Override
	public int hashCode() {
		return multiplicity;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return decoratedBonus.toString();
	}
	
	
}