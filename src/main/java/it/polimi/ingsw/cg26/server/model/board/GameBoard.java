package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.common.change.Change;
import it.polimi.ingsw.cg26.common.state.CityState;
import it.polimi.ingsw.cg26.server.model.Scheduler;
import it.polimi.ingsw.cg26.server.model.cards.KingDeck;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import it.polimi.ingsw.cg26.server.model.market.Market;
import it.polimi.ingsw.cg26.server.model.player.Player;
import it.polimi.ingsw.cg26.common.state.BoardState;
import it.polimi.ingsw.cg26.common.state.CouncillorState;
import it.polimi.ingsw.cg26.common.state.PlayerState;
import it.polimi.ingsw.cg26.common.state.RegionState;
import it.polimi.ingsw.cg26.common.observer.Observable;
import java.util.Collection;
import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;

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
	
	private Dictionary<CityColor, RewardTile> colorBonuses;

	
	
	
	
	

	

	private final Scheduler scheduler;

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
		this.scheduler = new Scheduler(this);

	}

	public static GameBoard createGameBoard(PoliticDeck deck, Collection<Councillor> councillorsPool, Balcony kingBalcony, Collection<Region> regions, NobilityTrack nobilityTrack, King king, Market market, KingDeck kingDeck) {
		return new GameBoard(deck, new LinkedList<>(councillorsPool), kingBalcony, new LinkedList<>(regions), nobilityTrack, king, market, kingDeck);
	}

	public BoardState getState() {
		LinkedList<RegionState> regionsState = new LinkedList<>();
		for (Region region: regions)
			regionsState.add(region.getState());
		LinkedList<CouncillorState> councillorsState = new LinkedList<>();
		for (Councillor c: councillorsPool)
			councillorsState.add(c.getState());
		List<PlayerState> playersState = scheduler.getPlayersState();
		// TODO serialize market
		return new BoardState(playersState, politicDeck.getState(), councillorsState, kingBalcony.getState(), regionsState, nobilityTrack.getState(), king.getState(), null, kingDeck.getState());
	}

	public void registerPlayer(Player player) {
		scheduler.registerPlayer(player);
	}

	public void actionPerformed() {
		scheduler.actionPerformed();
	}

	public Player getCurrentPlayer() {
		return scheduler.getCurrentPlayer();
	}

	public Region getRegion(RegionState requiredRegion) {
		for (Region region: this.regions)
			if (region.getName().equals(requiredRegion.getName()))
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

		
	
	 public void checkBonuses(Player player, CityColor color){
		 if (checkColorBonuses(player, color)) {
			 RewardTile bonus = colorBonuses.get(color);
		        if (bonus == null)
		        	return;
		        bonus.apply(player);
		        if (kingDeck.hasNext())
		        	this.kingDeck.draw().apply(player);
		 }
		 
		 for (Region r: regions) {
			 if (r.checkRegionBonuses(player)) {
				 RewardTile regionBonus = r.getRegionBonus();
				 if (regionBonus != null) {
					 regionBonus.apply(player);
				 }
			 }
		 }
			 
		 
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
	 
	 
	 public RewardTile getColorBonus(CityColor color) {
			return colorBonuses.get(color);
		}

	
}