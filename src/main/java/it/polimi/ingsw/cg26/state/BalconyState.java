package it.polimi.ingsw.cg26.state;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class BalconyState implements Serializable {

    private static final long serialVersionUID = 1564432177666855654L;

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
