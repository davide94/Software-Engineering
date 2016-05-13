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
    public Councillor(PoliticColor color) {
        this.color = color;
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