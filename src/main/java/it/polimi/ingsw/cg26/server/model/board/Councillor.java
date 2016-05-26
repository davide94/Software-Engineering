package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;

/**
 * 
 */
public class Councillor {

    /**
     *
     */
    private PoliticColor color;

    /**
     * Default constructor
     */
    private Councillor(PoliticColor color) {
        if (color == null)
            throw new NullPointerException();
        this.color = color;
    }

    public static Councillor createCouncillor(PoliticColor color) {
        return new Councillor(color);
    }

    public CouncillorDTO getState() {
        return new CouncillorDTO(color.getState());
    }

    /**
     *
     */
    public PoliticColor getColor() {
        return this.color;
    }

    @Override
    public String toString() {
        return "Councillor{" +
                "color=" + color +
                '}';
    }
    
}