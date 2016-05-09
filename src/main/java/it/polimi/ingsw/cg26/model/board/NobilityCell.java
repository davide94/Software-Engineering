package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.model.bonus.*;

import java.util.Collection;

/**
 * 
 */
public class NobilityCell {

    /**
     *
     */
    private int index;

    /**
     *
     */
    private NobilityCell next;

    /**
     *
     */
    private Collection<Bonus> bonuses;


    /**
     * Default constructor
     */
    public NobilityCell(int index, NobilityCell next, Collection<Bonus> bonuses) {
        if (index < 0)
            throw new IllegalArgumentException();
        if (bonuses == null)
            throw new NullPointerException();
        this.index = index;
        this.next = next;
    	this.bonuses = bonuses;
    }

    /**
     * @return
     */
    public NobilityCell next() {
        return this.next;
    }

    /**
     * @return
     */
    public Boolean hasNext() {
        return this.next != null;
    }

    /**
     *
     * @return
     */
    public int getIndex() {
        return this.index;
    }

}