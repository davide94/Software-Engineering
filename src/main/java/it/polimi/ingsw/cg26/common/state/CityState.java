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
    
    public void setEmporiums(List<EmporiumState> emporiums) {
		if (emporiums == null)
			throw new NullPointerException();
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CityState cityState = (CityState) o;

		if (name != null ? !name.equals(cityState.name) : cityState.name != null)
			return false;
		if (color != null ? !color.equals(cityState.color) : cityState.color != null)
			return false;
		if (emporiums != null ? !emporiums.equals(cityState.emporiums) : cityState.emporiums != null)
			return false;
		if (bonuses != null ? !bonuses.equals(cityState.bonuses) : cityState.bonuses != null)
			return false;
		return nearCities != null ? nearCities.equals(cityState.nearCities) : cityState.nearCities == null;

	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (color != null ? color.hashCode() : 0);
		result = 31 * result + (emporiums != null ? emporiums.hashCode() : 0);
		result = 31 * result + (bonuses != null ? bonuses.hashCode() : 0);
		result = 31 * result + (nearCities != null ? nearCities.hashCode() : 0);
		return result;
	}
}
