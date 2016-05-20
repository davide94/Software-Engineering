package it.polimi.ingsw.cg26.server.model.state;

import java.util.Collection;

/**
 *
 */
public class RegionState {

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
}
