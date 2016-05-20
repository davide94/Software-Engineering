package it.polimi.ingsw.cg26.server.model.cards;

import java.util.LinkedList;
import java.util.List;

import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.common.state.BonusState;
import it.polimi.ingsw.cg26.common.state.KingDeckState;

public class KingDeck extends Deck<List<Bonus>> {
    /**
     *
     */
    public  KingDeck(List<List<Bonus>> c) {
        super(c);
    }

    public KingDeckState getState() {
        List<List<BonusState>> cardsState = new LinkedList<>();
        for (List<Bonus> t: this.cards) {
            List<BonusState> bonusState = new LinkedList<>();
            for (Bonus b : t) {
                bonusState.add(b.getState());
            }
            cardsState.add(bonusState);
        }
        return new KingDeckState(cardsState);
    }
}
