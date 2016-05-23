package it.polimi.ingsw.cg26.common.state;

import java.io.Serializable;

/**
 *
 */
public class PoliticColorState implements Serializable {

    private static final long serialVersionUID = -1850240040714737462L;

    private String color;

    public PoliticColorState(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PoliticColorState that = (PoliticColorState) o;

        return color != null ? color.equals(that.color) : that.color == null;

    }

    @Override
    public int hashCode() {
        return color != null ? color.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "PoliticColorState{" +
                "color='" + color + '\'' +
                '}';
    }

}
