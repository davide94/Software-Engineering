package it.polimi.ingsw.cg26.common.state;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

/**
 *
 */
public class BusinessPermissionTileState implements Serializable {

    private static final long serialVersionUID = 7399538004113207286L;

    private Collection<String> cities;

    private Collection<BonusState> bonuses;

    public BusinessPermissionTileState(Collection<String> cities, Collection<BonusState> bonuses) {
        if (cities == null || bonuses == null)
            throw new NullPointerException();
        this.cities = new LinkedList<>(cities);
        this.bonuses = new LinkedList<>(bonuses);
    }

    @Override
    public String toString() {
        return "BusinessPermissionTileState{" +
                "cities=" + cities +
                ", bonuses=" + bonuses +
                '}';
    }
}