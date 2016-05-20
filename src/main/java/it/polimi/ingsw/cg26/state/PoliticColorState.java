package it.polimi.ingsw.cg26.state;

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
}
