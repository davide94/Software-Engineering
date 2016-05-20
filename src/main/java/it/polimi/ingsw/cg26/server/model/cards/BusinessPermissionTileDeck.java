package it.polimi.ingsw.cg26.server.model.cards;

import it.polimi.ingsw.cg26.common.state.BusinessPermissionTileDeckState;
import it.polimi.ingsw.cg26.common.state.BusinessPermissionTileState;

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
    public BusinessPermissionTileDeck(Collection<BusinessPermissionTile> c) {
        super(c);
    }

    public BusinessPermissionTileDeckState getState() {
        LinkedList<BusinessPermissionTileState> openCardsState = new LinkedList<>();
        for (int i = 0; i < OPEN_CARDS_NUMBER; i++)
            openCardsState.add(this.cards.get(i).getState());
        return new BusinessPermissionTileDeckState(openCardsState);
    }

    /**
     * @return
     */
    public BusinessPermissionTile draw(int which) {
        if (which == 1)
            return this.cards.remove(1);
        return draw();
    }

    /**
     *
     */
    public void change() {
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
