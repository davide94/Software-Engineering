package it.polimi.ingsw.cg26.common.dto;

import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

/**
 *
 */
public class BusinessPermissionTileDTO extends SellableDTO implements Serializable {

    private static final long serialVersionUID = 7399538004113207286L;

    private Collection<String> cities;

    private BonusDTO bonuses;

    /**
     * Constructs a  Business Permit Tile DTO object
     * @param cities is a collection of names of the Cities where the tile can build
     * @param reward is RewardTileDTO DTO
     * @param price is the price of the tile if the tile is in the store
     * @param owner is a string that identifies the player who owns the tile if the tile is in the store
     * @throws NullPointerException if cities or bonuses are null
     */
    public BusinessPermissionTileDTO(Collection<String> cities, BonusDTO bonuses, int price, String owner) {
        super(price, owner);
    	if (cities == null || bonuses == null)
            throw new NullPointerException();
        this.cities = new LinkedList<>(cities);
        this.bonuses = bonuses;
    }

    /**
     * Returns a collection of names of the cities where the tile can build
     * @return a collection of names of the cities where the tile can build
     */
    public Collection<String> getCities() {
        return cities;
    }

    /**
     * Returns a Reward Tile DTO
     * @return a Reward Tile DTO
     */
    public BonusDTO getBonuses() {
        return bonuses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) 
        	return true;
        if (o == null || getClass() != o.getClass()) 
        	return false;
        if (!super.equals(o)) 
        	return false;

        BusinessPermissionTileDTO that = (BusinessPermissionTileDTO) o;

        if (cities != null ? !cities.equals(that.cities) : that.cities != null) 
        	return false;
        return bonuses != null ? bonuses.equals(that.bonuses) : that.bonuses == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (cities != null ? cities.hashCode() : 0);
        result = 31 * result + (bonuses != null ? bonuses.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BusinessPermissionTileDTO{" +
                "cities=" + cities +
                ", bonuses=" + bonuses +
                '}';
    }
}
