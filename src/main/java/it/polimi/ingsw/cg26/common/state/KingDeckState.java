package it.polimi.ingsw.cg26.common.state;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class KingDeckState implements Serializable {

    private static final long serialVersionUID = -5230368352327280474L;

    private List<List<BonusState>> cards;

    public KingDeckState(List<List<BonusState>> cards) {
        this.cards = cards;
    }

    public List<List<BonusState>> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        return "KingDeckState{" +
                "cards=" + cards +
                '}';
    }
}
