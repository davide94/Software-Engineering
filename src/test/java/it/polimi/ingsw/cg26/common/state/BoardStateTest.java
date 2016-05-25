package it.polimi.ingsw.cg26.common.state;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 *
 */
public class BoardStateTest {

    private LinkedList<PlayerState> players;
    private PoliticDeckState deck;
    private List<CouncillorState> councillorsPool;
    private BalconyState kingBalcony;
    private List<RegionState> regions;
    private NobilityTrackState nobilityTrack;
    private KingState king;
    private MarketState market;
    private KingDeckState kingDeck;

    private BoardState board;

    @Before
    public void setUp() throws Exception {
        players = new LinkedList<>();
        deck = new PoliticDeckState();
        councillorsPool = new LinkedList<>();
        kingBalcony = new BalconyState(new LinkedList<>());
        regions = new LinkedList<>();
        nobilityTrack = new NobilityTrackState(new LinkedList<>());
        king = new KingState("cityname");
        market = new MarketState();
        kingDeck = new KingDeckState(new LinkedList<>());

        board = new BoardState(players, deck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck);

    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail1() throws Exception {
        new BoardState(null, deck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail2() throws Exception {
        new BoardState(players, null, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail3() throws Exception {
        new BoardState(players, deck, null, kingBalcony, regions, nobilityTrack, king, market, kingDeck);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail4() throws Exception {
        new BoardState(players, deck, councillorsPool, null, regions, nobilityTrack, king, market, kingDeck);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail5() throws Exception {
        new BoardState(players, deck, councillorsPool, kingBalcony, null, nobilityTrack, king, market, kingDeck);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail6() throws Exception {
        new BoardState(players, deck, councillorsPool, kingBalcony, regions, null, king, market, kingDeck);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail7() throws Exception {
        new BoardState(players, deck, councillorsPool, kingBalcony, regions, nobilityTrack, null, market, kingDeck);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail8() throws Exception {
        new BoardState(players, deck, councillorsPool, kingBalcony, regions, nobilityTrack, king, null, kingDeck);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail9() throws Exception {
        new BoardState(players, deck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, null);
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
        LinkedList<CouncillorState> cp = new LinkedList<>();
        cp.add(new CouncillorState(new PoliticColorState("c1")));
        cp.add(new CouncillorState(new PoliticColorState("c2")));
        cp.add(new CouncillorState(new PoliticColorState("c3")));
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