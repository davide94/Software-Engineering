package it.polimi.ingsw.cg26.common.update.request;

import it.polimi.ingsw.cg26.common.ClientModel;

public class CityBonusRequest implements Request {

	private static final long serialVersionUID = 3270777908587153052L;

	private int multiplicity;
	
	public CityBonusRequest(int multiplicity) {
		if(multiplicity < 1)
			throw new IllegalArgumentException();
		this.multiplicity = multiplicity;
	}
	
	@Override
	public void apply(ClientModel model) {
		// TODO Auto-generated method stub

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
		CityBonusRequest other = (CityBonusRequest) obj;
		if (multiplicity != other.multiplicity)
			return false;
		return true;
	}
	
	

}
