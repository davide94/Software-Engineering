package it.polimi.ingsw.cg26.model.cards;

/**
 * 
 */
public class PoliticColor {

    /**
     *
     */
    private String color;

    /**
     *
     */
    public PoliticColor(String color) {
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

        PoliticColor that = (PoliticColor) o;

        return color != null ? color.equalsIgnoreCase(that.color) : that.color == null;

    }

    @Override
    public int hashCode() {
        return color != null ? color.hashCode() : 0;
    }
}