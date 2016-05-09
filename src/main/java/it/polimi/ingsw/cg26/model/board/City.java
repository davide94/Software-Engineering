package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.model.Dijkstra;
import it.polimi.ingsw.cg26.model.bonus.Bonus;
import it.polimi.ingsw.cg26.exceptions.*;
import it.polimi.ingsw.cg26.model.player.Player;

import java.util.*;

/**
 * 
 */
public class City implements Comparable<City> {

    public double distance = Double.POSITIVE_INFINITY;
    public City previous;
    public int compareTo(City other) {
        return Double.compare(distance, other.distance);
    }

    private String name;
    
    private CityColor color;

    private List<Emporium> emporiums;
    
    private List<Bonus> bonuses;
    
    public List<City> nearCities;

    private boolean visited = false;
    

    public City(String name, CityColor color,List<Bonus> bonuses) {
        this.name = name;
        this.color = color;
        this.bonuses = bonuses;
        
        this.emporiums = new ArrayList<Emporium>();
        this.nearCities = new LinkedList<>();
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

	

	/**
     * @param
     */
    private void takeBonus(Player p) {
       for(Bonus iterBonus:bonuses){
    	   iterBonus.apply(p);
    	   }
    }
    
    /**
     * @param c this method add a city to nearCities
     */
    public void link(City c){
    	if (c == null)
    		throw new NullPointerException();
        if (!this.nearCities.contains(c))
            this.nearCities.add(c);
    }

    private void initDistance() {
        if (this.distance == Double.POSITIVE_INFINITY)
            return;
        this.distance = Double.POSITIVE_INFINITY;
        for (City city: this.nearCities)
            city.initDistance();
    }

    public int distanceFrom(City city) {
        this.initDistance();
        Dijkstra.computePaths(this);
        return (int) city.distance;
    }

}
