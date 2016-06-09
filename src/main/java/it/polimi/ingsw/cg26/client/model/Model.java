package it.polimi.ingsw.cg26.client.model;

import it.polimi.ingsw.cg26.client.model.state.GameNotStarted;
import it.polimi.ingsw.cg26.client.model.state.State;
import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.dto.*;
import it.polimi.ingsw.cg26.common.observer.Observable;

import java.util.Collection;
import java.util.List;

/**
 *
 */
public class Model extends Observable<Model> implements ClientModel {

    private State state;

    private PlayerDTO localPlayer;

    private PlayerDTO currentPlayer;

    private List<PlayerDTO> players;

    private PoliticDeckDTO politicDeck;

    private Collection<CouncillorDTO> councillorsPool;

    private KingDeckDTO kingDeck;

    private BalconyDTO kingBalcony;

    private List<RegionDTO> regions;

    private NobilityTrackDTO nobilityTrack;

    private KingDTO king;

    private MarketDTO market;

    public Model() {
        state = new GameNotStarted();
    }

    public State getState() {
        return state;
    }

    public PlayerDTO getLocalPlayer() {
        return localPlayer;
    }

    public PlayerDTO getCurrentPlayer() {
        return currentPlayer;
    }

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public PoliticDeckDTO getPoliticDeck() {
        return politicDeck;
    }

    public Collection<CouncillorDTO> getCouncillorsPool() {
        return councillorsPool;
    }

    public KingDeckDTO getKingDeck() {
        return kingDeck;
    }

    public BalconyDTO getKingBalcony() {
        return kingBalcony;
    }

    public List<RegionDTO> getRegions() {
        return regions;
    }

    public NobilityTrackDTO getNobilityTrack() {
        return nobilityTrack;
    }

    public KingDTO getKing() {
        return king;
    }

    public MarketDTO getMarket() {
        return market;
    }

    public void setLocalPlayer(PlayerDTO localPlayer) {
        this.localPlayer = localPlayer;
    }

    public void setCurrentPlayer(PlayerDTO currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }

    public void setPoliticDeck(PoliticDeckDTO politicDeck) {
        this.politicDeck = politicDeck;
    }

    public void setCouncillorsPool(Collection<CouncillorDTO> councillorsPool) {
        this.councillorsPool = councillorsPool;
    }

    public void setKingDeck(KingDeckDTO kingDeck) {
        this.kingDeck = kingDeck;
    }

    public void setKingBalcony(BalconyDTO kingBalcony) {
        this.kingBalcony = kingBalcony;
    }

    public void setRegions(List<RegionDTO> regions) {
        this.regions = regions;
    }

    public void setNobilityTrack(NobilityTrackDTO nobilityTrack) {
        this.nobilityTrack = nobilityTrack;
    }

    public void setKing(KingDTO king) {
        this.king = king;
    }

    public void setMarket(MarketDTO market) {
        this.market = market;
    }
}
