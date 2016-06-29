package it.polimi.ingsw.cg26.common.dto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class MarketDTO implements Serializable {

    private static final long serialVersionUID = 446702034944104371L;
    
    private final List<SellableDTO> onSale;
    
    /**
     * Construct a MarketDTO object
     * @param onSale the sellable objects that are on sale
     * @throws NullPointerException if the argument is null
     */
    public MarketDTO(List<SellableDTO> onSale){
    	if(onSale == null)
    		throw new NullPointerException();
    	this.onSale = new LinkedList<>(onSale);
    }

	/**
	 * @return the sellableDTO onSale in the market
	 */
	public List<SellableDTO> getOnSale() {
		return new LinkedList<>(onSale);
	}
}
