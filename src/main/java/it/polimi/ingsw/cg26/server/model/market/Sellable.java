package it.polimi.ingsw.cg26.server.model.market;

import it.polimi.ingsw.cg26.common.dto.SellableDTO;
import it.polimi.ingsw.cg26.server.model.player.Player;



public abstract class Sellable {

    /**
     *The cost of the object
     */
    private int price;

    /**
     *The owner of the object
     */
    private Player owner;

    /**
     * Default constructor
     */
    public Sellable() {
    }
    
    public abstract SellableDTO getState();

    /**
     * Get the price
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * Set the price
	 * @param price
	 * @throws IllegalArgumentException if the price is negative
	 */
	public void setPrice(int price) {
		if (price < 0)
			throw new IllegalArgumentException();
		this.price = price;
	}

	/**
	 * Get the owner
	 * @return the owner
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * Set the owner
	 * @param owner the owner to set
	 */
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public abstract void giveToNewOwner(Player player);
	
	public abstract void backToOwner();

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
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
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (price != other.price)
			return false;
		return true;
	}
}