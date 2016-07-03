package it.polimi.ingsw.cg26.common.update.request;

import it.polimi.ingsw.cg26.common.ClientModel;

public class CityBonusRequest extends RequestWithMultiplicity {

	private static final long serialVersionUID = 3270777908587153052L;
	
	public CityBonusRequest(int multiplicity) {
		super(multiplicity);
	}
	
	@Override
	public void apply(ClientModel model) {
		model.getState().pendingCityBonusRequest(getMultiplicity());
	}
}
