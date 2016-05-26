package it.polimi.ingsw.cg26.common.change;

import java.io.Serializable;

import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;

/**
 *
 */
@FunctionalInterface
public interface Change extends Serializable {

	void apply(GameBoardDTO gameGameBoardDTO);
}
