package it.polimi.ingsw.cg26.common.state;


import java.io.Serializable;

/**
 *
 */
public class CouncillorState implements Serializable {

    private static final long serialVersionUID = -5217378439988221876L;

    private PoliticColorState color;

    public CouncillorState(PoliticColorState color) {
        if (color == null)
            throw new NullPointerException();
        this.color = color;
    }

    public PoliticColorState getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "CouncillorState{" +
                "color=" + color +
                '}';
    }
}
