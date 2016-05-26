package it.polimi.ingsw.cg26.common.dto;

import java.io.Serializable;

/**
 *
 */
public class PoliticCardDTO extends SellableDTO implements Serializable {

    private static final long serialVersionUID = -8556202630984580045L;

    private final PoliticColorDTO color;

    /**
     * Constructs a Politic Card DTO object
     * @param color is a Politic Color DTO
     * @param price is the price of the card if the tile is in the store
     * @param owner is a string that identifies the player who owns the card if the card is in the store
     * @throws NullPointerException if color is null
     */
    public PoliticCardDTO(PoliticColorDTO color, int price, String owner) {
    	super(price, owner);
        if (color == null)
            throw new NullPointerException();
        this.color = color;
    }

    /**
     * Returns a Politic Color DTO
     * @return a Politic Color DTO
     */
    public PoliticColorDTO getColor() {
        return color;
    }

}
