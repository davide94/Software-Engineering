package it.polimi.ingsw.cg26.model.board;

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
    
    private List<City> nearCities;

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
        takeRecursivelyBonus(p);
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

    public boolean hasEmporium(Player p) {
        for (Emporium e: this.emporiums)
            if (e.getPlayer().equals(p))
                return true;
        return false;
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
     *
     */
    private void takeRecursivelyBonus(Player p) {
        PriorityQueue<City> queue = new PriorityQueue<>();
        LinkedList<City> taken = new LinkedList<>();
        queue.add(this);

        while (!queue.isEmpty()) {
            City u = queue.poll();
            if (u.hasEmporium(p) && !taken.contains(u)) {
                u.takeBonus(p);
                taken.add(u);
                queue.addAll(u.nearCities);
            }

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
        this.distance = 0.;
        PriorityQueue<City> vertexQueue = new PriorityQueue<>();
        vertexQueue.add(this);

        while (!vertexQueue.isEmpty()) {
            City u = vertexQueue.poll();

            for (City v: u.nearCities)
            {
                double distanceThroughU = u.distance + 1;
                if (distanceThroughU < v.distance) {
                    vertexQueue.remove(v);

                    v.distance = distanceThroughU;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
        }
        return (int) city.distance;
    }

}
