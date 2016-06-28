package it.polimi.ingsw.cg26.client.model;

import it.polimi.ingsw.cg26.client.model.state.StateContext;
import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.dto.*;
import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.common.observer.Observable;
import it.polimi.ingsw.cg26.common.update.Update;

import java.util.*;

/**
 *
 */
public class Model extends Observable<Update> implements ClientModel {

    private StateContext state;

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
    
    private Map<CityColorDTO, BonusDTO> colorBonuses;

    private List<String> messages;

    public Model() {
        state = new StateContext();
        players = new LinkedList<>();
        councillorsPool = new LinkedList<>();
        regions = new LinkedList<>();
        colorBonuses = new HashMap<>();
        messages = new LinkedList<>();
    }

    public StateContext getState() {
        return state;
    }

    public PlayerDTO getLocalPlayer() {
        return localPlayer;
    }

    public PlayerDTO getCurrentPlayer() {
        return currentPlayer;
    }

    public List<PlayerDTO> getPlayers() {
        return new LinkedList<>(players);
    }

    public PoliticDeckDTO getPoliticDeck() {
        return politicDeck;
    }

    public Collection<CouncillorDTO> getCouncillorsPool() {
        return new LinkedList<>(councillorsPool);
    }

    public KingDeckDTO getKingDeck() {
        return kingDeck;
    }

    public BalconyDTO getKingBalcony() {
        return kingBalcony;
    }

    public List<RegionDTO> getRegions() {
        return new LinkedList<>(regions);
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
    
    public Map<CityColorDTO, BonusDTO> getColorBonuses() {
    	return new HashMap<>(colorBonuses);
    }

    @Override
    public void setLocalPlayer(PlayerDTO localPlayer) {
        this.localPlayer = localPlayer;
    }

    @Override
    public void setCurrentPlayer(PlayerDTO currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public synchronized void setPlayers(List<PlayerDTO> players) {
        this.players = new LinkedList<>(players);
    }

    @Override
    public void setPoliticDeck(PoliticDeckDTO politicDeck) {
        this.politicDeck = politicDeck;
    }

    @Override
    public void setCouncillorsPool(Collection<CouncillorDTO> councillorsPool) {
        this.councillorsPool = new LinkedList<>(councillorsPool);
    }

    @Override
    public void setKingDeck(KingDeckDTO kingDeck) {
        this.kingDeck = kingDeck;
    }

    @Override
    public void setKingBalcony(BalconyDTO kingBalcony) {
        this.kingBalcony = kingBalcony;
    }

    @Override
    public void setRegions(List<RegionDTO> regions) {
        this.regions = new LinkedList<>(regions);
    }

    @Override
    public void setNobilityTrack(NobilityTrackDTO nobilityTrack) {
        this.nobilityTrack = nobilityTrack;
    }

    @Override
    public void setKing(KingDTO king) {
        this.king = king;
    }

    @Override
    public void setMarket(MarketDTO market) {
        this.market = market;
    }

	@Override
	public void setColorBonuses(Map<CityColorDTO, BonusDTO> colorBonuses) {
		this.colorBonuses = new HashMap<>(colorBonuses);
	}

    @Override
    public void addMessage(String sender, String body) {
        messages.add((sender.equals(localPlayer.getName()) ? "You" : sender) + ": " + body);
    }

    public List<String> getMessages() {
        return new LinkedList<>(messages);
    }
}
