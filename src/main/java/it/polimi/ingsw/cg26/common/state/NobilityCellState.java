package it.polimi.ingsw.cg26.common.state;

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
        if (bonuses == null)
            throw new NullPointerException();
        if (index < 0)
            throw new IllegalArgumentException();
        this.index = index;
        this.bonuses = bonuses;
    }

    public int getIndex() {
        return index;
    }

    public Collection<BonusState> getBonuses() {
        return bonuses;
    }

    @Override
    public String toString() {
        return "NobilityCellState{" +
                "index=" + index +
                ", bonuses=" + bonuses +
                '}';
    }
}
