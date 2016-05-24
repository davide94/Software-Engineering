package it.polimi.ingsw.cg26.common.state;

import java.io.Serializable;

/**
 *
 */
public class PoliticCardState extends SellableState implements Serializable {

    private static final long serialVersionUID = -8556202630984580045L;

    private final PoliticColorState color;

    public PoliticCardState(PoliticColorState color, int price, PlayerState owner) {
    	super(price, owner);
        this.color = color;
    }

    public PoliticColorState getColor() {
        return color;
    }

}
