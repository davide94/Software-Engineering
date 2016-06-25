package it.polimi.ingsw.cg26.common.dto;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.bonusdto.AssistantBonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.CoinBonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.EmptyBonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.NobilityBonusDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 *
 */
public class GameBoardDTOTest {

    private LinkedList<PlayerDTO> players;
    private PlayerDTO currentPlayer;
    private PoliticDeckDTO deck;
    private List<CouncillorDTO> councillorsPool;
    private BalconyDTO kingBalcony;
    private List<RegionDTO> regions;
    private NobilityTrackDTO nobilityTrack;
    private KingDTO king;
    private MarketDTO market;
    private KingDeckDTO kingDeck;
    private Map<CityColorDTO, BonusDTO> colorBonuses;

    private GameBoardDTO board;

    @Before
    public void setUp() throws Exception {
        players = new LinkedList<>();
        currentPlayer = new PlayerDTO("playerName", 0, false, 0, 0, 0, 0, 0, 0, new LinkedList<PoliticCardDTO>(), new LinkedList<BusinessPermissionTileDTO>(), new LinkedList<BusinessPermissionTileDTO>());
        deck = new PoliticDeckDTO();
        councillorsPool = new LinkedList<>();
        kingBalcony = new BalconyDTO(new LinkedList<>());
        regions = new LinkedList<>();
        nobilityTrack = new NobilityTrackDTO(new LinkedList<>());
        king = new KingDTO("cityname");
        market = new MarketDTO(new ArrayList<SellableDTO>());
        kingDeck = new KingDeckDTO(new LinkedList<>());
        colorBonuses = new HashMap<>();

        board = new GameBoardDTO(players, currentPlayer, deck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);

    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail1() throws Exception {
        new GameBoardDTO(null, currentPlayer, deck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail2() throws Exception {
        new GameBoardDTO(players, currentPlayer, null, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail3() throws Exception {
        new GameBoardDTO(players, currentPlayer, deck, null, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail4() throws Exception {
        new GameBoardDTO(players, currentPlayer, deck, councillorsPool, null, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail5() throws Exception {
        new GameBoardDTO(players, currentPlayer, deck, councillorsPool, kingBalcony, null, nobilityTrack, king, market, kingDeck, colorBonuses);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail6() throws Exception {
        new GameBoardDTO(players, currentPlayer, deck, councillorsPool, kingBalcony, regions, null, king, market, kingDeck, colorBonuses);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail7() throws Exception {
        new GameBoardDTO(players, currentPlayer, deck, councillorsPool, kingBalcony, regions, nobilityTrack, null, market, kingDeck, colorBonuses);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail8() throws Exception {
        new GameBoardDTO(players, currentPlayer, deck, councillorsPool, kingBalcony, regions, nobilityTrack, king, null, kingDeck, colorBonuses);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail9() throws Exception {
        new GameBoardDTO(players, currentPlayer, deck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, null, colorBonuses);
    }
    
    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail10() throws Exception {
        new GameBoardDTO(players, currentPlayer, deck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, null);
    }
    
    @Test
    public void testSetCurrentPlayer() throws Exception {
    	PlayerDTO player = new PlayerDTO("Davide", 2, true, 2, 5, 2, 0, 0, 5, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    	board.setCurrentPlayer(player);
    	
    	assertEquals(player, board.getCurrentPlayer());
    }

    @Test
    public void testGetPlayers() throws Exception {
        assertEquals(board.getPlayers(), players);
    }
    
    @Test
    public void testSetPlayers() throws Exception {
    	PlayerDTO player = new PlayerDTO("Marco", 1, false, 2, 5, 1, 1, 0, 3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    	List<PlayerDTO> players = new ArrayList<>();
    	players.add(player);
    	board.setPlayers(players);
    	
    	assertEquals(players, board.getPlayers());
    }

    @Test
    public void testGetPoliticDeck() throws Exception {
        assertEquals(board.getPoliticDeck(), deck);
    }
    
    @Test
    public void testSetPoliticDeck() throws Exception {
    	PoliticDeckDTO deck = new PoliticDeckDTO();
    	board.setPoliticDeck(deck);
    	assertEquals(deck, board.getPoliticDeck());
    }

    @Test
    public void testGetCouncillorsPool() throws Exception {
        assertEquals(board.getCouncillorsPool(), councillorsPool);
    }

    @Test (expected = NullPointerException.class)
    public void testSetCouncillorsPoolShouldFail() throws Exception {
        board.setCouncillorsPool(null);
    }

    @Test
    public void testSetCouncillorsPool() throws Exception {
        LinkedList<CouncillorDTO> cp = new LinkedList<>();
        cp.add(new CouncillorDTO(new PoliticColorDTO("c1")));
        cp.add(new CouncillorDTO(new PoliticColorDTO("c2")));
        cp.add(new CouncillorDTO(new PoliticColorDTO("c3")));
        board.setCouncillorsPool(cp);
        assertEquals(board.getCouncillorsPool(), cp);
    }

    @Test
    public void testGetKingDeck() throws Exception {
        assertEquals(board.getKingDeck(), kingDeck);
    }
    
    @Test
    public void testSetKingDeck() throws Exception {
    	List<RewardTileDTO> tiles = new ArrayList<>();
    	tiles.add(new RewardTileDTO(new CoinBonusDTO(new EmptyBonusDTO(), 3)));
    	KingDeckDTO kingDeck = new KingDeckDTO(tiles);
    	board.setKingDeck(kingDeck);
    	
    	assertEquals(kingDeck, board.getKingDeck());
    }

    @Test
    public void testGetKingBalcony() throws Exception {
        assertEquals(board.getKingBalcony(), kingBalcony);
    }
    
    @Test
    public void testSetKingBalcony() throws Exception {
    	List<CouncillorDTO> councillors = new ArrayList<>();
    	councillors.add(new CouncillorDTO(new PoliticColorDTO("verde")));
    	councillors.add(new CouncillorDTO(new PoliticColorDTO("blu")));
    	councillors.add(new CouncillorDTO(new PoliticColorDTO("rosso")));
    	BalconyDTO balcony = new BalconyDTO(councillors);
    	board.setKingBalcony(balcony);
    	
    	assertEquals(balcony, board.getKingBalcony());
    }

    @Test
    public void testGetRegions() throws Exception {
        assertEquals(board.getRegions(), regions);
    }

    @Test
    public void testSetRegions() throws Exception {
    	List<RegionDTO> regions = new ArrayList<>();
    	regions.add(new RegionDTO("Lombardia", new ArrayList<>(), new BusinessPermissionTileDeckDTO(new ArrayList<>()), new BalconyDTO(new ArrayList<>()), new EmptyBonusDTO()));
    	regions.add(new RegionDTO("Veneto", new ArrayList<>(), new BusinessPermissionTileDeckDTO(new ArrayList<>()), new BalconyDTO(new ArrayList<>()), new EmptyBonusDTO()));
    	board.setRegions(regions);
    	
    	assertEquals(regions, board.getRegions());
    }
    
    @Test
    public void testGetNobilityTrack() throws Exception {
        assertEquals(board.getNobilityTrack(), nobilityTrack);
    }
    
    @Test
    public void testSetNobilityTrack() throws Exception {
    	List<NobilityCellDTO> cells = new ArrayList<>();
    	cells.add(new NobilityCellDTO(0, new CoinBonusDTO(new EmptyBonusDTO(), 2)));
    	cells.add(new NobilityCellDTO(1, new AssistantBonusDTO(new EmptyBonusDTO(), 3)));
    	NobilityTrackDTO track = new NobilityTrackDTO(cells);
    	board.setNobilityTrack(track);
    	
    	assertEquals(track, board.getNobilityTrack());
    }

    @Test
    public void testGetKing() throws Exception {
        assertEquals(board.getKing(), king);
    }
    
    @Test
    public void setKing() throws Exception {
    	KingDTO king = new KingDTO("Roma");
    	board.setKing(king);
    	
    	assertEquals(king, board.getKing());
    	assertNotEquals(new KingDTO("Mialno"), board.getKing());
    }

    @Test
    public void testGetMarket() throws Exception {
        assertEquals(board.getMarket(), market);
    }
    
    @Test
    public void testSetMarket() throws Exception {
    	List<SellableDTO> onSale = new ArrayList<>();
    	onSale.add(new AssistantDTO(2, "Marco"));
    	onSale.add(new PoliticCardDTO(new PoliticColorDTO("nero"), 4, "Luca"));
    	onSale.add(new BusinessPermissionTileDTO(new ArrayList<>(), new NobilityBonusDTO(new EmptyBonusDTO(), 1), 5, "Davide"));
    	MarketDTO market = new MarketDTO(onSale);
    	board.setMarket(market);
    	
    	assertEquals(market, board.getMarket());
    }

    @Test
    public void testToString() throws Exception {
        board.toString();
    }
}