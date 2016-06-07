package it.polimi.ingsw.cg26.server.actions.quick;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticColorDTO;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.server.exceptions.NotExistingCouncillorException;
import it.polimi.ingsw.cg26.server.model.board.Balcony;
import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.board.CityColor;
import it.polimi.ingsw.cg26.server.model.board.Councillor;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.board.King;
import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.board.NobilityTrack;
import it.polimi.ingsw.cg26.server.model.board.Region;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTileDeck;
import it.polimi.ingsw.cg26.server.model.cards.KingDeck;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import it.polimi.ingsw.cg26.server.model.market.Market;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class ElectAsQuickActionTest {
	
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
		return pool;
	}
	
	@Before
	public void setUp(){
		LinkedList<PoliticCard> politicCards = new LinkedList<>();
		politicCards.add(new PoliticCard(new PoliticColor("c1")));
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
		
		List<Assistant> assistants = new ArrayList<>();
		for(int i=0; i<3; i++)
			assistants.add(new Assistant());
		//Player player1 = new Player(1, "Marco", NobilityCell.createNobilityCell(1, null, new EmptyBonus()), 5, new ArrayList<PoliticCard>(), assistants);
		gameBoard.registerPlayer("Marco");
	}
	
	@Test
	public void testBuildActionShouldAssignTheToken() {
		Action action = new ElectAsQuickAction(createRegion().getState(), new CouncillorDTO(new PoliticColorDTO("verde")), 38);
		
		assertEquals(38, action.getToken());
	}
	
	@Test (expected = NullPointerException.class)
	public void testConstructActionWithRegionNullShouldThrowNullPointerException(){
		new ElectAsQuickAction(null, new CouncillorDTO(new PoliticColorDTO("verde")), 16);
	}
	
	@Test (expected = NullPointerException.class)
	public void testConstructActionWithCouncillorNullShouldThrowNullPointerException(){
		new ElectAsQuickAction(createRegion().getState(), null, 58);
	}
	
	@Test (expected = NoRemainingActionsException.class)
	public void testApplyActionToAPlayerWithoutRemainingQickActionsShouldThrowAnException(){
		gameBoard.getCurrentPlayer().performQuickAction();
		Action action = new ElectAsQuickAction(createRegion().getState(), new CouncillorDTO(new PoliticColorDTO("verde")), 1);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = NoRemainingAssistantsException.class)
	public void testApplyActionToAPlayerWithoutRemainingAssistantsShouldThrowAnException(){
		gameBoard.getCurrentPlayer().takeAssistants(3);
		Action action = new ElectAsQuickAction(createRegion().getState(), new CouncillorDTO(new PoliticColorDTO("arancione")), 1);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = NotExistingCouncillorException.class)
	public void testApplyActionWithACouncillorThatIsntInThePoolShouldThrowAnException(){
		Action action = new ElectAsQuickAction(createRegion().getState(), new CouncillorDTO(new PoliticColorDTO("bianco")), 1);
		
		action.apply(gameBoard);
	}

	@Test
	public void testApplyCheckChangesOnTheGameBoard(){
		Action action = new ElectAsQuickAction(region.getState(), addedCouncillor.getState(), 1);
		
		action.apply(gameBoard);
		
		assertTrue(gameBoard.getRegion(region.getState()).getBalcony().getCouncillors().contains(addedCouncillor));
		assertFalse(gameBoard.getRegion(createRegion().getState()).getBalcony().getCouncillors().contains(droppedCouncillor));
		assertFalse(gameBoard.getCouncillorsPool().contains(addedCouncillor));
		assertTrue(gameBoard.getCouncillorsPool().contains(droppedCouncillor));
	}
	
	@Test
	public void testApplyCheckChangesOnThePlayer(){
		Action action = new ElectAsQuickAction(region.getState(), addedCouncillor.getState(), 1);
		
		action.apply(gameBoard);

		assertEquals(2 ,gameBoard.getCurrentPlayer().getAssistantsNumber());
		assertFalse(gameBoard.getCurrentPlayer().canPerformQuickAction());
	}
}
