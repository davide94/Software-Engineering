package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.player.Player;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.common.dto.NobilityCellDTO;

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
    private Bonus bonuses;


    /**
     * Default constructor
     */
    private NobilityCell(int index, NobilityCell next, Bonus bonuses) {
        if (index < 0)
            throw new IllegalArgumentException();
        if (bonuses == null)
            throw new NullPointerException();
        this.index = index;
        this.next = next;
        this.bonuses = bonuses;
    }

    public static NobilityCell createNobilityCell(int index, NobilityCell next, Bonus bonuses) {
        return new NobilityCell(index, next, bonuses);
    }

    public NobilityCellDTO getState() {

        return new NobilityCellDTO(this.index, this.bonuses.getState());
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

    public void apply(Player player) throws NoRemainingCardsException {
        bonuses.apply(player);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) 
        	return true;
        if (o == null || getClass() != o.getClass()) 
        	return false;

        NobilityCell that = (NobilityCell) o;

        if (index != that.index) 
        	return false;
        if (next != null ? !next.equals(that.next) : that.next != null) 
        	return false;
        return bonuses != null ? bonuses.equals(that.bonuses) : that.bonuses == null;

    }

    @Override
    public int hashCode() {
        int result = index;
        result = 31 * result + (next != null ? next.hashCode() : 0);
        result = 31 * result + (bonuses != null ? bonuses.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NobilityCell{" +
                "index=" + index +
                ", bonuses=" + bonuses +
                ", next=" + next +
                '}';
    }
}