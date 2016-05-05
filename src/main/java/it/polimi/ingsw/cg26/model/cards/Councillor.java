package it.polimi.ingsw.cg26.model.cards;

/**
 * 
 */
public class Councillor {

    /**
     *
     */
    private CouncillorColor color;

    /**
     * Default constructor
     */
    public Councillor(CouncillorColor color) {
        this.color = color;
    }

    /**
     *
     */
    public CouncillorColor getColor() {
        return this.color;
    }

}