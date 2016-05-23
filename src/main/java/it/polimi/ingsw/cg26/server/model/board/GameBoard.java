package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.common.change.Change;
import it.polimi.ingsw.cg26.common.state.CityState;
import it.polimi.ingsw.cg26.server.model.cards.KingDeck;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.market.Market;
import it.polimi.ingsw.cg26.server.model.player.Player;
import it.polimi.ingsw.cg26.common.state.BoardState;
import it.polimi.ingsw.cg26.common.state.CouncillorState;
import it.polimi.ingsw.cg26.common.state.RegionState;
import it.polimi.ingsw.cg26.server.observer.Observable;

import java.util.Collection;
import java.util.LinkedList;

/**
 * 
 */
public class GameBoard extends Observable<Change> {

	private final PoliticDeck politicDeck;

	private final Collection<Councillor> councillorsPool;

	private KingDeck kingDeck;

	private final Balcony kingBalcony;

	private final Collection<Region> regions;

	private final NobilityTrack nobilityTrack;

	private final King king;

	private final Market market;

	private GameBoard(PoliticDeck deck, Collection<Councillor> councillorsPool, Balcony kingBalcony, Collection<Region> regions, NobilityTrack nobilityTrack, King king, Market market, KingDeck kingDeck) {
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

	public BoardState getState() {
		LinkedList<RegionState> regionsState = new LinkedList<>();
		for (Region region: regions)
			regionsState.add(region.getState());
		LinkedList<CouncillorState> councillorsState = new LinkedList<>();
		for (Councillor c: councillorsPool)
			councillorsState.add(c.getState());
		// TODO serialize market
		return new BoardState(politicDeck.getState(), councillorsState, kingBalcony.getState(), regionsState, nobilityTrack.getState(), king.getState(), null, kingDeck.getState());
	}

	public static GameBoard createGameBoard(PoliticDeck deck, Collection<Councillor> councillorsPool, Balcony kingBalcony, Collection<Region> regions, NobilityTrack nobilityTrack, King king, Market market, KingDeck kingDeck) {
		return new GameBoard(deck, new LinkedList<>(councillorsPool), kingBalcony, new LinkedList<>(regions), nobilityTrack, king, market, kingDeck);
	}

	public Region getRegion(RegionState requiredRegion) {
		for (Region region: this.regions)
			if (region.getState().equals(requiredRegion))
				return region;
		return null;
	}

	public Balcony getKingBalcony() {
		return this.kingBalcony;
	}

	public KingDeck getKingDeck() {
		return this.kingDeck;
	}

	public Collection<Councillor> getCouncillorsPool() {
		return this.councillorsPool;
	}

	public King getKing() {
		return this.king;
	}

	public City getCity(CityState requiredCity) {
		for (Region region: this.regions) {
			City city = region.getCity(requiredCity);
			if (city != null)
				return city;
		}
		return null;
	}

	public PoliticDeck getPoliticDeck() {
		return this.politicDeck;
	}

	public NobilityTrack getNobilityTrack() {
		return this.nobilityTrack;
	}

	@Override
	public String toString() {
		return "GameBoard{" +
				"councillorsPool=" + councillorsPool +
				", kingBalcony=" + kingBalcony +
				", regions=" + regions +
				", nobilityTrack=" + nobilityTrack +
				", king=" + king +
				'}';
	}

	/**
	 * @return the market
	 */
	public Market getMarket() {
		return market;
	}

		
	
	 public boolean checkColorBonuses(Player player, CityColor color){
	    	
	        for(Region iterRegion:regions){
	        	for(City iterCity: iterRegion.getCities()){
	        		if(iterCity.getColor().equals(color) && !iterCity.hasEmporium(player)){
	        			return false;
	        			
	        			//cosa succede per la città viola?
	        			//SOL: contatore che se è uguale a 1 ritorna false
	        		}
	        	}
	        	
	        }
	    	return true;
	    }

	
}