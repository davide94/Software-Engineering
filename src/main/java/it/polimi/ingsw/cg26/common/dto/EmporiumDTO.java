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
    private EmporiumDTO(String player) {
        this.player = player;
    }

    public static EmporiumDTO createEmporium(String player) {
        if (player.isEmpty())
            throw new IllegalArgumentException();
        return new EmporiumDTO(player);
    }

    public static EmporiumDTO createPlaceholderEmporium() {
        return new EmporiumDTO(null);
    }

	public boolean isPlaceholder() {
        return player == null;
    }

    /**
     * Returns a String that identifies a Player
     * @return a String that identifies a Player
     */
    public String getPlayer() {
        return player != null ? player : "placeholder";
    }

    public boolean belongsTo(PlayerDTO p) {
        return player != null && player.equals(p.getName());
    }

    @Override
    public String toString() {
        return "EmporiumDTO{" +
                "player='" + getPlayer() + '\'' +
                '}';
    }
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmporiumDTO other = (EmporiumDTO) obj;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		return true;
	}
}
