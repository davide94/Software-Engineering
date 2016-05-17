package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.model.cards.PoliticColor;

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