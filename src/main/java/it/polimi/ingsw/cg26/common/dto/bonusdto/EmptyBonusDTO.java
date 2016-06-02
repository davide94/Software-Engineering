package it.polimi.ingsw.cg26.common.dto.bonusdto;

public class EmptyBonusDTO implements BonusDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1131598535281663529L;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "";
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
		return true;
	}
}
