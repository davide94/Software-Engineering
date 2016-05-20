package it.polimi.ingsw.cg26.common.state;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class NobilityTrackState implements Serializable {

    private static final long serialVersionUID = -3226153742652964326L;

    private List<NobilityCellState> cellsState;

    public NobilityTrackState(List<NobilityCellState> cellsState) {
        this.cellsState = cellsState;
    }

    public List<NobilityCellState> getCellsState() {
        return cellsState;
    }

    @Override
    public String toString() {
        return "NobilityTrackState{" +
                "cellsState=" + cellsState +
                '}';
    }
}
