package it.polimi.ingsw.cg26.model.cards;

        import it.polimi.ingsw.cg26.model.player.Player;
        import it.polimi.ingsw.cg26.model.market.Sellable;

/**
 *
 */
public class PoliticCard extends Sellable {

    private final CouncillorColor color;

    /**
     *
     */
    public PoliticCard(CouncillorColor color) {
        if (color == null)
            throw new NullPointerException();
        this.color = color;
    }

    /**
     * @return
     */
    public CouncillorColor getColor() {
        return this.color;
    }

    /**
     *
     */
    public void Sell() {
        // TODO implement here
    }

}