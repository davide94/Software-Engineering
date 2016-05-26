package it.polimi.ingsw.cg26.common.dto;

public abstract class SellableDTO {
	
	private int price;
	
	private String owner;

	/**
	 * Constructs a Sellable DTO object
	 * @param price is the price of the item if the tile is in the store
	 * @param owner is a string that identifies the player who owns the item if the item is in the store
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
}
