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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PoliticCardDTO other = (PoliticCardDTO) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "PoliticCard, " + color + ", price: " + getPrice() + ", owner: " + getOwner();
	}
}
