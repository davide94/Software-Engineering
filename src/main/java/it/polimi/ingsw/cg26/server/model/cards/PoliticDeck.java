package it.polimi.ingsw.cg26.server.model.cards;

import it.polimi.ingsw.cg26.server.exceptions.NoMoreCardsException;
import it.polimi.ingsw.cg26.common.state.PoliticDeckState;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 *
 */
public class PoliticDeck extends Deck<PoliticCard> {

    /**
     *
     */
    private LinkedList<PoliticCard> discarded;

    /**
     *
     */
    public PoliticDeck(Collection<PoliticCard> cards) {
        if (cards == null)
            throw new NullPointerException();
        this.discarded = new LinkedList<>(cards);
    }

    public PoliticDeckState getState() {
        return new PoliticDeckState();
    }

    /**
     * @return
     */
    @Override
    public synchronized PoliticCard draw() {
        if (!hasNext()) {
            if (this.discarded.isEmpty())
                throw new NoMoreCardsException();
            this.shuffle();
        }
        return super.draw();
    }

    /**
     * @param
     */
    public synchronized void discard(PoliticCard card) {
        if (card == null)
            throw new NullPointerException();
        this.discarded.add(card);
    }

    public synchronized void discardAll(Collection<PoliticCard> cards) {
        if (cards == null)
            throw new NullPointerException();
        this.discarded.addAll(cards);
    }

    /**
     *
     */
    protected synchronized void shuffle() {
        Collections.shuffle(this.discarded);
        super.addAll(this.discarded);
        this.discarded = new LinkedList<>();
    }

}
