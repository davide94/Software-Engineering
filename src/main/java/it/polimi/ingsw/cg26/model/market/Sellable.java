package it.polimi.ingsw.cg26.model.market;

import it.polimi.ingsw.cg26.model.player.Player;

/**
 * 
 */
public abstract class Sellable {

    /**
     * 
     */
    private int price;
    
    /**
     * 
     */
    private Player owner;
	
    /**
     * Default constructor
     */
    public Sellable() {
    }

    /**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return the owner
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(Player owner) {
		this.owner = owner;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + price;
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
		Sellable other = (Sellable) obj;
		if (price != other.price)
			return false;
		return true;
	}
	
	

}