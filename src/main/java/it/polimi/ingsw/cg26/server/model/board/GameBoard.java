package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.common.update.Update;
import it.polimi.ingsw.cg26.common.dto.*;
import it.polimi.ingsw.cg26.server.exceptions.CityNotFoundException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.Scheduler;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.cards.KingDeck;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.market.Market;
import it.polimi.ingsw.cg26.server.model.player.Player;
import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
import it.polimi.ingsw.cg26.common.observer.Observable;

import java.util.*;

/**
 * 
 */
public class GameBoard extends Observable<Update> {

	private final PoliticDeck politicDeck;

	private final Collection<Councillor> councillorsPool;

	private KingDeck kingDeck;

	private final Balcony kingBalcony;

	private final Collection<Region> regions;

	private final NobilityTrack nobilityTrack;

	private final King king;

	private final Market market;
	
	private Map<CityColor, Bonus> colorBonuses;

	private final Scheduler scheduler;

	private GameBoard(PoliticDeck deck, Collection<Councillor> councillorsPool, Balcony kingBalcony, Collection<Region> regions, NobilityTrack nobilityTrack, King king, Market market, KingDeck kingDeck, Map<CityColor, Bonus> colorBonuses) {
		if (deck == null || councillorsPool == null || kingBalcony == null || regions == null || nobilityTrack == null || king == null || market == null || kingDeck == null || colorBonuses == null)
			throw new NullPointerException();
		this.politicDeck = deck;
		this.councillorsPool = councillorsPool;
		this.kingBalcony = kingBalcony;
		this.regions = regions;
		this.nobilityTrack = nobilityTrack;
		this.king = king;
		this.market = market;
		this.kingDeck = kingDeck;
		this.colorBonuses = colorBonuses;
		this.scheduler = new Scheduler(this);

	}

	public static GameBoard createGameBoard(PoliticDeck deck, Collection<Councillor> councillorsPool, Balcony kingBalcony, Collection<Region> regions, NobilityTrack nobilityTrack, King king, Market market, KingDeck kingDeck, Map<CityColor, Bonus> colorBonuses) {
		return new GameBoard(deck, new LinkedList<>(councillorsPool), kingBalcony, new LinkedList<>(regions), nobilityTrack, king, market, kingDeck, colorBonuses);
	}

	public GameBoardDTO getState() {
		LinkedList<RegionDTO> regionsState = new LinkedList<>();
		for (Region region: regions)
			regionsState.add(region.getState());
		LinkedList<CouncillorDTO> councillorsState = new LinkedList<>();
		for (Councillor c: councillorsPool)
			councillorsState.add(c.getState());
		List<PlayerDTO> playersState = scheduler.getPlayersState();
		// TODO serialize market
		return new GameBoardDTO(playersState, scheduler.getCurrentPlayer().getState(), politicDeck.getState(), councillorsState, kingBalcony.getState(), regionsState, nobilityTrack.getState(), king.getState(), market.getState(), kingDeck.getState());
	}

	public Collection<PlayerDTO> getFullPlayers() {
		return scheduler.getPlayersFullState();
	}

	public long registerPlayer(String name) {
		return scheduler.registerPlayer(name);
	}

	public void actionPerformed() {
		scheduler.actionPerformed();
	}

	public Player getCurrentPlayer() {
		return scheduler.getCurrentPlayer();
	}
	
	public void foldSell(){
		scheduler.foldSell();
	}
	
	public void foldBuy(){
		scheduler.foldBuy();
	}

    public Collection<Region> getRegions() {
        return new LinkedList<>(regions);
    }

    public Region getRegion(RegionDTO requiredRegion) {
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

	public City getCity(CityDTO requiredCity) throws CityNotFoundException {
		for (Region region: this.regions) {
			City city = region.getCity(requiredCity);
			if (city != null)
				return city;
		}
		throw new CityNotFoundException();
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

	public void checkBonuses(Player player, CityColor color) throws NoRemainingCardsException {
        if (checkColorBonuses(player, color)) {
            Bonus bonus = colorBonuses.get(color);
            if (bonus == null)
                return;
            bonus.apply(player);
            if (kingDeck.hasNext())
                this.kingDeck.draw().apply(player);
        }

        for (Region r: regions) {
            if (r.checkRegionBonuses(player)) {
                Bonus regionBonus = r.getRegionBonus();
                if (regionBonus != null) {
                    regionBonus.apply(player);
                    if (kingDeck.hasNext())
                        this.kingDeck.draw().apply(player);
                }
            }
        }
    }

    public boolean checkColorBonuses(Player player, CityColor color){
        for(Region iterRegion:regions){
            for(City iterCity: iterRegion.getCities()){
                if(iterCity.getColor().equals(color) && !iterCity.hasEmporium(player)){
                    return false;
                }
            }
        }
        return true;
    }

    public Bonus getColorBonus(CityColor color) {
        return colorBonuses.get(color);
    }
}