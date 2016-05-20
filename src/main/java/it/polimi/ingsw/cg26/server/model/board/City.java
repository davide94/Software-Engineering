package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.server.exceptions.ExistingEmporiumException;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.player.Player;
import it.polimi.ingsw.cg26.common.state.BonusState;
import it.polimi.ingsw.cg26.common.state.CityState;
import it.polimi.ingsw.cg26.common.state.EmporiumState;

import java.util.*;

/**
 * 
 */
public class City {

    private Double distance = Double.POSITIVE_INFINITY;

    private String name;
    
    private CityColor color;

    private List<Emporium> emporiums;
    
    private List<Bonus> bonuses;
    
    private List<City> nearCities;

    private City(String name, CityColor color, List<Bonus> bonuses, List<Emporium> emporiums, List<City> nearCities) {
        if (name == null || color == null || bonuses == null || emporiums == null || nearCities == null)
            throw new NullPointerException();
        this.name = name;
        this.color = color;
        this.bonuses = bonuses;
        this.emporiums = emporiums;
        this.nearCities = nearCities;
    }

    public static City createCity(String name, CityColor color,List<Bonus> bonuses) {
        return new City(name, color, new LinkedList<>(bonuses), new ArrayList<>(), new LinkedList<>());
    }

    public CityState getState() {
        LinkedList<BonusState> bonusesState = new LinkedList<>();
        for (Bonus b: bonuses)
            bonusesState.add(b.getState());
        LinkedList<String> nearCitiesState = new LinkedList<>();
        for (City c: nearCities)
            nearCitiesState.add(c.getName());
        LinkedList<EmporiumState> emporiumsState = new LinkedList<>();
        for (Emporium e: emporiums)
            emporiumsState.add(e.getState());
        return new CityState(name, color.getState(), bonusesState, emporiumsState, nearCitiesState);
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
        emporiums.add(Emporium.createEmporium(p));
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

    public int getEmporiumsNumber() {
        return this.emporiums.size();
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
        LinkedList<City> queue = new LinkedList<>();
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
        if (this.distance.isInfinite())
            return;
        this.distance = Double.POSITIVE_INFINITY;
        for (City city: this.nearCities)
            city.initDistance();
    }

    public int distanceFrom(City city) {
        this.initDistance();
        this.distance = 0.;
        LinkedList<City> queue = new LinkedList<>();
        queue.add(this);
        while (!queue.isEmpty()) {
            City u = queue.poll();
            for (City v: u.nearCities)
            {
                Double distanceThroughU = u.distance + 1;
                if (distanceThroughU < v.distance) {
                    queue.remove(v);
                    v.distance = distanceThroughU;
                    queue.add(v);
                }
            }
        }
        return city.distance.intValue();
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", color=" + color +
                ", emporiums=" + emporiums +
                ", bonuses=" + bonuses +
                '}';
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name) || !name.equalsIgnoreCase(other.name))
			return false;
		return true;
	}
    
    
}