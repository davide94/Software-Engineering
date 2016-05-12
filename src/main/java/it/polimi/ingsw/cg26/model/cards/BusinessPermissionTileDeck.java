package it.polimi.ingsw.cg26.model.cards;

import java.util.Collection;

/**
 *
 */
public class BusinessPermissionTileDeck extends Deck<BusinessPermissionTile> {

    /**
     *
     */
    public  BusinessPermissionTileDeck(Collection<BusinessPermissionTile> c) {
        super(c);
    }

    /**
     * @return
     */
    public synchronized BusinessPermissionTile draw(int which) {
        if (which == 1)
            super.draw(1);
        return draw();
    }

    /**
     *
     */
    public synchronized void change() {
        add(draw());
        add(draw());
    }
}
