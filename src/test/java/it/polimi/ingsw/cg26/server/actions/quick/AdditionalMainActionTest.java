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
import it.polimi.ingsw.cg26.server.model.player.Assistant;

public class AdditionalMainActionTest {

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
		Action additionalMainAction = new AdditionalMainAction(15);
		
		assertEquals(15, additionalMainAction.getToken());
	}
	
	@Test (expected = NoRemainingActionsException.class)
	public void testApplyActionToAplayerWithoutRemainingQuickActionsShouldThrowAnException() throws Exception {
		gameBoard.getCurrentPlayer().performQuickAction();
		AdditionalMainAction action = new AdditionalMainAction(1);
		action.apply(gameBoard);
	}
	
	@Test
	public void testApplyAction() throws Exception {
		AdditionalMainAction action = new AdditionalMainAction(1);
		gameBoard.getCurrentPlayer().addAssistant(new Assistant());
		gameBoard.getCurrentPlayer().addAssistant(new Assistant());
		action.apply(gameBoard);
		gameBoard.getCurrentPlayer().performMainAction();
		gameBoard.getCurrentPlayer().performMainAction();
		
		assertFalse(gameBoard.getCurrentPlayer().canPerformQuickAction());
		assertEquals(0, gameBoard.getCurrentPlayer().getAssistantsNumber());
		assertFalse(gameBoard.getCurrentPlayer().canPerformMainAction());
	}

}
