package it.polimi.ingsw.cg26.common.dto;


import java.io.Serializable;

/**
 *
 */
public class CouncillorDTO implements Serializable {

    private static final long serialVersionUID = -5217378439988221876L;

    private PoliticColorDTO color;

    /**
     * Constructs a Councillor DTO object
     * @param color is a Politic Color DTO
     * @throws NullPointerException if color is null
     */
    public CouncillorDTO(PoliticColorDTO color) {
        if (color == null)
            throw new NullPointerException();
        this.color = color;
    }

    /**
     * Returns a Politic Color DTO
     * @return a Politic Color DTO
     */
    public PoliticColorDTO getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CouncillorDTO that = (CouncillorDTO) o;

        return color != null ? color.equals(that.color) : that.color == null;

    }

    @Override
    public int hashCode() {
        return color != null ? color.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CouncillorDTO{" +
                "color=" + color +
                '}';
    }
}
