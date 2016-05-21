package it.polimi.ingsw.cg26.common.state;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class CityState implements Serializable {

    private static final long serialVersionUID = -6088147095293002180L;

    private final String name;

    private final CityColorState color;

    private List<EmporiumState> emporiums;

    private final List<BonusState> bonuses;

    private final List<String> nearCities;

    public CityState(String name, CityColorState color, List<BonusState> bonuses, List<EmporiumState> emporiums, List<String> nearCities) {
        if (name == null || color == null || bonuses == null || emporiums == null || nearCities == null)
            throw new NullPointerException();
        this.name = name;
        this.color = color;
        this.bonuses = bonuses;
        this.emporiums = emporiums;
        this.nearCities = nearCities;
    }

    public String getName() {
        return name;
    }

    public CityColorState getColor() {
        return color;
    }

    public List<EmporiumState> getEmporiums() {
        return emporiums;
    }
    
    public void setEmporiums(List<EmporiumState> emporiums){
    	this.emporiums = emporiums;
    }

    public List<BonusState> getBonuses() {
        return bonuses;
    }

    public List<String> getNearCities() {
        return nearCities;
    }

    @Override
    public String toString() {
        return "CityState{" +
                "name='" + name + '\'' +
                ", color=" + color +
                ", emporiums=" + emporiums +
                ", bonuses=" + bonuses +
                ", nearCities=" + nearCities +
                '}';
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bonuses == null) ? 0 : bonuses.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nearCities == null) ? 0 : nearCities.hashCode());
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
		CityState other = (CityState) obj;
		if (bonuses == null) {
			if (other.bonuses != null)
				return false;
		} else if (!bonuses.equals(other.bonuses))
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nearCities == null) {
			if (other.nearCities != null)
				return false;
		} else if (!nearCities.equals(other.nearCities))
			return false;
		return true;
	}
    
    
}
