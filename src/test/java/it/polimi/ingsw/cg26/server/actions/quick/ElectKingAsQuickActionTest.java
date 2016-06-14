package it.polimi.ingsw.cg26.server.actions.quick;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
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
import it.polimi.ingsw.cg26.server.model.cards.KingDeck;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import it.polimi.ingsw.cg26.server.model.market.Market;

public class ElectKingAsQuickActionTest {

	private GameBoard gameBoard;
	
	private Councillor droppedCouncillor = Councillor.createCouncillor(new PoliticColor("verde")); 
	
	private Councillor addedCouncillor = Councillor.createCouncillor(new PoliticColor("blu"));
	
	private long token;
	
	private List<Councillor> createCouncillorsPool(){
		List<Councillor> pool = new ArrayList<>();
		pool.add(Councillor.createCouncillor(new PoliticColor("nero")));
		pool.add(addedCouncillor);
		pool.add(Councillor.createCouncillor(new PoliticColor("giallo")));
		pool.add(Councillor.createCouncillor(new PoliticColor("blu")));
		pool.add(Councillor.createCouncillor(new PoliticColor("rosso")));
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
		PoliticDeck politicDeck = new PoliticDeck(politicCards);
		List<Councillor> pool = createCouncillorsPool();
		Balcony kingBalcony = Balcony.createBalcony(4);
		kingBalcony.elect(droppedCouncillor);
		kingBalcony.elect(Councillor.createCouncillor(new PoliticColor("verde")));
		kingBalcony.elect(Councillor.createCouncillor(new PoliticColor("rosso")));
		kingBalcony.elect(Councillor.createCouncillor(new PoliticColor("giallo")));
		List<Region> regions = new ArrayList<>();
		NobilityTrack track = NobilityTrack.createNobilityTrack(NobilityCell.createNobilityCell(1, null, new EmptyBonus()));
		King king = King.createKing(City.createCity("Milano", CityColor.createCityColor("Oro"), new EmptyBonus()));
		Market market = new Market();
		KingDeck kingDeck = new KingDeck(new ArrayList<RewardTile>());
		Map<CityColor, Bonus> map = new HashMap<>();
		
		this.gameBoard = GameBoard.createGameBoard(politicDeck, pool, kingBalcony, regions, track, king, market, kingDeck, map);
		
		token = gameBoard.registerPlayer("Marco");
		gameBoard.start();
	}
	
	@Test
	public void testBuildActionShouldAssignTheToken() {
		Action action = new ElectKingAsQuickAction(addedCouncillor.getState(), 42);
		
		assertEquals(42, action.getToken());
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildActionWithCouncillorNullShouldThrowExcpetion() throws Exception {
		new ElectKingAsQuickAction(null, token);
	}
	
	@Test (expected = NoRemainingActionsException.class)
	public void testApplyActionWithoutRemainingQuickActionShouldThrowException() throws Exception {
		gameBoard.getCurrentPlayer().performQuickAction();
		Action action = new ElectKingAsQuickAction(addedCouncillor.getState(), token);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = NoRemainingAssistantsException.class)
	public void testApplyActionWithoutRemainingAssistantShouldThrowException() throws Exception {
		gameBoard.getCurrentPlayer().takeAssistants(1);
		Action action = new ElectKingAsQuickAction(addedCouncillor.getState(), token);
		
		action.apply(gameBoard);
	}
	
	@Test
	public void testApplyActionCheckChanges() throws Exception {
		Action action = new ElectKingAsQuickAction(addedCouncillor.getState(), token);
		action.apply(gameBoard);
		
		assertTrue(gameBoard.getCouncillorsPool().contains(droppedCouncillor));
		List<Councillor> councillors = new ArrayList<>();
		councillors.add(Councillor.createCouncillor(new PoliticColor("verde")));
		councillors.add(Councillor.createCouncillor(new PoliticColor("rosso")));
		councillors.add(Councillor.createCouncillor(new PoliticColor("giallo")));
		councillors.add(addedCouncillor);
		assertTrue(gameBoard.getKingBalcony().getCouncillors().containsAll(councillors));
		assertEquals(5, gameBoard.getCouncillorsPool().size());
		assertEquals(4, gameBoard.getKingBalcony().getCouncillors().size());
		assertFalse(gameBoard.getCurrentPlayer().canPerformQuickAction());
		assertEquals(0, gameBoard.getCurrentPlayer().getAssistantsNumber());
	}

}
