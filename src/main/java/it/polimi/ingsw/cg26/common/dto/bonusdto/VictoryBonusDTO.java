package it.polimi.ingsw.cg26.common.dto.bonusdto;

public class VictoryBonusDTO extends BonusDTODecorator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3058751773884723298L;

	/**
	 * Decorate a VictoryBonusDTO on a decoratedBonusDTO
	 * @param decoratedBonusDTO the bonus to be decorated
	 * @param multiplicity the multiplicity of the bonus
	 */
	public VictoryBonusDTO(BonusDTO decoratedBonusDTO, int multiplicity) {
		super(decoratedBonusDTO, multiplicity);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (super.toString().isEmpty() ? "" : super.toString() + ", ") + getMultiplicity() + " Victory Points";
	}
}
