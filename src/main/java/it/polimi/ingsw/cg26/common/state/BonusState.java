package it.polimi.ingsw.cg26.common.state;

import java.io.Serializable;

/**
 *
 */
public class BonusState implements Serializable {

    private static final long serialVersionUID = 2736328090252332859L;

    private String name;

    public BonusState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "BonusState{" +
                "name='" + name + '\'' +
                '}';
    }
}
