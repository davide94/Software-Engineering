package it.polimi.ingsw.cg26.server.model.state;

import java.util.List;

/**
 *
 */
public class BusinessPermissionTileDeckState {

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
