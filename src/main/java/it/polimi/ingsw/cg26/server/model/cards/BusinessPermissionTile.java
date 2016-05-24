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
     *
     */
    private Collection<City> cities;

    /**
     *
     */
    private Collection<Bonus> bonuses;

    /**
     * Default constructor
     */
    public BusinessPermissionTile(Collection<City> cities, Collection<Bonus> bonuses) {
        if (cities == null || bonuses == null)
            throw new NullPointerException();
        this.cities = new LinkedList<>(cities);
        this.bonuses = new LinkedList<>(bonuses);
    }

    @Override
    public BusinessPermissionTileState getState() {
        LinkedList<String> citiesState = new LinkedList<>();
        for (City c: cities)
            citiesState.add(c.getName());
        LinkedList<BonusState> bonusesState = new LinkedList<>();
        for (Bonus b: bonuses)
            bonusesState.add(b.getState());
        return new BusinessPermissionTileState(citiesState, bonusesState, this.getPrice(), this.getOwner().getState());
    }

    /**
     * @return
     */
    public Collection<Bonus> getBonuses() {
        Collection<Bonus> c =  new LinkedList<>();
        c.addAll(this.bonuses);
        return c;
    }

    public boolean canBuildIn(CityState city) {
        for (City c: this.cities)
            if (c.getName().equalsIgnoreCase(city.getName()))
                return true;
        return false;
    }

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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		if (cities == null) {
			if (other.cities != null)
				return false;
		} else if (!cities.equals(other.cities))
			return false;
		return true;
	}
    
    
}