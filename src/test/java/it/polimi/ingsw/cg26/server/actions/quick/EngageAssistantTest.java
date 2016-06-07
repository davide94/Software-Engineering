package it.polimi.ingsw.cg26.server.actions.quick;

import static org.junit.Assert.*;

import java.util.*;

import it.polimi.ingsw.cg26.server.model.cards.*;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
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
import it.polimi.ingsw.cg26.server.model.market.Market;

public class EngageAssistantTest {

	private GameBoard gameBoard;
	
	@Before
	public void setUp(){
		LinkedList<PoliticCard> politicCards = new LinkedList<>();
		politicCards.add(new PoliticCard(new PoliticColor("verde")));
		politicCards.add(new PoliticCard(new PoliticColor("giallo")));
		politicCards.add(new PoliticCard(new PoliticColor("bianco")));
		politicCards.add(new PoliticCard(new PoliticColor("multicolor")));
		politicCards.add(new PoliticCard(new PoliticColor("verde")));
		politicCards.add(new PoliticCard(new PoliticColor("nero")));
		politicCards.add(new PoliticCard(new PoliticColor("viola")));
		PoliticDeck politicDeck = new PoliticDeck(politicCards);
		List<Councillor> pool = new ArrayList<Councillor>();
		Balcony kingBalcony = Balcony.createBalcony(4);
		List<Region> regions = new ArrayList<>();
		NobilityTrack track = NobilityTrack.createNobilityTrack(NobilityCell.createNobilityCell(1, null, new EmptyBonus()));
		King king = King.createKing(City.createCity("Milano", CityColor.createCityColor("Oro"), new EmptyBonus()));
		Market market = new Market();
		KingDeck kingDeck = new KingDeck(new ArrayList<RewardTile>());
		Map<CityColor, Bonus> map = new HashMap<>();
		
		this.gameBoard = GameBoard.createGameBoard(politicDeck, pool, kingBalcony, regions, track, king, market, kingDeck, map);
		
		gameBoard.registerPlayer("Marco");
	}
	
	@Test
	public void testBuildActionShouldAssignTheToken() {
		Action engageAssitant = new EngageAssistant(45);
		
		assertEquals(45, engageAssitant.getToken());
	}
	
	@Test (expected = NoRemainingActionsException.class)
	public void testApplyActionToAPlayerWithoutQuickActionsShoulThrowAnException(){
		gameBoard.getCurrentPlayer().performQuickAction();
		Action engageAssitant = new EngageAssistant(1);
		engageAssitant.apply(gameBoard);
	}
	
	@Test
	public void testApplyActionToAPlayerWith1AssistantsShouldAddOneMore(){
		Action engageAssitant = new EngageAssistant(1);
		engageAssitant.apply(gameBoard);
		
		assertFalse(gameBoard.getCurrentPlayer().canPerformQuickAction());
		assertEquals(2, gameBoard.getCurrentPlayer().getAssistantsNumber());
	}
	
	@Test
	public void testApplyActionToAPlayerWith10ShouldHave7Coins(){
		Action engageAssitant = new EngageAssistant(1);
		engageAssitant.apply(gameBoard);
		
		assertFalse(gameBoard.getCurrentPlayer().canPerformQuickAction());
		assertEquals(7, gameBoard.getCurrentPlayer().getCoinsNumber());
	}

}
