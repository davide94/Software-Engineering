package it.polimi.ingsw.cg26.common.dto;

import it.polimi.ingsw.cg26.server.model.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

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

    private GameBoardDTO board;

    @Before
    public void setUp() throws Exception {
        players = new LinkedList<>();
        currentPlayer = new PlayerDTO("playerName", 0, 0, 0, 0, 0, 0, 0, new LinkedList<PoliticCardDTO>(), new LinkedList<BusinessPermissionTileDTO>(), new LinkedList<BusinessPermissionTileDTO>());
        deck = new PoliticDeckDTO();
        councillorsPool = new LinkedList<>();
        kingBalcony = new BalconyDTO(new LinkedList<>());
        regions = new LinkedList<>();
        nobilityTrack = new NobilityTrackDTO(new LinkedList<>());
        king = new KingDTO("cityname");
        market = new MarketDTO();
        kingDeck = new KingDeckDTO(new LinkedList<>());

        board = new GameBoardDTO(players, currentPlayer, deck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck);

    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail1() throws Exception {
        new GameBoardDTO(null, currentPlayer, deck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail2() throws Exception {
        new GameBoardDTO(players, currentPlayer, null, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail3() throws Exception {
        new GameBoardDTO(players, currentPlayer, deck, null, kingBalcony, regions, nobilityTrack, king, market, kingDeck);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail4() throws Exception {
        new GameBoardDTO(players, currentPlayer, deck, councillorsPool, null, regions, nobilityTrack, king, market, kingDeck);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail5() throws Exception {
        new GameBoardDTO(players, currentPlayer, deck, councillorsPool, kingBalcony, null, nobilityTrack, king, market, kingDeck);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail6() throws Exception {
        new GameBoardDTO(players, currentPlayer, deck, councillorsPool, kingBalcony, regions, null, king, market, kingDeck);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail7() throws Exception {
        new GameBoardDTO(players, currentPlayer, deck, councillorsPool, kingBalcony, regions, nobilityTrack, null, market, kingDeck);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail8() throws Exception {
        new GameBoardDTO(players, currentPlayer, deck, councillorsPool, kingBalcony, regions, nobilityTrack, king, null, kingDeck);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail9() throws Exception {
        new GameBoardDTO(players, currentPlayer, deck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, null);
    }

    @Test
    public void testGetPlayers() throws Exception {
        assertEquals(board.getPlayers(), players);
    }

    @Test
    public void testGetPoliticDeck() throws Exception {
        assertEquals(board.getPoliticDeck(), deck);
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
    public void testGetKingBalcony() throws Exception {
        assertEquals(board.getKingBalcony(), kingBalcony);
    }

    @Test
    public void testGetRegions() throws Exception {
        assertEquals(board.getRegions(), regions);
    }

    @Test
    public void testGetNobilityTrack() throws Exception {
        assertEquals(board.getNobilityTrack(), nobilityTrack);
    }

    @Test
    public void testGetKing() throws Exception {
        assertEquals(board.getKing(), king);
    }

    @Test
    public void testGetMarket() throws Exception {
        assertEquals(board.getMarket(), market);
    }

    @Test
    public void testToString() throws Exception {
        board.toString();
    }
}