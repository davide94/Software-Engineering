package it.polimi.ingsw.cg26.common.dto.bonusdto;

public class CoinBonusDTO extends BonusDTODecorator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9032510890732500289L;

	/**
	 * Decorate a CoinBonusDTO on a decoratedBonusDTO
	 * @param decoratedBonusDTO the bonus to be decorated
	 * @param multiplicity the multiplicity of the bonus
	 */
	public CoinBonusDTO(BonusDTO decoratedBonusDTO, int multiplicity) {
		super(decoratedBonusDTO, multiplicity);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "\nCoinBonus{" + 
				"multiplicity=" + getMultiplicity() + 
				"}";
	}

}
