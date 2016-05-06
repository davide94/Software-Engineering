package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.model.player.Player;

import java.util.*;

/**
 * 
 */
public class City {
	
	 /**
     * 
     */
    private String name;

    
    /**
     * 
     */
    private CityColor color;
    
    /**
     * 
     */
    private Set<City> nearCities;
    

    /**
     * Default constructor
     */
    public City(String name, CityColor color) {
    	this.name=name;
    	this.color=color;
    	this.nearCities= new HashSet<City>();
    }

     

    /**
     * @param
     */
    public void build(Player p) {
        // TODO implement here
    }

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