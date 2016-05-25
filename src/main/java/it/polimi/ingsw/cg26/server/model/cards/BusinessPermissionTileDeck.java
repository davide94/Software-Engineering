package it.polimi.ingsw.cg26.server.model.cards;

import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDeckDTO;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;

import java.util.Collection;
import java.util.LinkedList;

/**
 *
 */
public class BusinessPermissionTileDeck extends Deck<BusinessPermissionTile> {

    /**
     * Number of open cards
     */
    private static final int OPEN_CARDS_NUMBER = 2;

    /**
     * Constructs a Business Permit Tile Deck
     * @param cards is a collection of cards that the deck will contain
     */
    public BusinessPermissionTileDeck(Collection<BusinessPermissionTile> cards) {
        super(cards);
    }

    /**
     * Generates the dto of the Deck
     * @return the dto of the Deck
     */
    public BusinessPermissionTileDeckDTO getState() {
        LinkedList<BusinessPermissionTileDTO> openCardsState = new LinkedList<>();
        for (int i = 0; i < OPEN_CARDS_NUMBER; i++)
            openCardsState.add(this.cards.get(i).getState());
        return new BusinessPermissionTileDeckDTO(openCardsState);
    }

    /**
     * Removes and returns one of the open cards
     * @param position represents which card has to be drawn, 0 is the upper
     * @return one of the open cards
     * @throws IllegalArgumentException if which is negative or greater than the maximum allowed value
     */
    public BusinessPermissionTile draw(int position) {
        if (position < 0 || position >= OPEN_CARDS_NUMBER || position >= cards.size())
            throw new IllegalArgumentException();
        return this.cards.remove(position);
    }

    /**
     * Puts to the bottom of the deck the open cards
     */
    public void change() {
        for (int i = 0; i < OPEN_CARDS_NUMBER; i++)
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
