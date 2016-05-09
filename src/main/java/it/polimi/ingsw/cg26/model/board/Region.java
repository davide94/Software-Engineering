package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.model.bonus.Bonus;
import it.polimi.ingsw.cg26.model.cards.BusinessPermissionTileDeck;
import it.polimi.ingsw.cg26.model.cards.Councillor;
import it.polimi.ingsw.cg26.model.player.Player;

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
        for(City x: cities){
         if(x.getName()==city){
        	 return x;
         }
         
        }return null;
        //scrivere eccezione città non presente
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

    /**
     * @return
     */
    public Set<City> getCities() {
        // TODO implement here
        return null;
    }

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}