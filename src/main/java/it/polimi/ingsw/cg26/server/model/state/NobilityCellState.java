package it.polimi.ingsw.cg26.server.model.state;

import it.polimi.ingsw.cg26.server.model.bonus.Bonus;

import java.util.Collection;

/**
 *
 */
public class NobilityCellState {

    private int index;

    private Collection<BonusState> bonuses;

    public NobilityCellState(int index, Collection<BonusState> bonuses) {
        this.index = index;
        this.bonuses = bonuses;
    }
}
