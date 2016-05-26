package it.polimi.ingsw.cg26.common.dto;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class KingDeckDTO implements Serializable {

    private static final long serialVersionUID = -5230368352327280474L;

    private List<RewardTileDTO> tiles;

    /**
     * Constructs a King Deck DTO object
     * @param tiles is a list of Reward Tiles DTO
     * @throws NullPointerException if tiles is null
     */
    public KingDeckDTO(List<RewardTileDTO> tiles) {
        if (tiles == null)
            throw new NullPointerException();
        this.tiles = tiles;
    }

    /**
     * Returns a list of Reward Tiles DTO
     * @return a list of Reward Tiles DTO
     */
    public List<RewardTileDTO> getTiles() {
        return tiles;
    }

    @Override
    public String toString() {
        return "KingDeckDTO{" +
                "tiles=" + tiles +
                '}';
    }
}
