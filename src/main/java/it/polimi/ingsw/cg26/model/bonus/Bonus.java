package it.polimi.ingsw.cg26.model.bonus;

import it.polimi.ingsw.cg26.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.model.player.Player;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 
 */
public abstract class Bonus {

    /**
     * Default constructor
     */
    public Bonus() {
    }

    /**
     * 
     */
    private int multiplicity;

    /**
     * @param to
     */
    public abstract void apply(Player to);

}