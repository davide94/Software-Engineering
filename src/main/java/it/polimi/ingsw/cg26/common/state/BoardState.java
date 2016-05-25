package it.polimi.ingsw.cg26.common.state;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class BoardState implements Serializable {

    private static final long serialVersionUID = 3006533728187141277L;

    private final List<PlayerState> players;

    private final PoliticDeckState politicDeck;

    private List<CouncillorState> councillorsPool;

    private final KingDeckState kingDeck;

    private final BalconyState kingBalcony;

    private final List<RegionState> regions;

    private final NobilityTrackState nobilityTrack;

    private final KingState king;

    private final MarketState market;

    public BoardState(List<PlayerState> players, PoliticDeckState deck, List<CouncillorState> councillorsPool, BalconyState kingBalcony, List<RegionState> regions, NobilityTrackState nobilityTrack, KingState king, MarketState market, KingDeckState kingDeck) {
        if (players == null || deck == null || councillorsPool == null || kingBalcony == null || regions == null || nobilityTrack == null || king == null || kingDeck == null)
            throw new NullPointerException();
        this.players = players;
        this.politicDeck = deck;
        this.councillorsPool = councillorsPool;
        this.kingBalcony = kingBalcony;
        this.regions = regions;
        this.nobilityTrack = nobilityTrack;
        this.king = king;
        this.market = market;
        this.kingDeck = kingDeck;
    }

    public List<PlayerState> getPlayers(){
    	return players;
    }

    public PoliticDeckState getPoliticDeck() {
        return politicDeck;
    }

    public List<CouncillorState> getCouncillorsPool() {
        return councillorsPool;
    }
    
    public void setCouncillorsPool(List<CouncillorState> councillorsPool){
        if (councillorsPool == null)
            throw new NullPointerException();
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
