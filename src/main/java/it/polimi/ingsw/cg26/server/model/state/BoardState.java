package it.polimi.ingsw.cg26.server.model.state;

import it.polimi.ingsw.cg26.server.model.board.Councillor;
import it.polimi.ingsw.cg26.server.model.board.King;
import it.polimi.ingsw.cg26.server.model.market.Market;

import java.util.Collection;

/**
 *
 */
public class BoardState {

    private final PoliticDeckState politicDeck;

    private final Collection<Councillor> councillorsPool;

    private final KingDeckState kingDeck;

    private final BalconyState kingBalcony;

    private final Collection<RegionState> regions;

    private final NobilityTrackState nobilityTrack;

    private final KingState king;

    private final MarketState market;

    public BoardState(PoliticDeckState deck, Collection<Councillor> councillorsPool, BalconyState kingBalcony, Collection<RegionState> regions, NobilityTrackState nobilityTrack, KingState king, MarketState market, KingDeckState kingDeck) {
        if (deck == null || councillorsPool == null || kingBalcony == null || regions == null || nobilityTrack == null || king == null || market == null || kingDeck == null)
            throw new NullPointerException();
        this.politicDeck = deck;
        this.councillorsPool = councillorsPool;
        this.kingBalcony = kingBalcony;
        this.regions = regions;
        this.nobilityTrack = nobilityTrack;
        this.king = king;
        this.market = market;
        this.kingDeck = kingDeck;
    }
}
