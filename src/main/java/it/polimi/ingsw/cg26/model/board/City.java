package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.model.bonus.Bonus;
import it.polimi.ingsw.cg26.exceptions.*;
import it.polimi.ingsw.cg26.model.player.Player;

import java.util.*;

/**
 * 
 */
public class City {


    private String name;
    
    private CityColor color;
    
    //private Region region;
    
    private List<Emporium> emporiums;
    
    private List<Bonus> bonuses;
    
    private Set<City> nearCities;
    
    

    public City(String name, CityColor color,List<Bonus> bonuses) {
        this.name = name;
        this.color=color;
        this.bonuses=bonuses;
        
        emporiums= new ArrayList<Emporium>();
        
    }
    
    
    /**
     * @param
     */
    public void build(Player p) {
    	
    	for(Emporium x:emporiums){
    	if(x.getPlayer()==p){
    		throw new ExistingEmporiumException();
    		}
    	}
    	
        emporiums.add(new Emporium(p));
        takeBonus(p);
    }
    
        
    public String getName() {
        return this.name;
    }

    
    
    public CityColor getColor() {
		return color;
	}


   
	public List<Emporium> getEmporiums() {
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
       for(Bonus iterBonus:bonuses){
    	   iterBonus.apply(p);
    	   }
    }

    /**
     * @param
     */
    public void distanceFrom(String s) {
        // TODO implement here
    }
    
    
    /*
     * @param city this method add a city to nearCities
     */
    public void link(City c){
    	if(c==null){ 
    		throw new NotValidCityException();
    	}
    	else{
    		this.nearCities.add(c);
    	}
    }
    
    

}