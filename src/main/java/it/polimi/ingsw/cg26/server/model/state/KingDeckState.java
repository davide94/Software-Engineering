package it.polimi.ingsw.cg26.server.model.state;

import java.util.List;

/**
 *
 */
public class KingDeckState {

    private List<List<BonusState>> cards;

    public KingDeckState(List<List<BonusState>> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return "KingDeckState{" +
                "cards=" + cards +
                '}';
    }
}
