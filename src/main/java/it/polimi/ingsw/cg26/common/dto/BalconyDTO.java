package it.polimi.ingsw.cg26.common.dto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class BalconyDTO implements Serializable {

    private static final long serialVersionUID = 1564432177666855654L;

    private List<CouncillorDTO> councillors;

    /**
     * Constructs a Balcony DTO object
     * @param councillors is a list of councillors in the balcony
     * @throws NullPointerException if councillors is null
     */
    public BalconyDTO(List<CouncillorDTO> councillors) {
        if (councillors == null)
            throw new NullPointerException();
        this.councillors = new LinkedList<>(councillors);
    }

    /**
     * Returns a list of councillors
     * @return a list of councillors
     */
    public List<CouncillorDTO> getCouncillors() {
        return new LinkedList<>(councillors);
    }

    @Override
    public String toString() {
        return "BalconyDTO{" +
                "councillors=" + councillors +
                '}';
    }
}
