package it.polimi.ingsw.cg26.server.model.cards;

import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.market.Sellable;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.Collection;
import java.util.LinkedList;

/**
 * 
 */
public class BusinessPermissionTile extends Sellable {

    /**
     * Collection of cities in which the tile permits to build
     */
    private final Collection<City> cities;

    /**
     * Collection of bonuses that the tile makes you earn
     */
    private final Bonus bonuses;

    /**
     * Constructs a Business Permit Tile
     * @param cities is a collection of cities in which the tile permits to build
     * @param reward is reward tile that the tile makes you earn
     * @throws NullPointerException if cities or bonuses are null
     */
    public BusinessPermissionTile(Collection<City> cities, Bonus bonuses) {
        if (cities == null || bonuses == null)
            throw new NullPointerException();
        this.cities = new LinkedList<>(cities);
        this.bonuses = bonuses;
    }

    @Override
    /**
     * Generates the dto of the tile
     * @return the dto of the tile
     */
    public BusinessPermissionTileDTO getState() {
        LinkedList<String> citiesState = new LinkedList<>();
        for (City c: cities)
            citiesState.add(c.getName());
        Player owner = this.getOwner();
        String name = "none";
        if (owner != null)
            name = owner.getName();
        return new BusinessPermissionTileDTO(citiesState, bonuses.getState(), this.getPrice(), name);
    }

    /**
     * Checks if with this tile it is possible to build an emporium in a city
     * @param city is the city where is checked if it is possible to build an emporium
     * @return true if it is possible to build an emporium in city with this tile, false if not
     */
    public boolean canBuildIn(CityDTO city) {
        for (City c: this.cities)
            if (c.getName().equalsIgnoreCase(city.getName()))
                return true;
        return false;
    }

    
    /**
     * Get the cities on the BPT
     * @return the cities on the BPT
     */
    public Collection<City> getCities() {
        return new LinkedList<>(cities);
    }

    
    /**
     * Apply the bonus to the player
     * @param p is the player that should receive the bonus
     * @throws NoRemainingCardsException if there aren't enough politic cards in the deck
     */
    public void getReward(Player p) throws NoRemainingCardsException {
        bonuses.apply(p);
    }
    
    
    /**
     * Get the bonuses of the BPT
     * @return the bonuses of the BPT
     */
    public Bonus getBonuses(){
    	return this.bonuses;
    }

    /**
     * Reassigns the tile to his owner
     */
    @Override
    public void backToOwner() {
    	this.getOwner().addPermissionTile(this);
    }
    
    
    /**
     * Add the BPT to the new player
     */
    @Override
	public void giveToNewOwner(Player player){
    	player.addPermissionTile(this);
    }

    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bonuses == null) ? 0 : bonuses.hashCode());
		result = prime * result + ((cities == null) ? 0 : cities.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BusinessPermissionTile other = (BusinessPermissionTile) obj;
		if (bonuses == null) {
			if (other.bonuses != null)
				return false;
		} else if (!bonuses.equals(other.bonuses))
			return false;
		if (cities == null) {
			if (other.cities != null)
				return false;
		} else if (!cities.equals(other.cities))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "BusinessPermissionTile{" +
                "cities=" + cities +
                ", bonuses=" + bonuses +
                '}';
    }
}