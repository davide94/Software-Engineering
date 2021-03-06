package it.polimi.ingsw.cg26.server.actions.main;

import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticColorDTO;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.CouncillorNotFoundException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.model.board.*;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.cards.*;
import it.polimi.ingsw.cg26.server.model.market.Market;
import it.polimi.ingsw.cg26.server.model.player.Player;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ElectAsMainActionTest {
	
	private Player currentPlayer;
	
	private GameBoard gameBoard;
	
	private Region region;
	
	private Councillor droppedCouncillor = Councillor.createCouncillor(new PoliticColor("bianco")); 
	
	private Councillor addedCouncillor = Councillor.createCouncillor(new PoliticColor("arancione"));
	
	private Region createRegion(){
		List<BusinessPermissionTile> tiles = new ArrayList<>();
		tiles.add(new BusinessPermissionTile(new ArrayList<City>(), new EmptyBonus()));
		tiles.add(new BusinessPermissionTile(new ArrayList<City>(), new EmptyBonus()));
		BusinessPermissionTileDeck bPTDeck = new BusinessPermissionTileDeck(tiles);
		
		Balcony balcony = Balcony.createBalcony(4);
		balcony.elect(droppedCouncillor);
		balcony.elect(Councillor.createCouncillor(new PoliticColor("verde")));
		balcony.elect(Councillor.createCouncillor(new PoliticColor("rosso")));
		balcony.elect(Councillor.createCouncillor(new PoliticColor("giallo")));
		return Region.createRegion("hills", new ArrayList<City>(), bPTDeck, balcony, new EmptyBonus());
	}
	
	private List<Councillor> createCouncillorsPool(){
		List<Councillor> pool = new ArrayList<>();
		pool.add(Councillor.createCouncillor(new PoliticColor("verde")));
		pool.add(addedCouncillor);
		pool.add(Councillor.createCouncillor(new PoliticColor("giallo")));
		pool.add(Councillor.createCouncillor(new PoliticColor("blu")));
		pool.add(Councillor.createCouncillor(new PoliticColor("rosso")));
		pool.add(Councillor.createCouncillor(new PoliticColor("arancione")));
		return pool;
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
		politicCards.add(new PoliticCard(new PoliticColor("viola")));
		politicCards.add(new PoliticCard(new PoliticColor("verde")));
		politicCards.add(new PoliticCard(new PoliticColor("giallo")));
		politicCards.add(new PoliticCard(new PoliticColor("bianco")));
		politicCards.add(new PoliticCard(new PoliticColor("multicolor")));
		politicCards.add(new PoliticCard(new PoliticColor("verde")));
		politicCards.add(new PoliticCard(new PoliticColor("nero")));
		politicCards.add(new PoliticCard(new PoliticColor("viola")));
		PoliticDeck politicDeck = new PoliticDeck(politicCards);
		List<Councillor> pool = createCouncillorsPool();
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

		currentPlayer = gameBoard.registerPlayer("Marco");
		gameBoard.registerPlayer("Luca");
		gameBoard.start();
	}
	
	@Test
	public void testBuildActionShouldAssignTheToken() {
		Action action = new ElectAsMainAction(createRegion().getState(), new CouncillorDTO(new PoliticColorDTO("giallo")), 42);
		
		assertEquals(42, action.getToken());
	}
	
	@Test (expected = NullPointerException.class)
	public void testConstructActionWithRegionNullShouldThrowNullPointerException(){
		new ElectAsMainAction(null, new CouncillorDTO(new PoliticColorDTO("giallo")), 16);
	}
	
	@Test (expected = NullPointerException.class)
	public void testConstructActionWithCouncillorNullShouldThrowNullPointerException(){
		new ElectAsMainAction(createRegion().getState(), null, 58);
	}
	
	@Test (expected = NoRemainingActionsException.class)
	public void testApplyActionToAPlayerWithoutRemainingMainActionsShouldThrowAnException() throws Exception {
		gameBoard.getCurrentPlayer().performMainAction();
		Action action = new ElectAsMainAction(createRegion().getState(), new CouncillorDTO(new PoliticColorDTO("verde")), currentPlayer.getToken());
		
		action.apply(gameBoard);
	}
	
	@Test (expected = CouncillorNotFoundException.class)
	public void testApplyActionWithACouncillorThatIsntInThePoolShouldThrowAnException() throws Exception {
		Action action = new ElectAsMainAction(createRegion().getState(), new CouncillorDTO(new PoliticColorDTO("bianco")), currentPlayer.getToken());
		
		action.apply(gameBoard);
	}
	
	@Test
	public void testApplyCheckChangesOnTheGameBoard() throws Exception {
		Action action = new ElectAsMainAction(region.getState(), addedCouncillor.getState(), currentPlayer.getToken());
		
		action.apply(gameBoard);
		
		assertTrue(gameBoard.getRegion(region.getState()).getBalcony().getCouncillors().contains(addedCouncillor));
		assertFalse(gameBoard.getRegion(createRegion().getState()).getBalcony().getCouncillors().contains(droppedCouncillor));
		assertTrue(gameBoard.getCouncillorsPool().contains(droppedCouncillor));
		assertEquals(6, gameBoard.getCouncillorsPool().size());
	}

	@Test
	public void testApplyCheckChangesOnThePlayer() throws Exception {
		Action action = new ElectAsMainAction(region.getState(), addedCouncillor.getState(), currentPlayer.getToken());
		
		action.apply(gameBoard);

		assertEquals(14 ,gameBoard.getCurrentPlayer().getCoinsNumber());
		assertFalse(gameBoard.getCurrentPlayer().canPerformMainAction());
	}
}
