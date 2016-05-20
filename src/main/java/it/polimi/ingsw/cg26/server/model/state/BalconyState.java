package it.polimi.ingsw.cg26.server.model.state;

import it.polimi.ingsw.cg26.server.model.board.Councillor;

import java.util.List;

/**
 *
 */
public class BalconyState {

    private List<Councillor> councillors;

    public BalconyState(List<Councillor> councillors) {
        this.councillors = councillors;
    }

}
