package it.polimi.ingsw.cg26.client.commands;

import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;

import java.io.Serializable;

/**
 *
 */
public abstract class Elect extends Action {

	private static final long serialVersionUID = -5720689551302863319L;

	private final String region;

    private final PoliticColor councillorColor;

    /**
     * 
     * @param region
     * @param councillorColor
     */
    public Elect(String region, PoliticColor councillorColor) {
        if (region == null || councillorColor == null)
            throw new NullPointerException();
        this.region = region;
        this.councillorColor = councillorColor;
    }

}
