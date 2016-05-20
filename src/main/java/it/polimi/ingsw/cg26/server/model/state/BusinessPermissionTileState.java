package it.polimi.ingsw.cg26.server.model.state;

import java.util.Collection;
import java.util.LinkedList;

/**
 *
 */
public class BusinessPermissionTileState {

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
