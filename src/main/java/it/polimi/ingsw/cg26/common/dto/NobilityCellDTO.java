package it.polimi.ingsw.cg26.common.dto;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 */
public class NobilityCellDTO implements Serializable {

    private static final long serialVersionUID = -2718711005606269991L;

    private int index;

    private Collection<BonusDTO> bonuses;

    /**
     * Constructs a Nobility Cell DTO object
     * @param index is the index of the cell in the Nobility Track
     * @param bonuses is a collection of Bonuses DTO
     * @throws NullPointerException if bonuses is null
     * @throws IllegalArgumentException if index is negative
     */
    public NobilityCellDTO(int index, Collection<BonusDTO> bonuses) {
        if (bonuses == null)
            throw new NullPointerException();
        if (index < 0)
            throw new IllegalArgumentException();
        this.index = index;
        this.bonuses = bonuses;
    }

    /**
     * Returns the index of the Cell
     * @return the index of the Cell
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns a collection of Bonuses DTO
     * @return a collection of Bonuses DTO
     */
    public Collection<BonusDTO> getBonuses() {
        return bonuses;
    }

    @Override
    public String toString() {
        return "NobilityCellDTO{" +
                "index=" + index +
                ", bonuses=" + bonuses +
                '}';
    }
}
