package it.polimi.ingsw.cg26.common.state;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class BoardState implements Serializable {

    private static final long serialVersionUID = 3006533728187141277L;

    private final PoliticDeckState politicDeck;

    private List<CouncillorState> councillorsPool;

    private final KingDeckState kingDeck;

    private final BalconyState kingBalcony;

    private final List<RegionState> regions;

    private final NobilityTrackState nobilityTrack;

    private final KingState king;

    private final MarketState market;

    public BoardState(PoliticDeckState deck, List<CouncillorState> councillorsPool, BalconyState kingBalcony, List<RegionState> regions, NobilityTrackState nobilityTrack, KingState king, MarketState market, KingDeckState kingDeck) {
        if (deck == null || councillorsPool == null || kingBalcony == null || regions == null || nobilityTrack == null || king == null || kingDeck == null)
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

    public PoliticDeckState getPoliticDeck() {
        return politicDeck;
    }

    public List<CouncillorState> getCouncillorsPool() {
        return councillorsPool;
    }
    
    public void setCouncillorsPool(List<CouncillorState> councillorsPool){
    	this.councillorsPool = councillorsPool;
    }

    public KingDeckState getKingDeck() {
        return kingDeck;
    }

    public BalconyState getKingBalcony() {
        return kingBalcony;
    }

    public List<RegionState> getRegions() {
        return regions;
    }

    public NobilityTrackState getNobilityTrack() {
        return nobilityTrack;
    }

    public KingState getKing() {
        return king;
    }

    public MarketState getMarket() {
        return market;
    }

    @Override
    public String toString() {
        return "BoardState{" +
                "politicDeck=" + politicDeck +
                ", councillorsPool=" + councillorsPool +
                ", kingDeck=" + kingDeck +
                ", kingBalcony=" + kingBalcony +
                ", regions=" + regions +
                ", nobilityTrack=" + nobilityTrack +
                ", king=" + king +
                ", market=" + market +
                '}';
    }
}
