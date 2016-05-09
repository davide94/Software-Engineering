package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.model.bonus.Bonus;
import it.polimi.ingsw.cg26.model.cards.BusinessPermissionTileDeck;
import it.polimi.ingsw.cg26.model.cards.Councillor;
import it.polimi.ingsw.cg26.model.player.Player;
import it.polimi.ingsw.cg26.exceptions.*;
import java.util.Collection;
import java.util.Set;

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
    private Collection<Bonus> bonuses;
    

    public Region(String name, Collection<City> cities, BusinessPermissionTileDeck deck, Balcony balcony, Collection<Bonus> bonuses) {
    	if(name==null || cities==null || deck==null || balcony==null || bonuses==null)
    	{throw new NullPointerException();}
    	this.name=name;
    	this.cities=cities;
    	this.deck=deck;
    	this.balcony=balcony;
    	this.bonuses=bonuses;
    	
    	
    }

    /**
     * @param
     * @return
     */
    public City getCity(String city) {
        for(City iterCity: cities){
         if(iterCity.getName().equalsIgnoreCase(city)){
        	 return iterCity;
         }
         
        }throw new NotValidCityException();
    }

    
    
    /**
     * @param councillor
     */
    public Councillor elect(Councillor councillor) {
    	Councillor droppedCouncillor= balcony.elect(councillor);
    	return droppedCouncillor;
    }

    /**
     * @param
     * @param city 
     * @return
     */
    public void build(Player p, String city) {
        getCity(city).build(p);
    }


    
    /*public Set<City> getCities() {
        //implement here
        return null;
    }
    */
    
    
	/**
	 * @return the name of the region
	 */
	public String getName() {
		return name;
	}

}