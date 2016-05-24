package it.polimi.ingsw.cg26.common.state;

public class SellableState {
	
	private int price;
	
	private PlayerState owner;
	
	public SellableState() {
	}
	
	public SellableState(int price, PlayerState owner) {
		this.price = price;
		this.owner = owner;
	}
	
	
}
