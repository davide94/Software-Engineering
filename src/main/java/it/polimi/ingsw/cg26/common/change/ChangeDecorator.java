package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;

public abstract class ChangeDecorator implements Change {

	private static final long serialVersionUID = 3146183584551937856L;

	private Change decoratedChange;
	
	public ChangeDecorator(Change decoratedChange) {
		if(decoratedChange == null)
			throw new NullPointerException();
		this.decoratedChange = decoratedChange;
	}
	
	@Override
	public void apply(ClientModel model) {
		decoratedChange.apply(model);
	}

}
