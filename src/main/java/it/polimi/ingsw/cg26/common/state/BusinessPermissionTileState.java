package it.polimi.ingsw.cg26.common.state;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

/**
 *
 */
public class BusinessPermissionTileState extends SellableState implements Serializable {

    private static final long serialVersionUID = 7399538004113207286L;

    private Collection<String> cities;

    private Collection<BonusState> bonuses;

    public BusinessPermissionTileState(Collection<String> cities, Collection<BonusState> bonuses, int price, String owner) {
        super(price, owner);
    	if (cities == null || bonuses == null)
            throw new NullPointerException();
        this.cities = new LinkedList<>(cities);
        this.bonuses = new LinkedList<>(bonuses);
    }

    public Collection<String> getCities() {
        return cities;
    }

    public Collection<BonusState> getBonuses() {
        return bonuses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessPermissionTileState that = (BusinessPermissionTileState) o;

        if (cities != null ? !cities.equals(that.cities) : that.cities != null) return false;
        return bonuses != null ? bonuses.equals(that.bonuses) : that.bonuses == null;

    }

    @Override
    public int hashCode() {
        int result = cities != null ? cities.hashCode() : 0;
        result = 31 * result + (bonuses != null ? bonuses.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BusinessPermissionTileState{" +
                "cities=" + cities +
                ", bonuses=" + bonuses +
                '}';
    }
}
