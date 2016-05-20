package it.polimi.ingsw.cg26.server.model.state;

import java.util.List;

/**
 *
 */
public class BalconyState {

    private List<CouncillorState> councillors;

    public BalconyState(List<CouncillorState> councillors) {
        this.councillors = councillors;
    }

    @Override
    public String toString() {
        return "BalconyState{" +
                "councillors=" + councillors +
                '}';
    }
}
