package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.model.bonus.Bonus;
import it.polimi.ingsw.cg26.model.player.Player;

import java.util.*;

/**
 * 
 */
public class City {

    /**
     *
     * @param name
     * @param color
     * @param bonuses
     */
    public City(String name, CityColor color, Collection<Bonus> bonuses) {

    }

    /**
     * 
     */
    private String name = "";

    /**
     * 
     */
    private Set<City> nearCities;

    public String getName() {
        return this.name;
    }

    /**
     * @param
     */
    public void build(Player p) {
        // TODO implement here
    }

    /**
     * @param
     */
    private void takeBonus(Player p) {
        // TODO implement here
    }

    /**
     * @param
     */
    public void distanceFrom(String s) {
        // TODO implement here
    }

}