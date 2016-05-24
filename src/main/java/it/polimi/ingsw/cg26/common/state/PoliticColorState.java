package it.polimi.ingsw.cg26.common.state;

import java.io.Serializable;

/**
 *
 */
public class PoliticColorState implements Serializable {

    private static final long serialVersionUID = -1850240040714737462L;

    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30;40;1m";
    public static final String RED = "\u001B[31;40;1m";
    public static final String GREEN = "\u001B[32;40;1m";
    public static final String YELLOW = "\u001B[33;40;1m";
    public static final String BLUE = "\u001B[34;40;1m";
    public static final String PURPLE = "\u001B[35;40;1m";
    public static final String CYAN = "\u001B[36;40;1m";
    public static final String WHITE = "\u001B[37;40;1m";
    private String color;

    private String ansiString = "\u001B[255;85;85";

    public PoliticColorState(String color) {
        if (color == null)
            throw new NullPointerException();
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

    public String getColor() {
        return color;
    }

    public String getColoredColor() {
        if (color.equals("multicolor"))
            return "\u001B[31mm\u001B[32mu\u001B[33ml\u001B[34mt\u001B[35mi\u001B[36mc\u001B[37mo\u001B[38ml\u001B[31mo\u001B[32mr\u001B[0m";
        return ansiString + color + RESET;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PoliticColorState that = (PoliticColorState) o;

        return color != null ? color.equals(that.color) : that.color == null;

    }

    @Override
    public int hashCode() {
        return color != null ? color.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "PoliticColorState{" +
                "color='" + color + '\'' +
                '}';
    }

}
