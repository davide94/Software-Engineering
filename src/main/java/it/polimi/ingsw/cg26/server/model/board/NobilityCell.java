package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.common.dto.NobilityCellDTO;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.player.Player;


public class NobilityCell {

    /**
     * the index of the cell 
     */
    private int index;

    /**
     *the next nobility cell
     */
    private NobilityCell next;

    /**
     *the bonuses on the cell
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

    /**
     * Create a nobility cell
     * @param index is the index of the cell
     * @param next is the next nobility cell
     * @param bonuses are the bonuses on the cell
     * @return a new nobility cell
     */
    public static NobilityCell createNobilityCell(int index, NobilityCell next, Bonus bonuses) {
        return new NobilityCell(index, next, bonuses);
    }

    /**
     * Create a nobility cell DTO
     * @return the DTO of the nobility cell
     */
    public NobilityCellDTO getState() {

        return new NobilityCellDTO(this.index, this.bonuses.getState());
    }

    /**
     * Get the following nobility cell
     * @return the following nobility cell
     */
    public NobilityCell next() {
        return this.next;
    }

    /**
     * Check if there is another cell after the current cell 
     * @return true if the current cell has a following nobility cell else false 
     */
    public Boolean hasNext() {
        return this.next != null;
    }

    /**
     *Get the index of the cell
     * @return the index of the cell
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Apply the bonuses on the cell to the player that is on it
     * @param player is the player on the nobility cell
     * @throws NoRemainingCardsException if there aren't enough cards in the politic cards deck
     */
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