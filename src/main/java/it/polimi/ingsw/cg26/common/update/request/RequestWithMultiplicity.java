package it.polimi.ingsw.cg26.common.update.request;

import it.polimi.ingsw.cg26.common.ClientModel;

public abstract class RequestWithMultiplicity implements Request {

	private static final long serialVersionUID = 7426058755022356809L;
	
	private final int multiplicity;
	
	public RequestWithMultiplicity(int multiplicity) {
		if(multiplicity < 1)
			throw new IllegalArgumentException();
		this.multiplicity = multiplicity;
	}
	
	@Override
	public abstract void apply(ClientModel model);

	/**
	 * @return the multiplicity
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
		RequestWithMultiplicity other = (RequestWithMultiplicity) obj;
		if (multiplicity != other.multiplicity)
			return false;
		return true;
	}
	
	

}
