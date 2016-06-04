package it.polimi.ingsw.cg26.common.change;

import java.io.Serializable;

import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;

/**
 *
 */
@FunctionalInterface
public interface Change extends Serializable {

	default boolean isFor(long token) {
		return true;
	}

	/**
	 * Applies the change to the GameBoard DTO
	 * @param gameGameBoardDTO the GameBoard in which the state will be changed
	 */
	void apply(GameBoardDTO gameGameBoardDTO);
}
