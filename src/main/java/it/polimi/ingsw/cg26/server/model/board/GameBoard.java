package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.common.dto.*;
import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.common.observer.Observable;
import it.polimi.ingsw.cg26.common.update.PrivateUpdate;
import it.polimi.ingsw.cg26.common.update.Update;
import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.change.FullStateChange;
import it.polimi.ingsw.cg26.common.update.change.LocalPlayerChange;
import it.polimi.ingsw.cg26.server.exceptions.CityNotFoundException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.Scheduler;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.server.model.cards.KingDeck;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.market.Market;
import it.polimi.ingsw.cg26.server.model.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 
 */
public class GameBoard extends Observable<Update> {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

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

	/**
	 * Create a Board for the game
	 * @param deck is collection of politic cards
	 * @param councillorsPool is a collection of councillors that are not located in any balcony
	 * @param kingBalcony  is the king's balcony
	 * @param regions is a collection of regions that are on the map
	 * @param nobilityTrack contains the first nobility cell of the path of the nobility
	 * @param king is the king in the game
	 * @param market is the part of the game to sell and buy cards, tiles and assistants
	 * @param kingDeck is the the of Business Permission Tiles of the king
	 * @param colorBonuses it is the matching between all the cities with the same color and a particular bonus
	 * @throws NullPointerException if one of the parameters is null
	 */
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

	/**
	 * Create a Board for the game
	 * @param deck is collection of politic cards
	 * @param councillorsPool is a collection of councillors that are not located in any balcony
	 * @param kingBalcony  is the king's balcony
	 * @param regions is a collection of regions that are on the map
	 * @param nobilityTrack contains the first nobility cell of the path of the nobility
	 * @param king is the king in the game
	 * @param market is the part of the game to sell and buy cards, tiles and assistants
	 * @param kingDeck is the the of Business Permission Tiles of the king
	 * @param colorBonuses it is the matching between all the cities with the same color and a particular bonus
	 * @return a new game board
	 */
	public static GameBoard createGameBoard(PoliticDeck deck, Collection<Councillor> councillorsPool, Balcony kingBalcony, Collection<Region> regions, NobilityTrack nobilityTrack, King king, Market market, KingDeck kingDeck, Map<CityColor, Bonus> colorBonuses) {
		return new GameBoard(deck, new LinkedList<>(councillorsPool), kingBalcony, new LinkedList<>(regions), nobilityTrack, king, market, kingDeck, colorBonuses);
	}

	/**
	 * Get the State of the Game board
     * @return GameBoard DTO of the Game board
	 */
	public GameBoardDTO getState() {
		LinkedList<RegionDTO> regionsState = regions.stream().map(Region::getState)
                .collect(Collectors.toCollection(LinkedList::new));
        LinkedList<CouncillorDTO> councillorsState = councillorsPool.stream().map(Councillor::getState)
                .collect(Collectors.toCollection(LinkedList::new));
        List<PlayerDTO> playersState = scheduler.getPlayersState();
        Map<CityColorDTO, BonusDTO> colorBonusesDTO = new HashMap<>();
        for(Map.Entry<CityColor, Bonus> b : colorBonuses.entrySet()){
        	colorBonusesDTO.put(b.getKey().getState(), b.getValue().getState());
        }
		return new GameBoardDTO(playersState, scheduler.getCurrentPlayer() == null ? null: scheduler.getCurrentPlayer().getState(), politicDeck.getState(), councillorsState, kingBalcony.getState(), regionsState, nobilityTrack.getState(), king.getState(), market.getState(), kingDeck.getState(), colorBonusesDTO);
	}

	/**
	 * Get the scheduler that rules the turn logic of the game
	 * @return the scheduler of the game 
	 */
    public Scheduler getScheduler() {
        return scheduler;
    }

    /**
     * It is the procedure to start the game
     */
	public void start() {

        if (scheduler.playersNumber() == 2) {
            for (Region r: regions) {
                BusinessPermissionTile tile = r.getBPTDeck().randomCard();
                for (City c: tile.getCities()) {
                    // TODO: add placeholderEmporium
                }
            }
        }

        try {
            scheduler.start();
        } catch (NoRemainingCardsException e) {
            log.error("Not enough cards", e); // TODO: handle
        }

        notifyObservers(new FullStateChange(new BasicChange(), getState()));
        for (PlayerDTO player : getFullPlayers())
            notifyObservers(new PrivateUpdate(new LocalPlayerChange(new BasicChange(), player), player.getToken()));
    }
	
