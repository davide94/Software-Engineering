package it.polimi.ingsw.cg26.common.dto;

import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

/**
 *
 */
public class CityDTO implements Serializable {

    private static final long serialVersionUID = -6088147095293002180L;

    private final String name;

    private final CityColorDTO color;

    private Collection<EmporiumDTO> emporiums;

    private final BonusDTO bonuses;

    private final Collection<String> nearCities;

    /**
     * Constructs a CityDTO object
     * @param name is the name string
     * @param color is a CityColorDTO
     * @param bonuses is a BonusDTO
     * @param emporiums is a collection of EmporiumDTO
     * @param nearCities is a collection of strings that identifies the near cities
     * @throws NullPointerException if any of the parameters is null
     * @throws IllegalArgumentException if name is empty
     */
    public CityDTO(String name, CityColorDTO color, BonusDTO bonuses, Collection<EmporiumDTO> emporiums, Collection<String> nearCities) {
        if (name == null || color == null || bonuses == null || emporiums == null || nearCities == null)
            throw new NullPointerException();
        if (name.isEmpty())
            throw new IllegalArgumentException();
        this.name = name;
        this.color = color;
        this.bonuses = bonuses;
        this.emporiums = new LinkedList<>(emporiums);
        this.nearCities = new LinkedList<>(nearCities);
    }

    /**
     * Returns the name of the City
     * @return the name of the City
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the Color dto of the city
     * @return the Color dto of the city
     */
    public CityColorDTO getColor() {
        return color;
    }

    /**
     * Returns a collection of Emporiums DTO
     * @return a collection of Emporiums DTO
     */
    public Collection<EmporiumDTO> getEmporiums() {
        return new LinkedList<>(emporiums);
    }

    /**
     * Sets the emporiums collection
     * @param emporiums is a collection of Emporiums DTO
     * @throws NullPointerException if emporiums is null
     */
    public void setEmporiums(Collection<EmporiumDTO> emporiums) {
		if (emporiums == null)
			throw new NullPointerException();
    	this.emporiums = new LinkedList<>(emporiums);
    }

    /**
     * Returns a collection decorated bonus DTO
     * @return a collection of Bonuses DTO
     */
    public BonusDTO getBonuses() {
        return this.bonuses;
    }

    /**
     * Returns a collection of strings that identifies the near cities
     * @return a collection of strings that identifies the near cities
     */
    public Collection<String> getNearCities() {
        return new LinkedList<>(nearCities);
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) 
        	return true;
        if (o == null || getClass() != o.getClass()) 
        	return false;

        CityDTO cityDTO = (CityDTO) o;

        if (name != null ? !name.equals(cityDTO.name) : cityDTO.name != null) 
        	return false;
        if (color != null ? !color.equals(cityDTO.color) : cityDTO.color != null) 
        	return false;
        if (emporiums != null ? !emporiums.equals(cityDTO.emporiums) : cityDTO.emporiums != null) 
        	return false;
        if (bonuses != null ? !bonuses.equals(cityDTO.bonuses) : cityDTO.bonuses != null) 
        	return false;
        return nearCities != null ? nearCities.equals(cityDTO.nearCities) : cityDTO.nearCities == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (emporiums != null ? emporiums.hashCode() : 0);
        result = 31 * result + (bonuses != null ? bonuses.hashCode() : 0);
        result = 31 * result + (nearCities != null ? nearCities.hashCode() : 0);
        return result;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityDTO cityDTO = (CityDTO) o;

        if (name != null ? !name.equals(cityDTO.name) : cityDTO.name != null) return false;
        return color != null ? color.equals(cityDTO.color) : cityDTO.color == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CityDTO{" +
                "name='" + name + '\'' +
                ", color=" + color +
                ", emporiums=" + emporiums +
                ", bonuses=" + bonuses +
                ", nearCities=" + nearCities +
                '}';
    }
}
