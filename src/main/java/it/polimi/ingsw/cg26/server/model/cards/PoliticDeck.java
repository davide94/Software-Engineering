package it.polimi.ingsw.cg26.server.model.cards;

import it.polimi.ingsw.cg26.common.dto.PoliticDeckDTO;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class PoliticDeck extends Deck<PoliticCard> {

    /**
     * List of discarded cards
     */
    private List<PoliticCard> discarded;

    /**
     * Creates a deck of Politic Cards
     */
    public PoliticDeck(Collection<PoliticCard> cards) {
        if (cards == null)
            throw new NullPointerException();
        this.discarded = new LinkedList<>(cards);
        shuffle();
    }

    /**
     * Generates the dto of the deck
     * @return the dto of the deck
     */
    public PoliticDeckDTO getState() {
        return new PoliticDeckDTO();
    }

    @Override
    public PoliticCard draw() throws NoRemainingCardsException {
        if (!hasNext()) {
            if (this.discarded.isEmpty())
                throw new NoRemainingCardsException();
            this.shuffle();
        }
        return super.draw();
    }

    /**
     * Discards a card
     * @param card is the card to be discarded
     * @throws NullPointerException if card is null
     */
    public void discard(PoliticCard card) {
        if (card == null)
            throw new NullPointerException();
        this.discarded.add(card);
    }

    /**
     * Discards a collection of cards
     * @param cards is the collection of cards that has to be discarded
     * @throws NullPointerException if cards is null
     */
    public void discardAll(Collection<PoliticCard> cards) {
        if (cards == null)
            throw new NullPointerException();
        this.discarded.addAll(cards);
    }

    /**
     * Puts the discarded cards in the deck in a random order
     */
    private void shuffle() {
        Collections.shuffle(this.discarded);
        super.addAll(this.discarded);
        this.discarded = new LinkedList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (discarded != null ? discarded.hashCode() : 0);
        return result;
    }
}
