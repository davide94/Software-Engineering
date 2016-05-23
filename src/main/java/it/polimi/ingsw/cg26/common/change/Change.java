package it.polimi.ingsw.cg26.common.change;

import java.io.Serializable;

import it.polimi.ingsw.cg26.common.state.BoardState;

/**
 *
 */
public interface Change extends Serializable {

	void apply(BoardState gameBoardState);
}
