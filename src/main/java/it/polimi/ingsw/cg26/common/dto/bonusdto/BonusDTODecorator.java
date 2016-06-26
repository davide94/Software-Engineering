package it.polimi.ingsw.cg26.common.dto.bonusdto;

/**
 *
 */
public class BonusDTODecorator implements BonusDTO {

	
	/**
	 * Serial number
	 */
    private static final long serialVersionUID = 2736328090252332859L;

    
    /**
     * The multiplicity of the bonus
     */
	private final int multiplicity;
	
	private BonusDTO decoratedBonusDTO;

	/**
	 * Constructs a Bonus DTO object
	 * @param decoratedBonusDTO is the bonus to decorate
	 * @param multiplicity is the multiplicity
	 * @throws NullPointerException decoratedBonus is null
	 * @throws IllegalArgumentException if kind is empty or multiplicity is negative
     */
    public BonusDTODecorator(BonusDTO decoratedBonusDTO, int multiplicity) {
		if (multiplicity < 0)
			throw new IllegalArgumentException();
		if(decoratedBonusDTO == null)
			throw new NullPointerException();
		this.multiplicity = multiplicity;
		this.decoratedBonusDTO = decoratedBonusDTO;
    }

	/**
	 * Returns the multiplicity of the bonus
	 * @return the multiplicity of the bonus
     */
	public int getMultiplicity() {
		return multiplicity;
	}

	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + multiplicity;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BonusDTODecorator other = (BonusDTODecorator) obj;
		if (multiplicity != other.multiplicity)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return decoratedBonusDTO.toString();
	}

}
