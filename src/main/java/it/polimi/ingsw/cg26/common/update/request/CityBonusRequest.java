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

}
