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
            return this.cards.remove(1);
        return draw();
    }

    /**
     *
     */
    public synchronized void change() {
        add(draw());
        add(draw());
    }

    @Override
    public String toString() {
        return "BusinessPermissionTileDeck{" +
                "leftCard='" + super.cards.get(1) + "\'" +
                ", rightCard='" + super.cards.get(0) + "\'" +
                "}";
    }
}
