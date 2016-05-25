package it.polimi.ingsw.cg26.common.state;

public class SellableState {
	
	private int price;
	
	private String owner;
	
	public SellableState(int price, String owner) {
		if (price < 0)
			throw new IllegalArgumentException();
		this.price = price;
		this.owner = owner;
	}
	
	
}
