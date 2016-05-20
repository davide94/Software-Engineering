package it.polimi.ingsw.cg26.server.model.state;

import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;

import java.util.List;

/**
 *
 */
public class BusinessPermissionTileDeckState {

    private List<BusinessPermissionTileState> openCards;

    public BusinessPermissionTileDeckState(List<BusinessPermissionTileState> openCards) {
        this.openCards = openCards;
    }
}
