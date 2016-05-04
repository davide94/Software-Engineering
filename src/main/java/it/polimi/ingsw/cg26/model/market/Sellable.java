package it.polimi.ingsw.cg26.model.market;

/**
 * 
 */
public abstract class Sellable {

    /**
     * Default constructor
     */
    public Sellable() {
    }

    /**
     * 
     */
    private int price;
    
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

}