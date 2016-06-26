package it.polimi.ingsw.cg26.common.dto;

import java.io.Serializable;

/**
 *
 */
public class PoliticColorDTO implements Serializable {

    private static final long serialVersionUID = -1850240040714737462L;

    private String color;

    private String ansiString = "\u001B[255;85;85";

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
        switch (color) {
            case "white":
                ansiString = "\u001B[39m";
                break;
            case "black" :
                ansiString = "\u001B[38m";
                break;
            case "blue":
                ansiString = "\u001B[34m";
                break;
            case "orange":
                ansiString = "\u001B[33m";
                break;
            case "pink":
                ansiString = "\u001B[31m";
                break;
            case "violet":
                ansiString = "\u001B[35m";
                break;
            default:
                ansiString = "";
                break;
        }
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
     * Returns the color name string enriched with ANSI colors
     * @return the color name string enriched with ANSI colors
     */
    @Override
    public String toString() {
        if (color.equals("multicolor"))
            return "\u001B[31mm\u001B[32mu\u001B[33ml\u001B[34mt\u001B[35mi\u001B[36mc\u001B[37mo\u001B[38ml\u001B[31mo\u001B[32mr\u001B[0m";
        return ansiString + color + "\u001B[0m";
    }
}
