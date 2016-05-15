package it.polimi.ingsw.cg26.model.market;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Market {

    /**
     * 
     */
    private List<Sellable> onSale;
    
    /**
     * Default constructor
     */
    public Market() {
    	this.onSale = new ArrayList<>();
    }
    
    /**
     * 
     * @param sellable
     */
    public void addToMarket(Sellable sellable){
    	if(sellable == null)
    		throw new NullPointerException();
    	this.onSale.add(sellable);
    }
    
    /**
     * 
     * @param sellable
     */
    public void removeFromMarket(Sellable sellable){
    	if(sellable == null)
    		throw new NullPointerException();
    	this.onSale.remove(sellable);
    }
}