package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.model.cards.Councillor;
import it.polimi.ingsw.cg26.model.player.Player;

import java.util.Set;

/**
 * 
 */
public class Region {

    /**
     * 
     */
    private String name;

    /**
     * @param
     */
    public void Region() {
        // TODO implement here
    }

    /**
     * @param
     * @return
     */
    public City getCity(String city) {
        // TODO implement here
        return null;
    }

    /**
     * @param councillor
     */
    public void elect(Councillor councillor) {
        // TODO implement here
    }

    /**
     * @param
     * @param city 
     * @return
     */
    public Boolean build(Player p, String city) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Set<City> getCities() {
        // TODO implement here
        return null;
    }

}