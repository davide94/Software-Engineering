package it.polimi.ingsw.cg26.model.market;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg26.model.player.Player;

/**
 * 
 */
public class Market {

    /**
     * Default constructor
     */
    public Market() {
    	this.onSale = new ArrayList<Sellable>();
    }
    
    /**
     * 
     */
    private List<Sellable> onSale;
    
    /**
     * @param sellable is the object the player wants to sell
     * @param price is the price established by the player
     */
    public void sell(Sellable sellable, int price) {
        if(sellable==null){
        	throw new NullPointerException();
        } else if(price < 1) {
        	throw new IllegalArgumentException();
        } else {
        	sellable.setPrice(price);
        	onSale.add(sellable);
        }
    }

    /**
     * @param
     * @return
     */
    public Boolean buy(Player p) {
        // TODO implement here
        return null;
    }

}