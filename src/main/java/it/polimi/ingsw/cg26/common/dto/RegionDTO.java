package it.polimi.ingsw.cg26.common.dto;

import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

/**
 *
 */
public class RegionDTO implements Serializable {

    private static final long serialVersionUID = 2260796972775485470L;

    private final String name;

    private final Collection<CityDTO> cities;

    private BalconyDTO balcony;

    private BusinessPermissionTileDeckDTO deck;

    private final BonusDTO bonus;

	/**
	 * Constructs a Region DTO object
	 * @param name is the name of the Region
	 * @param cities is a Collection of Cities DTO
	 * @param deck is a Business Permit Tile DTO
	 * @param balcony is a Balcony DTO
     * @param bonus is a Reward Tile DTO
	 * @throws NullPointerException if any parameter is null
	 * @throws IllegalArgumentException if name is empty
     */
    public RegionDTO(String name, Collection<CityDTO> cities, BusinessPermissionTileDeckDTO deck, BalconyDTO balcony, BonusDTO bonus) {
        if (name.isEmpty())
			throw new IllegalArgumentException();
		if (cities == null || deck == null || balcony == null || bonus == null)
            throw new NullPointerException();
        this.name = name;
        this.cities = new LinkedList<>(cities);
        this.deck = deck;
        this.balcony = balcony;
        this.bonus = bonus;
    }

	/**
	 * Returns the name of the Region
	 * @return the name of the Region
     */
    public String getName() {
        return name;
    }

	/**
	 * Returns a collection of Cities DTO
	 * @return a collection of Cities DTO
     */
    public Collection<CityDTO> getCities() {
        return new LinkedList<>(cities);
    }

	/**
	 * Returns a Balcony DTO
	 * @return a Balcony DTO
     */
    public BalconyDTO getBalcony() {
        return balcony;
    }

    /**
	 * Sets the Balcony
	 * @param balcony is a Balcony DTO
	 * @throws NullPointerException is balcony is null
     */
    public void setBalcony(BalconyDTO balcony){
		if (balcony == null)
			throw new NullPointerException();
    	this.balcony = balcony;
    }

	/**
	 * Returns a Business Permit Tile Deck DTO
	 * @return a Business Permit Tile Deck DTO
     */
    public BusinessPermissionTileDeckDTO getDeck() {
        return deck;
    }

    /**
	 * Sets the Deck
	 * @param deck is a Business Permit Tile DTO
	 * @throws NullPointerException if deck is null
     */
    public void setDeck(BusinessPermissionTileDeckDTO deck){
		if (deck == null)
			throw new NullPointerException();
    	this.deck = deck;
    }

	/**
	 * Returns a Reward Tile DTO
	 * @return a Reward Tile DTO
     */
    public BonusDTO getBonus() {
        return bonus;
    }

    @Override
    public String toString() {
        return "RegionDTO{" +
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
		RegionDTO other = (RegionDTO) obj;
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
