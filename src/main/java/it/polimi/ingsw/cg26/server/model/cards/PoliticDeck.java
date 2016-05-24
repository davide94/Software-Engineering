package it.polimi.ingsw.cg26.server.model.cards;

import it.polimi.ingsw.cg26.server.exceptions.NoMoreCardsException;
import it.polimi.ingsw.cg26.common.state.PoliticDeckState;

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
    }

    /**
     * Generates the state of the deck
     * @return the state of the deck
     */
    public PoliticDeckState getState() {
        return new PoliticDeckState();
    }

    @Override
    public PoliticCard draw() {
        if (!hasNext()) {
            if (this.discarded.isEmpty())
                throw new NoMoreCardsException();
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

}
