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

	void apply(GameBoardDTO gameGameBoardDTO);
}
