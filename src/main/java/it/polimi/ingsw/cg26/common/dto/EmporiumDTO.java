package it.polimi.ingsw.cg26.common.dto;

import java.io.Serializable;

/**
 *
 */
public class EmporiumDTO implements Serializable {

    private static final long serialVersionUID = -5222349655829280833L;

    private String player;

    /**
     * Constructs an Emporium DTO object
     * @param player is a String that identifies a Player
     * @throws NullPointerException if player is null
     * @throws IllegalArgumentException if player is empty
     */
    public EmporiumDTO(String player) {
        if (player.isEmpty())
            throw new IllegalArgumentException();
        this.player = player;
    }

    /**
     * Returns a String that identifies a Player
     * @return a String that identifies a Player
     */
    public String getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return "EmporiumDTO{" +
                "player='" + player + '\'' +
                '}';
    }
}
