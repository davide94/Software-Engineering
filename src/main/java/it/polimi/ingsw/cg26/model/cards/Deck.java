package it.polimi.ingsw.cg26.model.cards;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 */
public class Deck {

    /**
     *
     */
    private ArrayList<PoliticCard> cards;
    private ArrayList<PoliticCard> discarded;

    /**
     * Default constructor
     */
    public Deck() {
        this.cards = new ArrayList<PoliticCard>();
        this.discarded = new ArrayList<PoliticCard>();
    }


    /**
     * @return
     */
    public synchronized PoliticCard draw() {
        if (!this.cards.iterator().hasNext()) {
            this.shuffle();
        }
        return this.cards.iterator().next();
    }

    /**
     * @param
     */
    public void discard(PoliticCard card) {
        if (card != null) {
            this.discarded.add(card);
        }
    }

    private void shuffle() {
        Collections.shuffle(this.discarded);
    }

}