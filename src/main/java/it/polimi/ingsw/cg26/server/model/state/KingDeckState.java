package it.polimi.ingsw.cg26.server.model.state;

import it.polimi.ingsw.cg26.server.model.bonus.Bonus;

import java.util.List;

/**
 *
 */
public class KingDeckState {

    private List<List<BonusState>> cards;

    public KingDeckState(List<List<BonusState>> cards) {
        this.cards = cards;
    }
}
