package it.polimi.ingsw.cg26.common.dto.bonusdto;

public class CardBonusDTO extends BonusDTODecorator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3182032895492819680L;

	/**
	 * Decorate a CardBonusDTO on a decoratedBonusDTO
	 * @param decoratedBonusDTO the bonus to be decorated
	 * @param multiplicity the multiplicity of the bonus
	 */
	public CardBonusDTO(BonusDTO decoratedBonusDTO, int multiplicity) {
		super(decoratedBonusDTO, multiplicity);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "\nCardBonus{"+
				"multiplicity=" + getMultiplicity() + 
				"}";
	}
}
