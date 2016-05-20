package it.polimi.ingsw.cg26.common.state;

import java.io.Serializable;

/**
 *
 */
public class EmporiumState implements Serializable {

    private static final long serialVersionUID = -5222349655829280833L;

    private String player;

    public EmporiumState(String player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "EmporiumState{" +
                "player='" + player + '\'' +
                '}';
    }
}
