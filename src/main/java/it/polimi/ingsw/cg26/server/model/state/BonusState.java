package it.polimi.ingsw.cg26.server.model.state;

/**
 *
 */
public class BonusState {

    private String name;

    public BonusState(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BonusState{" +
                "name='" + name + '\'' +
                '}';
    }
}
