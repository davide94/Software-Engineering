package it.polimi.ingsw.cg26.server.model.cards;

        import it.polimi.ingsw.cg26.server.model.market.Sellable;

/**
 *
 */
public class PoliticCard extends Sellable {

    private final PoliticColor color;

    /**
     *
     */
    public PoliticCard(PoliticColor color) {
        if (color == null)
            throw new NullPointerException();
        this.color = color;
    }

    /**
     * @return
     */
    public PoliticColor getColor() {
        return this.color;
    }

    /**
     *
     */
    public void sell() {
        // TODO implement here
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        return color != null ? color.hashCode() : 0;
    }
}