package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.model.bonus.Bonus;
import it.polimi.ingsw.cg26.model.player.Player;

import java.util.*;

/**
 * 
 */
public class City {


    private String name;
    
    private CityColor color;
    
    //private Region region;
    
    private List<Player> emporiums;
    
    private List<Bonus> bonuses;
    
    //private Set<City> nearCities;
    
    

    public City(String name, CityColor color,/*Set<City> nearCities,*/ List<Bonus> bonuses) {
        this.name = name;
        this.color=color;
        //this.nearCities=nearCities;
        this.bonuses=bonuses;
        
        emporiums= new ArrayList<Player>();
        
    }
    
    
    /**
     * @param
     */
    public void build(Player p) {
        emporiums.add(p);
    }
    
        
    public String getName() {
        return this.name;
    }

    
    
    public CityColor getColor() {
		return color;
	}


   
	public List<Player> getEmporiums() {
		return emporiums;
	}


	
	public List<Bonus> getBonuses() {
		return bonuses;
	}

/*
	public Set<City> getNearCities() {
		return nearCities;
	}
*/

	

	/**
     * @param
     */
    private void takeBonus(Player p) {
        // TODO implement here
    }

    /**
     * @param
     */
    public void distanceFrom(String s) {
        // TODO implement here
    }

}