package it.polimi.ingsw.cg26.server.actions.answer;

import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.update.request.PlayerBPTRequest;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.InvalidTileException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.model.board.*;
import it.polimi.ingsw.cg26.server.model.bonus.*;
import it.polimi.ingsw.cg26.server.model.cards.*;
import it.polimi.ingsw.cg26.server.model.market.Market;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ChoosePlayerBPTTest {

	private Region region;
	
	private GameBoard gameBoard;

	private long token;
	
	private BusinessPermissionTile tile;
	
	private BusinessPermissionTile usedTile;
	
	private List<BusinessPermissionTileDTO> tiles;
	
	private Region createRegion(){
		
		List<BusinessPermissionTile> tiles = new ArrayList<>();
		BusinessPermissionTileDeck bPTDeck = new BusinessPermissionTileDeck(tiles);
		
		Balcony balcony = Balcony.createBalcony(4);
		balcony.elect(Councillor.createCouncillor(new PoliticColor("bianco")));
		balcony.elect(Councillor.createCouncillor(new PoliticColor("verde")));
		balcony.elect(Councillor.createCouncillor(new PoliticColor("blu")));
		balcony.elect(Councillor.createCouncillor(new PoliticColor("nero")));
		return Region.createRegion("hills", new ArrayList<City>(), bPTDeck, balcony, new EmptyBonus());
	}

	@Before
	public void setUp() throws Exception {
		LinkedList<PoliticCard> politicCards = new LinkedList<>();
		politicCards.add(new PoliticCard(new PoliticColor("verde")));
		politicCards.add(new PoliticCard(new PoliticColor("giallo")));
		politicCards.add(new PoliticCard(new PoliticColor("bianco")));
		politicCards.add(new PoliticCard(new PoliticColor("multicolor")));
		politicCards.add(new PoliticCard(new PoliticColor("verde")));
		politicCards.add(new PoliticCard(new PoliticColor("nero")));
		politicCards.add(new PoliticCard(new PoliticColor("blu")));
		PoliticDeck politicDeck = new PoliticDeck(politicCards);
		List<Councillor> pool = new ArrayList<>();
		Balcony kingBalcony = Balcony.createBalcony(4);
		List<Region> regions = new ArrayList<>();
		NobilityTrack track = NobilityTrack.createNobilityTrack(NobilityCell.createNobilityCell(1, null, new EmptyBonus()));
		King king = King.createKing(City.createCity("Milano", CityColor.createCityColor("Oro"), new EmptyBonus()));
		Market market = new Market();
		KingDeck kingDeck = new KingDeck(new ArrayList<RewardTile>());
		Map<CityColor, Bonus> map = new HashMap<>();
		
		region = createRegion();
		regions.add(region);
		
		this.gameBoard = GameBoard.createGameBoard(politicDeck, pool, kingBalcony, regions, track, king, market, kingDeck, map);

		token = gameBoard.registerPlayer("Marco").getToken(); //create player with 10 coins, 0 nobility, 1 assistant, 6 politic cards
		gameBoard.start();
		tile = new BusinessPermissionTile(new ArrayList<City>(), new CoinBonus(new EmptyBonus(), 5));
		gameBoard.getCurrentPlayer().addPermissionTile(tile);
		usedTile = new BusinessPermissionTile(new ArrayList<City>(), new VictoryBonus(new AssistantBonus(new EmptyBonus(), 2), 4));
		gameBoard.getCurrentPlayer().addPermissionTile(usedTile);
		gameBoard.getCurrentPlayer().useBPT(usedTile);
		Bonus takePlayerBPTBonus = new TakePlayerBPTBonus(new EmptyBonus(), 1);
		takePlayerBPTBonus.apply(gameBoard.getCurrentPlayer());
		
		tiles = new ArrayList<>(); 
	}
	
	@Test
	public void testBuildActionShouldAssignTheToken() {
		Action action = new ChoosePlayerBPT(tiles, token);
		
		assertEquals(token, action.getToken());
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildActionWithTilesNullShouldThrowException() throws Exception {
		new ChoosePlayerBPT(null, token);
	}
	
	@Test (expected = NoRemainingActionsException.class)
	public void testApplyActionWithoutRemainingChooseActionShouldThrowException() throws Exception {
		gameBoard.getCurrentPlayer().performChooseAction();
		tiles.add(tile.getState());
		Action action = new ChoosePlayerBPT(tiles, token);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = InvalidTileException.class)
	public void testApplyActionWith2EqualBPTShouldThrowException() throws Exception {
		tiles.add(tile.getState());
		tiles.add(tile.getState());
		Action action = new ChoosePlayerBPT(tiles, token);
		
		action.apply(gameBoard);
	}

	@Test
	public void testApplyActionWithFaceUpTileCheckChanges() throws Exception {
		tiles.add(tile.getState());
		Action action = new ChoosePlayerBPT(tiles, token);
		action.apply(gameBoard);
		
		assertEquals(15, gameBoard.getCurrentPlayer().getCoinsNumber());
		assertEquals(tile, gameBoard.getCurrentPlayer().hasPermissionTile(tile.getState()));
		assertFalse(gameBoard.getCurrentPlayer().canPerformChooseAction());
		assertTrue(gameBoard.getCurrentPlayer().getPendingRequest().isEmpty());
	}
	
	@Test
	public void testApplyActionWithFaceDownTileCheckChanges() throws Exception {
		tiles.add(usedTile.getState());
		Action action = new ChoosePlayerBPT(tiles, token);
		action.apply(gameBoard);
		
		assertEquals(4, gameBoard.getCurrentPlayer().getVictoryPoints());
		assertEquals(3, gameBoard.getCurrentPlayer().getAssistantsNumber());
		assertEquals(tile, gameBoard.getCurrentPlayer().hasPermissionTile(tile.getState()));
		assertFalse(gameBoard.getCurrentPlayer().canPerformChooseAction());
		assertTrue(gameBoard.getCurrentPlayer().getPendingRequest().isEmpty());
	}
	
	@Test
	public void testApplyActionWith2TilesCheckChanges() throws Exception {
		gameBoard.getCurrentPlayer().performChooseAction();
		gameBoard.getCurrentPlayer().removePendingRequest(new PlayerBPTRequest(1));
		Bonus bonus = new TakePlayerBPTBonus(new EmptyBonus(), 2);
		bonus.apply(gameBoard.getCurrentPlayer());
		tiles.add(tile.getState());
		tiles.add(usedTile.getState());
		Action action = new ChoosePlayerBPT(tiles, token);
		action.apply(gameBoard);
		
		assertEquals(15, gameBoard.getCurrentPlayer().getCoinsNumber());
		assertEquals(4, gameBoard.getCurrentPlayer().getVictoryPoints());
		assertEquals(3, gameBoard.getCurrentPlayer().getAssistantsNumber());
		assertEquals(tile, gameBoard.getCurrentPlayer().hasPermissionTile(tile.getState()));
		assertFalse(gameBoard.getCurrentPlayer().canPerformChooseAction());
		assertTrue(gameBoard.getCurrentPlayer().getPendingRequest().isEmpty());
	}
}
