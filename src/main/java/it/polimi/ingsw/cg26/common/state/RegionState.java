package it.polimi.ingsw.cg26.common.state;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class RegionState implements Serializable {

    private static final long serialVersionUID = 2260796972775485470L;

    private final String name;

    private final List<CityState> cities;

    private BalconyState balcony;

    private BusinessPermissionTileDeckState deck;

    private final List<BonusState> bonus;

    public RegionState(String name, List<CityState> cities, BusinessPermissionTileDeckState deck, BalconyState balcony, List<BonusState> bonus) {
        if (name == null || cities == null || deck == null || balcony == null || bonus == null)
            throw new NullPointerException();
        this.name = name;
        this.cities = cities;
        this.deck = deck;
        this.balcony = balcony;
        this.bonus = bonus;
    }

    public String getName() {
        return name;
    }

    public List<CityState> getCities() {
        return cities;
    }

    public BalconyState getBalcony() {
        return balcony;
    }
    
    public void setBalcony(BalconyState balcony){
    	this.balcony = balcony;
    }

    public BusinessPermissionTileDeckState getDeck() {
        return deck;
    }
    
    public void setDeck(BusinessPermissionTileDeckState deck){
    	this.deck = deck;
    }

    public List<BonusState> getBonus() {
        return bonus;
    }

    @Override
    public String toString() {
        return "RegionState{" +
                "name='" + name + '\'' +
                ", cities=" + cities +
                ", balcony=" + balcony +
                ", deck=" + deck +
                ", bonus=" + bonus +
                '}';
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bonus == null) ? 0 : bonus.hashCode());
		result = prime * result + ((cities == null) ? 0 : cities.hashCode());
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
		RegionState other = (RegionState) obj;
		if (bonus == null) {
			if (other.bonus != null)
				return false;
		} else if (!bonus.equals(other.bonus))
			return false;
		if (cities == null) {
			if (other.cities != null)
				return false;
		} else if (!cities.equals(other.cities))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
    
    
}
