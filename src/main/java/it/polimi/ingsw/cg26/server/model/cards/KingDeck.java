package it.polimi.ingsw.cg26.server.model.cards;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import it.polimi.ingsw.cg26.common.state.KingDeckState;
import it.polimi.ingsw.cg26.common.state.RewardTileState;

/**
 *
 */
public class KingDeck extends Deck<RewardTile> {

    /**
     * Constructs a deck of RewardTiles with a collection of Reward Tiles that will be contained
     * @param c is a collection of reward tiles that the deck will contain, if c is a list, the order will be preserved
     */
    public  KingDeck(Collection<RewardTile> c) {
        super(c);
    }

    /**
     * Generates the state of the Deck
     * @return the state of the deck
     */
    public KingDeckState getState() {
        List<RewardTileState> tileState = new LinkedList<>();
        for (RewardTile t: this.cards) {
            tileState.add(t.getState());
        }
        return new KingDeckState(tileState);
    }

}
