package it.polimi.ingsw.cg26.common.dto;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 *
 */
public class PlayerDTOTest {

    private PlayerDTO player;

    private String playerName;
    private int token;
    private int victoryPoints;
    private int coins;
    private int remainingMainActions;
    private int remainingQuickActions;
    private int nobilitiCell;
    private int assistantsNumber;

    private List<PoliticCardDTO> cards;
    private List<BusinessPermissionTileDTO> tiles;
    private List<BusinessPermissionTileDTO> discardedTiles;

    @Before
    public void setUp() throws Exception {
        playerName = "playerName";
        token = 1234;
        victoryPoints = 3;
        coins = 10;
        remainingMainActions = 1;
        remainingQuickActions = 1;
        nobilitiCell = 4;
        assistantsNumber = 3;
        cards = new LinkedList<>();
        tiles = new LinkedList<>();
        discardedTiles = new LinkedList<>();
        player = new PlayerDTO(playerName, token, victoryPoints, coins, remainingMainActions, remainingQuickActions, nobilitiCell, assistantsNumber, cards, tiles, discardedTiles);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail1() throws Exception {
        new PlayerDTO(null, token, victoryPoints, coins, remainingMainActions, remainingQuickActions, nobilitiCell, assistantsNumber, cards, tiles, discardedTiles);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail2() throws Exception {
        new PlayerDTO(playerName, token, victoryPoints, coins, remainingMainActions, remainingQuickActions, nobilitiCell, assistantsNumber, null, tiles, discardedTiles);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail3() throws Exception {
        new PlayerDTO(playerName, token, victoryPoints, coins, remainingMainActions, remainingQuickActions, nobilitiCell, assistantsNumber, cards, null, discardedTiles);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail4() throws Exception {
        new PlayerDTO(playerName, token, victoryPoints, coins, remainingMainActions, remainingQuickActions, nobilitiCell, assistantsNumber, cards, tiles, null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorShouldFail5() throws Exception {
        new PlayerDTO("", token, victoryPoints, coins, remainingMainActions, remainingQuickActions, nobilitiCell, assistantsNumber, cards, tiles, discardedTiles);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorShouldFail6() throws Exception {
        new PlayerDTO(playerName, token, -1, coins, remainingMainActions, remainingQuickActions, nobilitiCell, assistantsNumber, cards, tiles, discardedTiles);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorShouldFail7() throws Exception {
        new PlayerDTO(playerName, token, victoryPoints, -1, remainingMainActions, remainingQuickActions, nobilitiCell, assistantsNumber, cards, tiles, discardedTiles);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorShouldFail8() throws Exception {
        new PlayerDTO(playerName, token, victoryPoints, coins, -1, remainingQuickActions, nobilitiCell, assistantsNumber, cards, tiles, discardedTiles);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorShouldFail9() throws Exception {
        new PlayerDTO(playerName, token, victoryPoints, coins, remainingMainActions, -1, nobilitiCell, assistantsNumber, cards, tiles, discardedTiles);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorShouldFail10() throws Exception {
        new PlayerDTO(playerName, token, victoryPoints, coins, remainingMainActions, remainingQuickActions, -1, assistantsNumber, cards, tiles, discardedTiles);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorShouldFail11() throws Exception {
        new PlayerDTO(playerName, token, victoryPoints, coins, remainingMainActions, remainingQuickActions, nobilitiCell, -1, cards, tiles, discardedTiles);
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals(player.getName(), playerName);
    }

    @Test
    public void testGetToken() throws Exception {
        assertEquals(player.getToken(), token);
    }

    @Test
    public void testGetVictoryPoints() throws Exception {
        assertEquals(player.getVictoryPoints(), victoryPoints);
    }

    @Test
    public void testGetCoins() throws Exception {
        assertEquals(player.getCoins(), coins);
    }

    @Test
    public void testGetRemainingMainActions() throws Exception {
        assertEquals(player.getRemainingMainActions(), remainingMainActions);
    }

    @Test
    public void testGetRemainingQuickActions() throws Exception {
        assertEquals(player.getRemainingQuickActions(), remainingQuickActions);
    }

    @Test
    public void testGetNobilityCell() throws Exception {
        assertEquals(player.getNobilityCell(), nobilitiCell);
    }

    @Test
    public void testGetAssistantsNumber() throws Exception {
        assertEquals(player.getAssistantsNumber(), assistantsNumber);
    }

    @Test
    public void testGetCards() throws Exception {
        assertEquals(player.getCards(), cards);
    }

    @Test
    public void testGetTiles() throws Exception {
        assertEquals(player.getTiles(), tiles);
    }

    @Test
    public void testGetDiscardedTiles() throws Exception {
        assertEquals(player.getDiscardedTiles(), discardedTiles);
    }
}