	/**
	 * Returns a collection that represents the DTO of all the players
	 * @return the dto of all the players
	 */
	public Collection<PlayerDTO> getFullPlayers() {
		return scheduler.getPlayersFullState();
	}

	/**
	 * Registers a new player
	 * @param name is the name of the player that have to be registered
	 * @return the token of the player that has been registered
	 * @throws NoRemainingCardsException if the new player doesn't draw the cards to start the game
	 */
	public Player registerPlayer(String name) throws NoRemainingCardsException {
		return scheduler.registerPlayer(name);
	}

	/**
	 * Returns the current Player
	 * @return the current Player
	 */
	public Player getCurrentPlayer() {
		return scheduler.getCurrentPlayer();
	}

	/**
	 * Get the region of the board
	 * @return all the regions of the board
	 */
    public Collection<Region> getRegions() {
        return new LinkedList<>(regions);
    }

    /**
     * 
     * @param requiredRegion is a region DTO 
     * @return the region of the board that matches with requiredRegion
     */
    public Region getRegion(RegionDTO requiredRegion) {
		for (Region region: this.regions)
			if (region.getName().equals(requiredRegion.getName()))
				return region;
		return null;
	}

    /**
     * Get king's balcony
     * @return king's balcony
     */
	public Balcony getKingBalcony() {
		return this.kingBalcony;
	}

	/**
	 * Get king's deck of Business Permission Tiles
	 * @return king's deck of Business Permission Tiles
	 */
	public KingDeck getKingDeck() {
		return this.kingDeck;
	}

	/**
	 * get the councillors' pool
	 * @return the councillors' pool
	 */
	public Collection<Councillor> getCouncillorsPool() {
		return new LinkedList<>(councillorsPool);
	}

    public boolean addCouncillor(Councillor c) {
        if (c == null)
            throw new NullPointerException();
        return councillorsPool.add(c);
    }

    public boolean removeCouncillor(Councillor c) {
        if (c == null)
            throw new NullPointerException();
        return councillorsPool.remove(c);
    }

    /**
	 * Get the king
	 * @return the king
	 */
	public King getKing() {
		return this.king;
	}

	/**
	 * 
	 * @param requiredCity is a city DTO
	 * @return the city of the board that matches with requiredCity
	 * @throws CityNotFoundException if there isn't a city in board that matches with requiredCity
	 */
	public City getCity(CityDTO requiredCity) throws CityNotFoundException {
		for (Region region: this.regions) {
			City city = region.getCity(requiredCity);
			if (city != null)
				return city;
		}
		throw new CityNotFoundException();
	}

	/**
	 * Get the deck of politic cards
	 * @return the deck of politic cards
	 */
	public PoliticDeck getPoliticDeck() {
		return this.politicDeck;
	}

	/**
	 * Get the nobility Track
	 * @return the nobility Track
	 */
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
	 * Get the market
	 * @return the market
	 */
	public Market getMarket() {
		return market;
	}

	/**
	 * Apply all the bonuses if the player has his emporiums in all the cities with the same color or of the same region 
	 * @param player is the player that has just built an emporium
	 * @param color is the color of the city in which the player has just built an emporium
	 * @throws NoRemainingCardsException if there aren't enough cards in the Politic cards deck
	 */
	public void checkBonuses(Player player, CityColor color) throws NoRemainingCardsException {
        if (checkColorBonuses(player, color)) {
            Bonus bonus = colorBonuses.get(color);
            if (bonus == null)
                return;
            bonus.apply(player);
            colorBonuses.remove(color);
            if (kingDeck.hasNext())
                this.kingDeck.draw().apply(player);
        }

        for (Region r: regions) {
            if (r.checkRegionBonuses(player)) {
                Bonus regionBonus = r.getRegionBonus();
                Bonus empty= new EmptyBonus();
                if (!regionBonus.equals(empty)){
                    regionBonus.apply(player);
                    if (kingDeck.hasNext())
                        this.kingDeck.draw().apply(player);
                }
               
            }
        }
    }

	/**
	 * Check if the player has his emporiums in all the cities with the same color
	 * @param player is the player that has just built an emporium
	 * @param color is the color of the city in which the player has just built an emporium
	 * @return true if the player has one emporium in every city with the same color else false
	 */
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

    /**
     * Get a bonus associated to the color of a group of cities
     * @param color is the color of a group of cities in the map
     * @return the bonus associated to the color of a group of cities
     */
    public Bonus getColorBonus(CityColor color) {
        return colorBonuses.get(color);
    }
}