package it.polimi.ingsw.cg26.common.dto;

import java.io.Serializable;

/**
 *
 */
public class PoliticColorDTO implements Serializable {

    private static final long serialVersionUID = -1850240040714737462L;

    private String color;

    /**
     * Constructs a Politic Color DTO object
     * @param color is the color name string
     * @throws NullPointerException if color is null
     * @throws IllegalArgumentException if color is empty
     */
    public PoliticColorDTO(String color) {
        if (color.isEmpty())
            throw new IllegalArgumentException();
        this.color = color;
    }

    /**
     * Returns the color name string
     * @return the color name string
     */
    public String getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PoliticColorDTO that = (PoliticColorDTO) o;

        return color != null ? color.equals(that.color) : that.color == null;

    }

    @Override
    public int hashCode() {
        return color != null ? color.hashCode() : 0;
    }

    /**
     * Returns the color name string
     * @return the color name string
     */
    @Override
    public String toString() {
        return color;
    }
}
