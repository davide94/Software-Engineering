package it.polimi.ingsw.cg26.common.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;

/**
 *
 */
public class GameBoardDTO implements Serializable {

    private static final long serialVersionUID = 3006533728187141277L;

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

    /**
     * Constructs a GameBoard DTO object
     * @param players is a list of player DTO
     * @param deck is a politic deck DTO
     * @param councillorsPool is a collection of councillor DTO
     * @param kingBalcony is a balcony DTO
     * @param regions is a list of region DTO
     * @param nobilityTrack is a nobility track DTO
     * @param king is a king DTO
     * @param market is a market DTO
     * @param kingDeck is a king's deck DTO
     * @throws NullPointerException if any of the parameters is null
     */
    public GameBoardDTO(List<PlayerDTO> players, PlayerDTO currentPlayer, PoliticDeckDTO deck, Collection<CouncillorDTO> councillorsPool, BalconyDTO kingBalcony, List<RegionDTO> regions, NobilityTrackDTO nobilityTrack, KingDTO king, MarketDTO market, KingDeckDTO kingDeck, Map<CityColorDTO, BonusDTO> colorBonuses) {
        if (players == null || deck == null || councillorsPool == null || kingBalcony == null || regions == null || nobilityTrack == null || king == null || market == null || kingDeck == null || colorBonuses == null)
            throw new NullPointerException();
        this.players = players;
        this.currentPlayer = currentPlayer;
        this.politicDeck = deck;
        this.councillorsPool = councillorsPool;
        this.kingBalcony = kingBalcony;
        this.regions = regions;
        this.nobilityTrack = nobilityTrack;
        this.king = king;
        this.market = market;
        this.kingDeck = kingDeck;
        this.colorBonuses = colorBonuses;
    }

    /**
     *
     * @return
     */
    public PlayerDTO getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     *
     * @param currentPlayer
     */
    public void setCurrentPlayer(PlayerDTO currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Returns a list of Player DTO
     * @return a list of Player DTO
     */
    public List<PlayerDTO> getPlayers(){
    	return players;
    }

    /**
     *
     * @param players
     */
    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }

    /**
     * Returns a politic Deck DTO
     * @return a politic Deck DTO
     */
    public PoliticDeckDTO getPoliticDeck() {
        return politicDeck;
    }

    /**
     *
     * @param politicDeck
     */
    public void setPoliticDeck(PoliticDeckDTO politicDeck) {
        this.politicDeck = politicDeck;
    }

    /**
     * Returns a collection of Councillors DTO
     * @return a collection of Councillors DTO
     */
    public Collection<CouncillorDTO> getCouncillorsPool() {
        return councillorsPool;
    }

    /**
     * Sets the councillors pool
     * @param councillorsPool is a collection of Councillors DTO
     * @throws NullPointerException if councillorsPool is null
     */
    public void setCouncillorsPool(Collection<CouncillorDTO> councillorsPool){
        if (councillorsPool == null)
            throw new NullPointerException();
    	this.councillorsPool = councillorsPool;
    }

    /**
     * Returns the King's Deck DTO
     * @return the King's Deck DTO
     */
    public KingDeckDTO getKingDeck() {
        return kingDeck;
    }

    /**
     *
     * @param kingDeck
     */
    public void setKingDeck(KingDeckDTO kingDeck) {
        this.kingDeck = kingDeck;
    }

    /**
     * Returns the King's Balcony DTO
     * @return the King's Balcony DTO
     */
    public BalconyDTO getKingBalcony() {
        return kingBalcony;
    }

    /**
     *
     * @param kingBalcony
     */
    public void setKingBalcony(BalconyDTO kingBalcony) {
        this.kingBalcony = kingBalcony;
    }

    /**
     * Returns a list of Regions DTO
     * @return a list of Regions DTO
     */
    public List<RegionDTO> getRegions() {
        return regions;
    }

    /**
     *
     * @param regions
     */
    public void setRegions(List<RegionDTO> regions) {
        this.regions = regions;
    }

    /**
     * Returns a Nobility Track DTO
     * @return a Nobility Track DTO
     */
    public NobilityTrackDTO getNobilityTrack() {
        return nobilityTrack;
    }

    /**
     *
     * @param nobilityTrack
     */
    public void setNobilityTrack(NobilityTrackDTO nobilityTrack) {
        this.nobilityTrack = nobilityTrack;
    }

    /**
     * Returns a King DTO
     * @return a King DTO
     */
    public KingDTO getKing() {
        return king;
    }

    /**
     *
     * @param king
     */
    public void setKing(KingDTO king) {
        this.king = king;
    }

    /**
     * Returns a Market DTO
     * @return a Market DTO
     */
    public MarketDTO getMarket() {
        return market;
    }

    /**
     *
     * @param market
     */
    public void setMarket(MarketDTO market) {
        this.market = market;
    }
    

	/**
	 * @return the colorBonuses
	 */
	public Map<CityColorDTO, BonusDTO> getColorBonuses() {
		return colorBonuses;
	}

	/**
	 * @param colorBonuses the colorBonuses to set
	 */
	public void setColorBonuses(Map<CityColorDTO, BonusDTO> colorBonuses) {
		this.colorBonuses = colorBonuses;
	}

    @Override
    public String toString() {
        return "GameBoardDTO{" +
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
