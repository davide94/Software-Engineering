package it.polimi.ingsw.cg26.server.model.cards;

import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.market.Sellable;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
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
    private Collection<City> cities;

    /**
     * Collection of bonuses that the tile makes you earn
     */
    private RewardTile reward;

    /**
     * Constructs a Business Permit Tile
     * @param cities is a collection of cities in which the tile permits to build
     * @param reward is reward tile that the tile makes you earn
     * @throws NullPointerException if cities or bonuses are null
     */
    public BusinessPermissionTile(Collection<City> cities, RewardTile reward) {
        if (cities == null || reward == null)
            throw new NullPointerException();
        this.cities = new LinkedList<>(cities);
        this.reward = reward;
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
        return new BusinessPermissionTileDTO(citiesState, reward.getState(), this.getPrice(), name);
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

    public void getReward(Player p) {
        reward.apply(p);
    }

    /**
     * Reassigns the tile to his owner
     */
    @Override
    public void backToOwner() {
    	this.getOwner().addPermissionTile(this);
    }
    
    @Override
	public void giveToNewOwner(Player player){
    	player.addPermissionTile(this);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cities == null) ? 0 : cities.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BusinessPermissionTile other = (BusinessPermissionTile) obj;
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
                ", reward=" + reward +
                '}';
    }
}