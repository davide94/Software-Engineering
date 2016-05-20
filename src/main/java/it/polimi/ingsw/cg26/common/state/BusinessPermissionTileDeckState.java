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
        this.openCards = openCards;
    }

    @Override
    public String toString() {
        return "BusinessPermissionTileDeckState{" +
                "openCards=" + openCards +
                '}';
    }
}
