package it.polimi.ingsw.cg26.server.model.cards;

import it.polimi.ingsw.cg26.common.dto.KingDeckDTO;
import it.polimi.ingsw.cg26.common.dto.RewardTileDTO;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class KingDeck extends Deck<RewardTile> {

    /**
     * Constructs a deck of RewardTiles with a collection of Reward Tiles that will be contained
     * @param c is a collection of reward tiles that the deck will contain, if c is a list, the order will be preserved
     */
    public  KingDeck(Collection<RewardTile> c) {
        super(c);
    }

    /**
     * Generates the dto of the Deck
     * @return the dto of the deck
     */
    public KingDeckDTO getState() {
        List<RewardTileDTO> tileState = this.cards.stream()
                .map(RewardTile::getState)
                .collect(Collectors.toCollection(LinkedList::new));
        return new KingDeckDTO(tileState);
    }

    @Override
    public RewardTile draw() throws NoRemainingCardsException {
        return super.draw();
    }

    @Override
    public boolean hasNext() {
        return super.hasNext();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deck<?> deck = (Deck<?>) o;

        return cards != null ? cards.equals(deck.cards) : deck.cards == null;
    }
}
