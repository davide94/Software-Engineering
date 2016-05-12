package it.polimi.ingsw.cg26.model.cards;

import it.polimi.ingsw.cg26.exceptions.NoMoreCardsException;

import java.util.Collection;
import java.util.LinkedList;

/**
 * 
 */
public class Deck<E> {

    /**
     *
     */
    private LinkedList<E> cards;

    /**
     * Default constructor
     */
    public Deck() {
        this.cards = new LinkedList<E>();
    }

    /**
     *
     */
    public Deck(Collection<E> c) {
        this.cards = new LinkedList<E>();
        addAll(c);
    }

    /**
     *
     */
    public Boolean hasNext() {
        return this.cards.size() != 0;
    }

    /**
     * @return
     */
    public synchronized E draw() {
        if (!hasNext())
            throw new NoMoreCardsException();
        return this.cards.poll();
    }

    /**
     *
     */
    public E draw(int index) {
        return this.cards.remove(index);
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