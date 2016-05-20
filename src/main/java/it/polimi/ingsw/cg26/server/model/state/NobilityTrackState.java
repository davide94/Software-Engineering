package it.polimi.ingsw.cg26.server.model.state;

import java.util.List;

/**
 *
 */
public class NobilityTrackState {

    private List<NobilityCellState> cellsState;

    public NobilityTrackState(List<NobilityCellState> cellsState) {
        this.cellsState = cellsState;
    }

    @Override
    public String toString() {
        return "NobilityTrackState{" +
                "cellsState=" + cellsState +
                '}';
    }
}
