package it.polimi.ingsw.cg26.common.state;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class BusinessPermissionTileDeckState implements Serializable {

    private static final long serialVersionUID = -6457819279356175722L;

    private List<BusinessPermissionTileState> openCards;

    public BusinessPermissionTileDeckState(List<BusinessPermissionTileState> openCards) {
        if (openCards == null)
            throw new NullPointerException();
        this.openCards = openCards;
    }

    public List<BusinessPermissionTileState> getOpenCards() {
        return openCards;
    }

    @Override
    public String toString() {
        return "BusinessPermissionTileDeckState{" +
                "openCards=" + openCards +
                '}';
    }
}
