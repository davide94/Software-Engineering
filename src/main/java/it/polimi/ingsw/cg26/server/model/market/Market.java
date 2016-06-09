package it.polimi.ingsw.cg26.server.model.market;

import it.polimi.ingsw.cg26.common.dto.MarketDTO;
import it.polimi.ingsw.cg26.common.dto.SellableDTO;
import it.polimi.ingsw.cg26.server.exceptions.SellableNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Market {

    /**
     *
     */
    private final List<Sellable> onSale;

    /**
     * Construct an empty market  
     */
    public Market() {
    	this.onSale = new ArrayList<>();
    }
    
    /**
     * Construct a DTO of the market
     * @return the MarketDTO of the market
     */
    public MarketDTO getState(){
    	List<SellableDTO> onSaleDTO = new ArrayList<>();
    	for(Sellable s : this.onSale){
    		onSaleDTO.add(s.getState());
    	}
    	return new MarketDTO(onSaleDTO);
    }
    
    /**
     * returns a copy of the sellable objects on sale
     * @return a list of sellable objects on sale
     */
    public List<Sellable> getOnSale(){
    	return new ArrayList<>(this.onSale);
    }
    
    /**
     * Receives a DTO and return the real sellable object
     * @param sellableDTO the sellable to get from the market
     * @return the sellable object cointained in the market
     * @throws NullPointerException if the given sellableDTO is null
     * @throws SellableNotFoundException if the sellableDTO doesn't match any sellable object in the market
     */
    public Sellable getRealSellable(SellableDTO sellableDTO) throws SellableNotFoundException {
    	if(sellableDTO == null)
    		throw new NullPointerException();
    	for(Sellable s : onSale){
    		if(s.getState().equals(sellableDTO))
    			return s;
    	}
    	throw new SellableNotFoundException();
    }
    
    /**
     * Adds a sellable to the list of the sellable object on sale in the market
     * @param sellable the sellable to put on sale
     * @throws NullPointerException if the argument is null
     */
    public void addToMarket(Sellable sellable){
    	if(sellable == null)
    		throw new NullPointerException();
    	this.onSale.add(sellable);
    }

    /**
     * Removes and return the given sellable from the market
     * @param sellable the sellable to remove
     * @return the removed sellable
     * @throws NullPointerException if the argument is null
     */
    public Sellable removeFromMarket(Sellable sellable){
    	if(sellable == null)
    		throw new NullPointerException();
    	Sellable removedSellable = this.onSale.get(this.onSale.indexOf(sellable));
    	this.onSale.remove(sellable);
    	return removedSellable;
    }
    
    /**
     * Ends the market phase, gives back to each owner their sellables
     */
    public void endMarket(){
    	List<Sellable> sellables = new ArrayList<>(onSale);
    	for(Sellable iterSellable : onSale){
    		iterSellable.setPrice(0);
    		iterSellable.backToOwner();
    	}
    	this.onSale.removeAll(sellables);
    }
}