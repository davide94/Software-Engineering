package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.model.bonus.Bonus;
import it.polimi.ingsw.cg26.model.cards.*;
import it.polimi.ingsw.cg26.model.player.Player;

import java.util.Collection;
import java.util.LinkedList;

/**
 * 
 */
public class Region {

    /**
     * 
     */
    private String name;
    private Collection<City> cities;
    private Balcony balcony;
    private BusinessPermissionTileDeck deck;
    private Collection<Bonus> bonus;

    public Region(String name, Collection<City> cities, BusinessPermissionTileDeck deck, Balcony balcony, Collection<Bonus> bonus) {
    	if(name==null || cities==null || deck==null || balcony==null || bonus ==null)
    		throw new NullPointerException();
    	this.name=name;
    	this.cities=new LinkedList<>(cities);
    	this.deck=deck;
    	this.balcony=balcony;
    	this.bonus = new LinkedList<>(bonus);
	}
    
    /**
     * 
     * @param player
     * @return
     */
    private boolean checkRegionBonuses(Player player){
    	if(bonus.isEmpty()){
    		return false;
    	}
    	for(City iterCity : cities){
    		if(!iterCity.hasEmporium(player)){
    			return false;
    		}
    	}
    	return true;
    }

    /**
     * @param
     * @return
     */
    public City getCity(String city) {
        for(City iterCity: cities)
        	if(iterCity.getName().equalsIgnoreCase(city))
        		return iterCity;
        return null;
    }

    public Balcony getBalcony(){
    	return this.balcony;
    }
    
    public BusinessPermissionTileDeck getBPTDeck(){
    	return this.deck;
    }
    
	/**
	 * @return the name of the region
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Region{" +
				"name='" + name + '\'' +
				", cities=" + cities +
				", balcony=" + balcony +
				", deck=" + deck +
				", bonus=" + bonus +
				'}';
	}

}