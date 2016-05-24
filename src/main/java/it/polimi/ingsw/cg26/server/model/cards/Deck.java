package it.polimi.ingsw.cg26.server.model.cards;

import it.polimi.ingsw.cg26.server.exceptions.NoMoreCardsException;

import java.util.Collection;
import java.util.LinkedList;

/**
 * 
 */
public class Deck<E> {

    /**
     * List of cards in the deck
     */
    protected LinkedList<E> cards;

    /**
     * Constructs an empty Deck
     */
    public Deck() {
        this.cards = new LinkedList<>();
    }

    /**
     * Constructs a deck with a collection of cards, if c is a List, the order is preserved
     * @param c is a collection of cards that the deck will contain
     * @throws NullPointerException if c is null
     */
    public Deck(Collection<E> c) {
        if (c == null)
            throw new NullPointerException();
        this.cards = new LinkedList<>(c);
    }

    /**
     * checks if there are at least one more card in the deck
     * @return true if there are at least one more card in the deck, false if not
     */
    public boolean hasNext() {
        return !this.cards.isEmpty();
    }

    /**
     * Draws (removes and returns) a card from the deck
     * @return the first element of the deck
     * @throws NoMoreCardsException if there are no more cards remaining in the deck
     */
    public E draw() {
        if (!hasNext())
            throw new NoMoreCardsException();
        return this.cards.poll();
    }

    /**
     * Puts a card at the bottom of the deck
     * @param c is a card to be added to the desk
     * @throws NullPointerException if c is null
     */
    public void add(E c) {
        if (c == null)
            throw new NullPointerException();
        this.cards.add(c);
    }

    /**
     * Puts a collection of cards at the bottom of the deck, if c is a List, the order is preserved
     * @param c is a collection of cards to be added to the desk
     * @throws NullPointerException if c is null
     */
    public void addAll(Collection<E> c) {
        if (c == null)
            throw new NullPointerException();
        this.cards.addAll(c);
    }

}