package it.polimi.ingsw.cg26.server.model.cards;

import it.polimi.ingsw.cg26.server.exceptions.NoMoreCardsException;

import java.util.Collection;
import java.util.LinkedList;

/**
 * 
 */
public class Deck<E> {

    /**
     *
     */
    protected LinkedList<E> cards;

    /**
     * Default constructor
     */
    public Deck() {
        this.cards = new LinkedList<>();
    }

    /**
     *
     */
    public Deck(Collection<E> c) {
        if (c == null)
            throw new NullPointerException();
        this.cards = new LinkedList<>(c);
    }

    /**
     *
     */
    public Boolean hasNext() {
        return !this.cards.isEmpty();
    }

    /**
     * @return
     */
    public E draw() {
        if (!hasNext())
            throw new NoMoreCardsException();
        return this.cards.poll();
    }

    /**
     *
     */
    public void add(E e) {
        if (e == null)
            throw new NullPointerException();
        this.cards.add(e);
    }

    /**
     *
     */
    public void addAll(Collection<E> c) {
        if (c == null)
            throw new NullPointerException();
        this.cards.addAll(c);
    }
}