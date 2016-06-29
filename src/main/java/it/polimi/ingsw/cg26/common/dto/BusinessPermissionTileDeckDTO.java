package it.polimi.ingsw.cg26.common.dto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class BusinessPermissionTileDeckDTO implements Serializable {

    private static final long serialVersionUID = -6457819279356175722L;

    private List<BusinessPermissionTileDTO> openCards;

    /**
     * Constructs a Business Permit Tile Deck DTO object
     * @param openCards is a list of business permit tiles DTO (open cards of the deck)
     * @throws NullPointerException if openCards is null
     */
    public BusinessPermissionTileDeckDTO(List<BusinessPermissionTileDTO> openCards) {
        if (openCards == null)
            throw new NullPointerException();
        this.openCards = new LinkedList<>(openCards);
    }

    /**
     * Returns a list of business permit tiles DTO (open cards of the deck)
     * @return a list of business permit tiles DTO (open cards of the deck)
     */
    public List<BusinessPermissionTileDTO> getOpenCards() {
        return new LinkedList<>(openCards);
    }

    @Override
    public String toString() {
        return "BusinessPermissionTileDeckDTO{" +
                "openCards=" + openCards +
                '}';
    }
}
