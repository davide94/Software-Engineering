package it.polimi.ingsw.cg26.server.model.cards;

import java.util.LinkedList;
import java.util.List;

import it.polimi.ingsw.cg26.common.state.KingDeckState;
import it.polimi.ingsw.cg26.common.state.RewardTileState;

public class KingDeck extends Deck<RewardTile> {
    /**
     *
     */
    public  KingDeck(List<RewardTile> c) {
        super(c);
    }

    public KingDeckState getState() {
        List<RewardTileState> tileState = new LinkedList<>();
        for (RewardTile t: this.cards) {
            tileState.add(t.getState());
        }
        return new KingDeckState(tileState);
    }
}
