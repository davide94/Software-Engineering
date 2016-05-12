package it.polimi.ingsw.cg26.model.cards;

        import it.polimi.ingsw.cg26.model.market.Sellable;

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
    public void Sell() {
        // TODO implement here
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return false;
/*        if (o == null || getClass() != o.getClass()) return false;

        PoliticCard that = (PoliticCard) o;

        return color != null ? color.equals(that.color) : that.color == null;
*/
    }

    @Override
    public int hashCode() {
        return color != null ? color.hashCode() : 0;
    }
}