package it.polimi.ingsw.cg26.model.cards;

import it.polimi.ingsw.cg26.exceptions.NoMoreCardsException;

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
    public PoliticDeck() {
        this.discarded = new LinkedList<PoliticCard>();
    }

    /**
     *
     * @return
     */
    public synchronized PoliticCard draw() {
        if (!hasNext()) {
            if (this.discarded.size() == 0){
                throw new NoMoreCardsException();
            }
            this.shuffle();
        }
        return super.draw();
    }

    /**
     * @param
     */
    public synchronized void discard(PoliticCard card) {
        if (card == null) {
            throw new NullPointerException();
        }
        this.discarded.add(card);
    }

    /**
     *
     */
    protected synchronized void shuffle() {
        Collections.shuffle(this.discarded);
        addAll(this.discarded);
        this.discarded = new LinkedList<PoliticCard>();
    }
}
