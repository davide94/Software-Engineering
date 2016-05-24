package it.polimi.ingsw.cg26.server.model.board;


import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTileDeck;
import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import it.polimi.ingsw.cg26.server.model.player.Player;
import it.polimi.ingsw.cg26.common.state.CityState;
import it.polimi.ingsw.cg26.common.state.RegionState;

import java.util.Collection;
import java.util.LinkedList;

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
	private RewardTile bonus;

	private Region(String name, Collection<City> cities, BusinessPermissionTileDeck deck, Balcony balcony, RewardTile bonus) {
		if (name == null || cities == null || deck == null || balcony == null || bonus == null)
			throw new NullPointerException();
		this.name = name;
		this.cities = cities;
		this.deck = deck;
		this.balcony = balcony;
		this.bonus = bonus;
	}

	public static Region createRegion(String name, Collection<City> cities, BusinessPermissionTileDeck deck, Balcony balcony, RewardTile bonus) {
		return new Region(name, new LinkedList<>(cities), deck, balcony, bonus);
	}

	public RegionState getState() {
		LinkedList<CityState> citiesState = new LinkedList<>();
		for (City c: cities)
			citiesState.add(c.getState());
		return new RegionState(name, citiesState, deck.getState(), balcony.getState(), bonus.getState());
	}

    /**
     * 
     * @param player
     * @return
     */
    public boolean checkRegionBonuses(Player player){
    	for(City iterCity : cities){
    		if(!iterCity.hasEmporium(player)){
    			return false;
    		}
    	}
    	return true;
    }
    
    public RewardTile getRegionBonus() {
    	RewardTile ret = this.bonus;
    	this.bonus = null;
    	return ret;
    }
   
    
    public Collection<City> getCities() {
		return cities;
	}


	/**
     * @param
     * @return
     */
    public City getCity(CityState requiredCity) {
        for(City city: cities)
        	if(city.getName().equalsIgnoreCase(requiredCity.getName()))
        		return city;
        return null;
    }

    public Balcony getBalcony(){
    	return this.balcony;
    }
    
    public BusinessPermissionTileDeck getBPTDeck(){
    	return this.deck;
    }
    
	/**
	 * @return the name of the region
	 */
	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || (getClass() != o.getClass() && RegionState.class != o.getClass())) return false;

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