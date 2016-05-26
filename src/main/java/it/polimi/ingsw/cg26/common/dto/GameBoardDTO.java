package it.polimi.ingsw.cg26.common.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 *
 */
public class GameBoardDTO implements Serializable {

    private static final long serialVersionUID = 3006533728187141277L;

    private PlayerDTO localPlayer;

    private final List<PlayerDTO> players;

    private final PoliticDeckDTO politicDeck;

    private Collection<CouncillorDTO> councillorsPool;

    private final KingDeckDTO kingDeck;

    private final BalconyDTO kingBalcony;

    private final List<RegionDTO> regions;

    private final NobilityTrackDTO nobilityTrack;

    private final KingDTO king;

    private final MarketDTO market;

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
    public GameBoardDTO(List<PlayerDTO> players, PoliticDeckDTO deck, Collection<CouncillorDTO> councillorsPool, BalconyDTO kingBalcony, List<RegionDTO> regions, NobilityTrackDTO nobilityTrack, KingDTO king, MarketDTO market, KingDeckDTO kingDeck) {
        if (players == null || deck == null || councillorsPool == null || kingBalcony == null || regions == null || nobilityTrack == null || king == null || market == null || kingDeck == null)
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


    public PlayerDTO getlocalPlayer() {
        return localPlayer;
    }

    public void setLocalPlayer(PlayerDTO localPlayer) {
        this.localPlayer = localPlayer;
    }

    /**
     * Returns a list of Player DTO
     * @return a list of Player DTO
     */
    public List<PlayerDTO> getPlayers(){
    	return players;
    }

    /**
     * Returns a politic Deck DTO
     * @return a politic Deck DTO
     */
    public PoliticDeckDTO getPoliticDeck() {
        return politicDeck;
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
     * Returns the King's Balcony DTO
     * @return the King's Balcony DTO
     */
    public BalconyDTO getKingBalcony() {
        return kingBalcony;
    }

    /**
     * Returns a list of Regions DTO
     * @return a list of Regions DTO
     */
    public List<RegionDTO> getRegions() {
        return regions;
    }

    /**
     * Returns a Nobility Track DTO
     * @return a Nobility Track DTO
     */
    public NobilityTrackDTO getNobilityTrack() {
        return nobilityTrack;
    }

    /**
     * Returns a King DTO
     * @return a King DTO
     */
    public KingDTO getKing() {
        return king;
    }

    /**
     * Returns a Market DTO
     * @return a Market DTO
     */
    public MarketDTO getMarket() {
        return market;
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
