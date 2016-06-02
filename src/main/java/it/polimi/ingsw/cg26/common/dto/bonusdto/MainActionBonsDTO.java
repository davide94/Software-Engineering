package it.polimi.ingsw.cg26.common.dto.bonusdto;

public class MainActionBonsDTO extends BonusDTODecorator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2601348078688044719L;

	/**
	 * Decorate a MainActionBonusDTO on a decoratedBonusDTO
	 * @param decoratedBonusDTO the bonus to be decorated
	 * @param multiplicity the multiplicity of the bonus
	 */
	public MainActionBonsDTO(BonusDTO decoratedBonusDTO, int multiplicity) {
		super(decoratedBonusDTO, multiplicity);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "\nMainActionBonus{" + 
				"multiplicity=" + getMultiplicity() + 
				"}";
	}

}
