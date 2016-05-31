package it.polimi.ingsw.cg26.common.dto;

import java.io.Serializable;

public class AssistantDTO extends SellableDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4531014558028133292L;

	/**
	 * Construct an Assistant DTO object
	 * @param price the price of the assistant
	 * @param owner the owner of the assistant
	 */
	public AssistantDTO(int price, String owner) {
		super(price, owner);
		//nothing to do here, assistant has only a price and an owner
	}

}
