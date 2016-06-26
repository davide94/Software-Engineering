package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.dto.EmporiumDTO;
import it.polimi.ingsw.cg26.server.exceptions.ExistingEmporiumException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class City {

    private Double distance = Double.POSITIVE_INFINITY;

    /**
     * the name of the city
     */
    private String name;

    /**
     * the color of the city
     */
    private CityColor color;

    /**
     * the list of emporiums that have been built in the city by the players
     */
    private List<Emporium> emporiums;

    /**
     * the bonus that every player takes if he builds an emporium in the city
     */
    private Bonus bonuses;
    
    /**
     * the list of cities that are linked directly with the city
     */
    private List<City> nearCities;

    /**
     * Default constructor
     * @param name of the city
     * @param color of the city
     * @param bonuses that every player takes if he builds an emporium in the city
     * @param emporiums is the list of emporiums that have been built in the city by the players
     * @param nearCities is the list of cities that are linked directly with the city
     * @throws NullPointerException if one of the parameters is null
     */
    private City(String name, CityColor color, Bonus bonuses, List<Emporium> emporiums, List<City> nearCities) {
        if (name == null || color == null || bonuses == null || emporiums == null || nearCities == null)
            throw new NullPointerException();
        this.name = name;
        this.color = color;
        this.bonuses = bonuses;
        this.emporiums = emporiums;
        this.nearCities = nearCities;
    }

    /**
     * Create a city
     * @param name of the city
     * @param color of the city
     * @param bonuses that every player takes if he builds an emporium in the city
     * @return a new city
     */
    public static City createCity(String name, CityColor color, Bonus bonuses) {
        return new City(name, color, bonuses, new ArrayList<>(), new LinkedList<>());
    }
    
    /**
     * Create a city DTO
     * @return the DTO of a city
     */
    public CityDTO getState() {
        LinkedList<String> nearCitiesState = nearCities.stream().map(City::getName)
                .collect(Collectors.toCollection(LinkedList::new));
        LinkedList<EmporiumDTO> emporiumsState = emporiums.stream().map(Emporium::getState)
                .collect(Collectors.toCollection(LinkedList::new));
        return new CityDTO(name, color.getState(), bonuses.getState(), emporiumsState, nearCitiesState);
    }

    /**
     * Build an emporium in the city
     * @param p is the player that wants to build an emporium in city
     * @throws ExistingEmporiumException if a player has already built an emporium in the city
     * @throws NoRemainingCardsException if there are more politic cards in the deck
     */
    public void build(Player p) throws ExistingEmporiumException, NoRemainingCardsException {
    	for(Emporium x:emporiums)
    	    if(x.getPlayer() == p)
    		    throw new ExistingEmporiumException();
        emporiums.add(Emporium.createEmporium(p));
        takeRecursivelyBonus(p);
    }

    /**
     * Get the name of the city
     * @return the name of the city
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the color of the city
     * @return the color of the city
     */
    public CityColor getColor() {
		return color;
	}

   /**
    * Get the collecction of emporium that have been built in the city
    * @return the emporiums built in the city
    */
	public List<Emporium> getEmporiums() {
		return emporiums;
	}

	/**
	 * Get the bonus that every player takes if he builds an emporium in the city
	 * @return the bonus that every player takes if he builds an emporium in the city
	 */
	public Bonus getBonuses() {
		return bonuses;
	}

	/**
	 * Get the number of emporiums in the city
	 * @return the number of emporiums in the city
	 */
    public int getEmporiumsNumber() {
        return this.emporiums.size();
    }

    /**
     * Check if a player has an emporium in the city
     * @param p is a player of the game
     * @return true if p has an emporium in the city else false
     */
    public boolean hasEmporium(Player p) {
        for (Emporium e: this.emporiums)
            if (e.getPlayer().equals(p))
                return true;
        return false;
    }

	/**
	 * Apply the bonus to a player
	 * @param p is a player of the game
	 * @throws NoRemainingCardsException if there aren't enough politic cards in the deck
	 */
    private void takeBonus(Player p) throws NoRemainingCardsException {
        bonuses.apply(p);
    }
    
    /**
     * Get the list of cities linked with the city
     * @return the list of cities linked with the city
     */
    public List<City> getNearCities() {
		return nearCities;
	}

	/**
	 * Apply the bonus of the cities linked with city to a player
	 * @param p is a player of the game 
	 * @throws NoRemainingCardsException if there aren't enough politic cards in the deck
	 */
    private void takeRecursivelyBonus(Player p) throws NoRemainingCardsException {
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
     * Add a city to nearCities
     * @param c is a city that has to be added to nearCities
     * @throws NullPointerException if c is null
     */
    public void link(City c){
    	if (c == null)
    		throw new NullPointerException();
        if (!this.nearCities.contains(c))
            this.nearCities.add(c);
    }

    /**
     * Set the initial value of distance 
     */
    private void initDistance() {
        if (this.distance.isInfinite())
            return;
        this.distance = Double.POSITIVE_INFINITY;
        for (City city: this.nearCities)
            city.initDistance();
    }

    /**
     * 
     * @param city
     * @return
     */
    public int distanceFrom(City city) {
        city.distance = Double.POSITIVE_INFINITY;
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
                "distance=" + distance +
                ", name='" + name + '\'' +
                ", color=" + color +
                ", emporiums=" + emporiums +
                ", bonuses=" + bonuses +
                //", nearCities=" + nearCities + // Attenzione che entra in un ciclo infinito se il grafo è ciclico
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
