package it.polimi.ingsw.cg26.server.model.cards;

import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDeckDTO;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

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
        for (int i = 0; i < OPEN_CARDS_NUMBER && i < cards.size(); i++)
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
        if (cards.size() >= OPEN_CARDS_NUMBER)
            return this.cards.remove(position);
        BusinessPermissionTile ret = this.cards.get(position);
        this.cards.set(position, this.cards.remove(OPEN_CARDS_NUMBER));
        return ret;
    }

    /**
     * Draw a BPT at random position then shuffle the deck. (for two players match initial configuration)
     * @return a BusinessPermissionTile from the deck
     */
    public BusinessPermissionTile randomCard() {
        BusinessPermissionTile tile = cards.get(new Random().nextInt(cards.size()));
        Collections.shuffle(cards);
        return tile;
    }

    /**
     * Puts to the bottom of the deck the open cards
     * @throws NoRemainingCardsException
     */
    public void change() throws NoRemainingCardsException {
        for (int i = 0; i < OPEN_CARDS_NUMBER; i++)
            add(draw());
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        String ret = "BusinessPermissionTileDeck{";
        for (int i = 0; i < (OPEN_CARDS_NUMBER < cards.size() ? OPEN_CARDS_NUMBER : cards.size()); i++)
            ret+= "openCard" + i + "='" + super.cards.get(i) + "\'";
        return ret + "}";
    }
}
