package it.polimi.ingsw.cg26.common.dto.bonusdto;

public class AssistantBonusDTO extends BonusDTODecorator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8869020502968734786L;

	/**
	 * Decorate an AssistantBonusDTO on a decoratedBonusDTO
	 * @param decoratedBonusDTO the bonus to be decorated
	 * @param multiplicity the multiplicity of the bonus
	 */
	public AssistantBonusDTO(BonusDTO decoratedBonusDTO, int multiplicity) {
		super(decoratedBonusDTO, multiplicity);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (super.toString().isEmpty() ? "" : super.toString() + ", ") + getMultiplicity() + " Assistants";
	}
}
