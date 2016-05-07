package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.model.bonus.Bonus;
import it.polimi.ingsw.cg26.model.cards.BusinessPermissionTileDeck;
import it.polimi.ingsw.cg26.model.cards.Councillor;
import it.polimi.ingsw.cg26.model.player.Player;

import java.util.Collection;
import java.util.Set;

/**
 * 
 */
public class Region {

    /**
     * 
     */
    private String name;

    public Region(Collection<City> cities, BusinessPermissionTileDeck deck, Balcony balcony, Bonus bonus) {
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
    public Councillor elect(Councillor councillor) {
        // TODO implement here
    	return null;
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

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}