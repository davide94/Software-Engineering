package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.model.market.Market;
import it.polimi.ingsw.cg26.observer.Observable;

import java.util.Collection;

/**
 * 
 */
public class GameBoard extends Observable {


	private final PoliticDeck politicDeck;
	
	private final Collection<Councillor> councillorsPool;
	
	//private KingDeck kingDeck;
	
	private final Balcony kingBalcony;
	
	private final Collection<Region> regions;
	
	private final NobilityTrack nobilityTrack;
	
	private final King king;
	
	private final Market market;


    public GameBoard(PoliticDeck deck, Collection<Councillor> councillorsPool, Balcony kingBalcony, Collection<Region> regions, NobilityTrack nobilityTrack, King king, Market market) {
    	if (deck == null || councillorsPool == null || kingBalcony == null || regions == null || nobilityTrack == null || king == null)
			throw new NullPointerException();
		this.politicDeck=deck;
    	this.councillorsPool=councillorsPool;
    	this.kingBalcony=kingBalcony;
    	this.regions=regions;
    	this.nobilityTrack=nobilityTrack;
    	this.king=king;
    	this.market=market;
    }

	public Region getRegion(String regionName) {
		for (Region region: this.regions)
			if (region.getName().equalsIgnoreCase(regionName))
				return region;
		return null;
	}

	public Balcony getKingBalcony() {
		return this.kingBalcony;
	}

	public Collection<Councillor> getCouncillorsPool() {
		return this.councillorsPool;
	}

	public King getKing() {
		return this.king;
	}

	public City getCity(String cityName) {
		for (Region region: this.regions) {
			City city = region.getCity(cityName);
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

	public String getState() {
		return this.toString();
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
}