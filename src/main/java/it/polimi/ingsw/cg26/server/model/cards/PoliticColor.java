package it.polimi.ingsw.cg26.server.model.cards;

import it.polimi.ingsw.cg26.common.dto.PoliticColorDTO;

/**
 * 
 */
public class PoliticColor {

    /**
     * The name of the color
     */
    private String color;

    /**
     * Creates a Color
     */
    public PoliticColor(String color) {
        if (color == null)
            throw new NullPointerException();
        this.color = color;
    }

    /**
     * Generate the dto of the color
     * @return the dto of the color
     */
    public PoliticColorDTO getState() {
        return new PoliticColorDTO(color);
    }

    /**
     * Returns the String name of the color
     * @return the String name of the color
     */
    public String colorString() {
        return this.color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        PoliticColor that = (PoliticColor) o;

        return color != null ? color.equalsIgnoreCase(that.color) : that.color == null;

    }

    @Override
    public int hashCode() {
        return color != null ? color.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "PoliticColor{" +
                "color='" + color + '\'' +
                '}';
    }
}