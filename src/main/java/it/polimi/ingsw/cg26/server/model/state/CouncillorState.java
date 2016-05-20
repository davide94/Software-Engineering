package it.polimi.ingsw.cg26.server.model.state;


/**
 *
 */
public class CouncillorState {

    private PoliticColorState color;

    /**
     * Default constructor
     */
    private CouncillorState(PoliticColorState color) {
        if (color == null)
            throw new NullPointerException();
        this.color = color;
    }
}
