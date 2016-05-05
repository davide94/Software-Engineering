package it.polimi.ingsw.cg26.model.cards;

import java.util.Collection;

/**
 *
 */
public class BusinessPermissionTileDeck extends Deck<BusinessPermissionTile> {

    /**
     * @return
     */
    public synchronized BusinessPermissionTile drawSecond() {
        // TODO if it's only one card in the deck?
        return draw(1);
    }
}
