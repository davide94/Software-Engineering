package it.polimi.ingsw.cg26.client.commands;

import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;

public class ElectAsMainAction extends Elect {

	private static final long serialVersionUID = -5968536496577636987L;

	/**
	 * 
	 * @param region
	 * @param councillorColor
	 */
	public ElectAsMainAction(String region, PoliticColor councillorColor) {
		super(region, councillorColor);
	}

}
