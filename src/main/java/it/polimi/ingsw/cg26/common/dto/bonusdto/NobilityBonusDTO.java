package it.polimi.ingsw.cg26.common.dto.bonusdto;

public class NobilityBonusDTO extends BonusDTODecorator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5401299989271524628L;

	/**
	 * Decorate a NobilityBonusDTO on a decoratedBonusDTO
	 * @param decoratedBonusDTO the bonus to be decorated
	 * @param multiplicity the multiplicity of the bonus
	 */
	public NobilityBonusDTO(BonusDTO decoratedBonusDTO, int multiplicity) {
		super(decoratedBonusDTO, multiplicity);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (super.toString().isEmpty() ? "" : super.toString() + ", ") + getMultiplicity() + " Nobility";
	}
}
