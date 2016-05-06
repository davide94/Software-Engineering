package it.polimi.ingsw.cg26.model.cards;

import it.polimi.ingsw.cg26.model.board.City;
import it.polimi.ingsw.cg26.model.bonus.Bonus;
import it.polimi.ingsw.cg26.model.market.Sellable;

import java.util.Collection;
import java.util.LinkedList;

/**
 * 
 */
public class BusinessPermissionTile extends Sellable {

    /**
     *
     */
    private Boolean used; // TODO usiamo questo flag oppure teniamo separate le carte usate e quelle non?

    /**
     *
     */
    private Collection<City> cities;

    /**
     *
     */
    private Collection<Bonus> bonuses;

    /**
     * Default constructor
     */
    public BusinessPermissionTile(Collection<City> cities, Collection<Bonus> bonuses) {
        this.cities = new LinkedList<City>();
        this.cities.addAll(cities);

        this.bonuses = new LinkedList<Bonus>();
        this.bonuses.addAll(bonuses);
    }

    /**
     * @return
     */
    public Collection<City> getCities() {
        Collection<City> c =  new LinkedList<City>();
        c.addAll(this.cities);
        return c;
    }

    /**
     * @return
     */
    public Collection<Bonus> getBonuses() {
        Collection<Bonus> c =  new LinkedList<Bonus>();
        c.addAll(this.bonuses);
        return c;
    }
}