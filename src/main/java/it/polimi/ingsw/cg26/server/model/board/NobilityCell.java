package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.server.model.player.Player;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.common.state.BonusState;
import it.polimi.ingsw.cg26.common.state.NobilityCellState;

import java.util.Collection;
import java.util.LinkedList;

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
    private NobilityCell(int index, NobilityCell next, Collection<Bonus> bonuses) {
        if (index < 0)
            throw new IllegalArgumentException();
        if (bonuses == null)
            throw new NullPointerException();
        this.index = index;
        this.next = next;
        this.bonuses = bonuses;
    }

    public static NobilityCell createNobilityCell(int index, NobilityCell next, Collection<Bonus> bonuses) {
        return new NobilityCell(index, next, new LinkedList<>(bonuses));
    }

    public NobilityCellState getState() {
        Collection<BonusState> bonusesState = new LinkedList<>();
        for (Bonus b: bonuses)
            bonusesState.add(b.getState());
        return new NobilityCellState(index, bonusesState);
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

    public void apply(Player player) {
        for (Bonus bonus: this.bonuses)
            bonus.apply(player);
    }

}