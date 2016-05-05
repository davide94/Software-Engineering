package it.polimi.ingsw.cg26.model.cards;

/**
 * 
 */
public class CouncillorColor {

    /**
     *
     */
    private String color;

    /**
     *
     */
    public CouncillorColor(String color) {
        this.color = color;
    }

    /**
     * @return
     */
    public String colorString() {
        return this.color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CouncillorColor that = (CouncillorColor) o;

        return color != null ? color.equals(that.color) : that.color == null;

    }

    @Override
    public int hashCode() {
        return color != null ? color.hashCode() : 0;
    }
}