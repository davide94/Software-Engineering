package it.polimi.ingsw.cg26.server.model.state;

import it.polimi.ingsw.cg26.server.model.board.NobilityCell;

import java.util.List;

/**
 *
 */
public class NobilityTrackState {

    private List<NobilityCellState> cellsState;

    public NobilityTrackState(List<NobilityCellState> cellsState) {
        this.cellsState = cellsState;
    }
}
