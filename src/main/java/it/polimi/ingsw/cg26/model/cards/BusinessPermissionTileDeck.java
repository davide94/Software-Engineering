package it.polimi.ingsw.cg26.model.cards;

import java.util.Collection;
import java.util.LinkedList;

/**
 *
 */
public class BusinessPermissionTileDeck extends Deck<BusinessPermissionTile> {

    private static final int OPEN_CARDS_NUMBER = 2;

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

    public Collection<BusinessPermissionTile> getOpenCards() {
        LinkedList<BusinessPermissionTile> cards = new LinkedList<>();
        for (int i = 0; i < OPEN_CARDS_NUMBER; i++)
            cards.add(super.cards.get(i));
        return cards;
    }

    @Override
    public String toString() {
        return "BusinessPermissionTileDeck{" +
                "leftCard='" + super.cards.get(1) + "\'" +
                ", rightCard='" + super.cards.get(0) + "\'" +
                "}";
    }
}
