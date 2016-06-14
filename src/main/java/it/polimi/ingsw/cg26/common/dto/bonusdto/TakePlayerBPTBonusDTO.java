package it.polimi.ingsw.cg26.common.dto.bonusdto;

public class TakePlayerBPTBonusDTO extends BonusDTODecorator {

	private static final long serialVersionUID = -328195322419976384L;

	/**
	 * Construct a TakePlayerBPTBonusDTO to decorate to another bonus
	 * @param decoratedBonusDTO the bonusDTO to decorate
	 * @param multiplicity the multiplicity of the bonus
	 * @throws NullPointerException if the decoratedBonusDTO is null
	 * @throws IllegalArgumentException if the multiplicity is less than 1
	 */
	public TakePlayerBPTBonusDTO(BonusDTO decoratedBonusDTO, int multiplicity) {
		super(decoratedBonusDTO, multiplicity);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (super.toString().isEmpty() ? "" : super.toString() + ", ") + getMultiplicity() + " Take Player BPT Bonus";
	}
}
