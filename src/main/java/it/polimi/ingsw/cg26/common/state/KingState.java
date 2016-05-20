package it.polimi.ingsw.cg26.common.state;

import java.io.Serializable;

/**
 *
 */
public class KingState implements Serializable {

    private static final long serialVersionUID = 4923580451344651682L;

    private String currentCity;

    public KingState(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    @Override
    public String toString() {
        return "KingState{" +
                "currentCity='" + currentCity + '\'' +
                '}';
    }
}
