package it.polimi.ingsw.cg26.server.model.state;

/**
 *
 */
public class CityColorState {

    private String color;

    private CityColorState(String color) {
        if (color == null)
            throw new NullPointerException();
        this.color = color;
    }

    @Override
    public String toString() {
        return "CityColorState{" +
                "color='" + color + '\'' +
                '}';
    }
}
