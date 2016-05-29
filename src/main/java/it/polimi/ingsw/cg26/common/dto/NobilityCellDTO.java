package it.polimi.ingsw.cg26.common.dto;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 */
public class NobilityCellDTO implements Serializable {

    private static final long serialVersionUID = -2718711005606269991L;

    private int index;

    private RewardTileDTO reward;

    /**
     * Constructs a Nobility Cell DTO object
     * @param index is the index of the cell in the Nobility Track
     * @param reward is a RewardTileDTO
     * @throws NullPointerException if bonuses is null
     * @throws IllegalArgumentException if index is negative
     */
    public NobilityCellDTO(int index, RewardTileDTO reward) {
        if (reward == null)
            throw new NullPointerException();
        if (index < 0)
            throw new IllegalArgumentException();
        this.index = index;
        this.reward = reward;
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
        return reward.getBonuses();
    }

    @Override
    public String toString() {
        return "NobilityCellDTO{" +
                "index=" + index +
                ", reward=" + reward +
                '}';
    }
}
