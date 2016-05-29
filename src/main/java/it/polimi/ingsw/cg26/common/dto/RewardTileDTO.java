package it.polimi.ingsw.cg26.common.dto;

import java.io.Serializable;
import java.util.Collection;

public class RewardTileDTO implements Serializable {

	private static final long serialVersionUID = -1029355922042286201L;

	private final Collection<BonusDTO> bonuses;

	/**
	 * Constructs a Reward Tile DTO object
	 * @param bonuses is a collection of Bonuses DTO
	 * @throws NullPointerException if bonuses is null
     */
	public RewardTileDTO(Collection<BonusDTO> bonuses) {
		if (bonuses == null)
			throw new NullPointerException();
		this.bonuses = bonuses;
	}

	/**
	 * Returns a collection of Bonuses DTO
	 * @return a collection of Bonuses DTO
	 */
	public Collection<BonusDTO> getBonuses() {
		return bonuses;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RewardTileDTO that = (RewardTileDTO) o;

		return bonuses != null ? bonuses.equals(that.bonuses) : that.bonuses == null;

	}

	@Override
	public int hashCode() {
		return bonuses != null ? bonuses.hashCode() : 0;
	}
}
