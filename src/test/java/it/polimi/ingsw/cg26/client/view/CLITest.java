package it.polimi.ingsw.cg26.client.view;

import it.polimi.ingsw.cg26.client.view.socket.ClientSocketOutView;
import it.polimi.ingsw.cg26.client.view.ui.CLI;
import it.polimi.ingsw.cg26.common.dto.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 *
 */
public class CLITest {

    private CLI cli;
    private GameBoardDTO board;

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

    //@Mock private Scanner mockedScanner;
    @Mock private PrintWriter mockedWriter;
    @Mock private ClientSocketOutView mockedOutHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        players = new LinkedList<>();
        currentPlayer = new PlayerDTO("playerName", 0, 0, 0, 0, 0, 0, 0, new LinkedList<PoliticCardDTO>(), new LinkedList<BusinessPermissionTileDTO>(), new LinkedList<BusinessPermissionTileDTO>());
        deck = new PoliticDeckDTO();
        councillorsPool = new LinkedList<>();
        kingBalcony = new BalconyDTO(new LinkedList<>());
        regions = new LinkedList<>();
        nobilityTrack = new NobilityTrackDTO(new LinkedList<>());
        king = new KingDTO("cityname");
        market = new MarketDTO(new LinkedList<>());
        kingDeck = new KingDeckDTO(new LinkedList<>());

    }

    @Test
    public void testQuit() throws Exception {
        //when(mockedScanner.nextLine()).thenReturn("Q");
        /*
        String data = "Q\r\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Scanner scanner = new Scanner(System.in);
        cli = new CLI(scanner, mockedWriter, mockedOutHandler);
        cli.update(board);

        cli.run();
        verify(mockedOutHandler).writeObject(new StaccahCommand());*/
    }

    @Test
    public void testUpdate() throws Exception {

    }
}