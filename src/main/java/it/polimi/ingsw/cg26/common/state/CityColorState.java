package it.polimi.ingsw.cg26.common.state;

import java.io.Serializable;

/**
 *
 */
public class CityColorState implements Serializable {

    private static final long serialVersionUID = 1178748348875116351L;

    private String color;

    public CityColorState(String color) {
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
