package it.polimi.ingsw.cg26.server.model.cards;

        import it.polimi.ingsw.cg26.common.state.PoliticCardState;
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

    @Override
    public PoliticCardState getState() {
        return new PoliticCardState(color.getState(), this.getPrice(), this.getOwner().getState());
    }

    /**
     * @return
     */
    public PoliticColor getColor() {
        return this.color;
    }

    @Override
    public void backToOwner() {
    	this.getOwner().addPoliticCard(this);
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