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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Councillor that = (Councillor) o;

        return color != null ? color.equals(that.color) : that.color == null;

    }

    @Override
    public int hashCode() {
        return color != null ? color.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Councillor{" +
                "color=" + color +
                '}';
    }
    
}