package it.polimi.ingsw.cg26.server.model.cards;

import it.polimi.ingsw.cg26.common.state.CityState;
import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.market.Sellable;
import it.polimi.ingsw.cg26.common.state.BonusState;
import it.polimi.ingsw.cg26.common.state.BusinessPermissionTileState;

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
    private Collection<Bonus> bonuses;

    /**
     * Constructs a Business Permit Tile
     * @param cities is a collection of cities in which the tile permits to build
     * @param bonuses is a collection of bonuses that the tile makes you earn
     * @throws NullPointerException if cities or bonuses are null
     */
    public BusinessPermissionTile(Collection<City> cities, Collection<Bonus> bonuses) {
        if (cities == null || bonuses == null)
            throw new NullPointerException();
        this.cities = new LinkedList<>(cities);
        this.bonuses = new LinkedList<>(bonuses);
    }

    /**
     * Generates the state of the tile
     * @return the state of the tile
     */
    public BusinessPermissionTileState getState() {
        LinkedList<String> citiesState = new LinkedList<>();
        for (City c: cities)
            citiesState.add(c.getName());
        LinkedList<BonusState> bonusesState = new LinkedList<>();
        for (Bonus b: bonuses)
            bonusesState.add(b.getState());
        return new BusinessPermissionTileState(citiesState, bonusesState);
    }

    /**
     * Checks if with this tile it is possible to build an emporium in a city
     * @param city is the city where is checked if it is possible to build an emporium
     * @return true if it is possible to build an emporium in city with this tile, false if not
     */
    public boolean canBuildIn(CityState city) {
        for (City c: this.cities)
            if (c.getName().equalsIgnoreCase(city.getName()))
                return true;
        return false;
    }

    /**
     * Reassigns the tile to his owner
     */
    @Override
    public void backToOwner() {
    	this.getOwner().addPermissionTile(this);
    }

    @Override
    public String toString() {
        String ret = "BusinessPermissionTile{cities={";
        for (City city: this.cities)
            ret += city.getName() + " ,";
        ret = ret.substring(0, ret.length() - 3);
        ret += "}, bonuses=" + bonuses + '}';
        return ret;
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
    
}