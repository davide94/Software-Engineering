package it.polimi.ingsw.cg26.common.dto.bonusdto;

public class TakeBPTBonusDTO extends BonusDTODecorator {

	private static final long serialVersionUID = -3711653932980214846L;

	/**
	 * Construct a TakeBPTBonusDTO to decorate to another bonus
	 * @param decoratedBonusDTO the bonusDTO to decorate
	 * @param multiplicity the multiplicity of the bonus
	 * @throws NullPointerException if the decoratedBonusDTO is null
	 * @throws IllegalArgumentException if the multiplicity is less than 1
	 */
	public TakeBPTBonusDTO(BonusDTO decoratedBonusDTO, int multiplicity) {
		super(decoratedBonusDTO, multiplicity);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (super.toString().isEmpty() ? "" : super.toString() + ", ") + getMultiplicity() + " Take BPT";
	}
}
