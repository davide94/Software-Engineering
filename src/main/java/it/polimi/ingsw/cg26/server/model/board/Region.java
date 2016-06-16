package it.polimi.ingsw.cg26.server.model.board;


import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTileDeck;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.Collection;
import java.util.LinkedList;


public class Region {

	/**
	 *The name of the region
	 */
	private String name;
	
	/**
	 * The collection of cities in the region
	 */
	private Collection<City> cities;
	
	/**
	 * The balcony of councillors in the region
	 */
	private Balcony balcony;
	
	/**
	 * The deck of business Permission Tiles of the region
	 */
	private BusinessPermissionTileDeck deck;
	
	/**
	 * The bonus applied to the first player that owns all the cities of the region
	 */
	private Bonus bonus;

	
	
	/**
	 * Default constructor
	 * @param name of the region
	 * @param cities in region
	 * @param deck of BPT in region
	 * @param balcony of councillors in region
	 * @param bonus applied to the first player that owns all the cities of the region
	 * @throws NullPointerException if one of the parameters is null
	 */
	private Region(String name, Collection<City> cities, BusinessPermissionTileDeck deck, Balcony balcony, Bonus bonus) {
		if (name == null || cities == null || deck == null || balcony == null || bonus == null)
			throw new NullPointerException();
		this.name = name;
		this.cities = cities;
		this.deck = deck;
		this.balcony = balcony;
		this.bonus = bonus;
	}

	
	/**
	 * Create a region
	 * @param name of the region
	 * @param cities in region
	 * @param deck of BPT in region
	 * @param balcony of councillors in region
	 * @param bonus applied to the first player that owns all the cities of the region
	 * @return a new region
	 */
	public static Region createRegion(String name, Collection<City> cities, BusinessPermissionTileDeck deck, Balcony balcony, Bonus bonus) {
		return new Region(name, new LinkedList<>(cities), deck, balcony, bonus);
	}

	
	/**
	 * Create a region DTO
	 * @return the DTO of the region
	 */
	public RegionDTO getState() {
		LinkedList<CityDTO> citiesState = new LinkedList<>();
		for (City c: cities)
			citiesState.add(c.getState());
		return new RegionDTO(name, citiesState, deck.getState(), balcony.getState(), bonus.getState());
	}
	
	

    /**
     * Check if player has all his emporium in the cities of the region
     * @param player that has just built an emporium
     * @return true if player has all his emporium in the cities of the region else false
     */
    public boolean checkRegionBonuses(Player player){
    	for(City iterCity : cities){
    		if(!iterCity.hasEmporium(player)){
    			return false;
    		}
    	}
    	return true;
    }
    
    
    /**
     * Get the bonus of the region and set null the bonus because it has just been taken
     * @return the bonus of the region
     */
    public Bonus getRegionBonus() {
    	Bonus ret = this.bonus;
    	this.bonus = null;
    	return ret;
    }
   
    
    
    /**
     * Get the collection of cities in the region
     * @return the collection of cities in the region
     */
    public Collection<City> getCities() {
		return cities;
	}


	/**
	 * Get a city in the region if it's present
     * @param the DTO of a city
     * @return the city in region that matches with requiredCity or null if it's not present in region
     */
    public City getCity(CityDTO requiredCity) {
        for(City city: cities)
        	if(city.getName().equalsIgnoreCase(requiredCity.getName()))
        		return city;
        return null;
    }

    
    /**
     * Get the balcony of the region
     * @return the balcony of the region
     */
    public Balcony getBalcony(){
    	return this.balcony;
    }
    
    
    /**
     * Get the deck of BPT of the region
     * @return the deck of BPT of the region
     */
    public BusinessPermissionTileDeck getBPTDeck(){
    	return this.deck;
    }
    
	/**
	 * Get the name of the region
	 * @return the name of the region
	 */
	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) 
			return true;
		if (o == null || (getClass() != o.getClass() && RegionDTO.class != o.getClass())) 
			return false;

		Region region = (Region) o;

		return name != null ? name.equals(region.name) : region.name == null;

	}

	@Override
	public int hashCode() {
		return name != null ? name.hashCode() : 0;
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