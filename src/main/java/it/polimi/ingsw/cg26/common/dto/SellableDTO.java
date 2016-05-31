package it.polimi.ingsw.cg26.common.dto;

import java.io.Serializable;

public abstract class SellableDTO implements Serializable {

	private static final long serialVersionUID = -6623553061225790883L;

	private int price;
	
	private String owner;

	/**
	 * Constructs a Sellable DTO object
	 * @param price is the price of the item if the tile is in the store
	 * @param owner is a string that identifies the player who owns the item if the item is in the store
	 * @throws IllegalArgumentException if the price is negative
	 */
	public SellableDTO(int price, String owner) {
		if (price < 0)
			throw new IllegalArgumentException();
		this.price = price;
		this.owner = owner;
	}

	/**
	 * Returns the price of the item
	 * @return the price of the item
     */
	public int getPrice() {
		return price;
	}

	/**
	 * Returns the owner of the item
	 * @return the owner of the item
     */
	public String getOwner() {
		return owner;
	}

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
		SellableDTO other = (SellableDTO) obj;
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
