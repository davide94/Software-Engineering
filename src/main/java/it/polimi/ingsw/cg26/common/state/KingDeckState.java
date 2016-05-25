package it.polimi.ingsw.cg26.common.state;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class KingDeckState implements Serializable {

    private static final long serialVersionUID = -5230368352327280474L;

    private List<RewardTileState> cards;

    public KingDeckState(List<RewardTileState> cards) {
        if (cards == null)
            throw new NullPointerException();
        this.cards = cards;
    }

    public List<RewardTileState> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        return "KingDeckState{" +
                "cards=" + cards +
                '}';
    }
}
