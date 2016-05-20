package it.polimi.ingsw.cg26.common.state;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 */
public class RegionState implements Serializable {

    private static final long serialVersionUID = 2260796972775485470L;

    private String name;

    private Collection<CityState> cities;

    private BalconyState balcony;

    private BusinessPermissionTileDeckState deck;

    private Collection<BonusState> bonus;

    public RegionState(String name, Collection<CityState> cities, BusinessPermissionTileDeckState deck, BalconyState balcony, Collection<BonusState> bonus) {
        if (name == null || cities == null || deck == null || balcony == null || bonus == null)
            throw new NullPointerException();
        this.name = name;
        this.cities = cities;
        this.deck = deck;
        this.balcony = balcony;
        this.bonus = bonus;
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
}
