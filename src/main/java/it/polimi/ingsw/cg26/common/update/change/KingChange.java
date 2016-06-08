package it.polimi.ingsw.cg26.common.update.change;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.dto.KingDTO;

public class KingChange extends ChangeDecorator {

	private static final long serialVersionUID = 5707210211297999062L;

	private KingDTO kingDTO;
	
	/**
	 * Constructs a change of the king's state
	 * @param decoratedChange the change to decorate
	 * @param kingDTO the new state of the king
	 * @throws NullPointerException if one or more arguments are null
	 */
	public KingChange(Change decoratedChange, KingDTO kingDTO) {
		super(decoratedChange);
		if(kingDTO == null)
			throw new NullPointerException();
		this.kingDTO = kingDTO;
	}
	
	@Override
	public void apply(ClientModel model) {
		super.apply(model);
		model.setKing(kingDTO);
	}

}
