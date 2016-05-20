package it.polimi.ingsw.cg26.state;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 */
public class NobilityCellState implements Serializable {

    private static final long serialVersionUID = -2718711005606269991L;

    private int index;

    private Collection<BonusState> bonuses;

    public NobilityCellState(int index, Collection<BonusState> bonuses) {
        this.index = index;
        this.bonuses = bonuses;
    }

    @Override
    public String toString() {
        return "NobilityCellState{" +
                "index=" + index +
                ", bonuses=" + bonuses +
                '}';
    }
}
