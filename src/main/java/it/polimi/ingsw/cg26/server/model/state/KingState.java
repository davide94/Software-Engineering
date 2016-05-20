package it.polimi.ingsw.cg26.server.model.state;

/**
 *
 */
public class KingState {

    private String currentCity;

    public KingState(String currentCity) {
        this.currentCity = currentCity;
    }

    @Override
    public String toString() {
        return "KingState{" +
                "currentCity='" + currentCity + '\'' +
                '}';
    }
}
