package it.polimi.ingsw.cg26.model.cards;

import it.polimi.ingsw.cg26.model.board.City;
import it.polimi.ingsw.cg26.model.bonus.Bonus;
import it.polimi.ingsw.cg26.model.market.Sellable;

import java.util.Collection;
import java.util.LinkedList;

/**
 * 
 */
public class BusinessPermissionTile extends Sellable {

    /**
     *
     */
    //private Boolean used; // TODO usiamo questo flag oppure teniamo separate le carte usate e quelle non?

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
        this.cities = new LinkedList<>();
        this.cities.addAll(cities);

        this.bonuses = new LinkedList<>();
        this.bonuses.addAll(bonuses);
    }

    /**
     * @return
     */
    public Collection<City> getCities() {
        Collection<City> c =  new LinkedList<>();
        c.addAll(this.cities);
        return c;
    }

    /**
     * @return
     */
    public Collection<Bonus> getBonuses() {
        Collection<Bonus> c =  new LinkedList<>();
        c.addAll(this.bonuses);
        return c;
    }

    public boolean canBuildIn(String name) {
        for (City city: this.cities)
            if (city.getName().equalsIgnoreCase(name))
                return true;
        return false;
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