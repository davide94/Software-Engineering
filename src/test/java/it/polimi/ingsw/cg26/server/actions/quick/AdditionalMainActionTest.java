package it.polimi.ingsw.cg26.server.actions.quick;

import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.model.board.*;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.cards.*;
import it.polimi.ingsw.cg26.server.model.market.Market;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class AdditionalMainActionTest {

	private GameBoard gameBoard;
	
	private long token;
	
	@Before
	public void setUp() throws Exception{
		LinkedList<PoliticCard> politicCards = new LinkedList<>();
		politicCards.addAll(Collections.nCopies(16, new PoliticCard(new PoliticColor("nero"))));
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

		token = gameBoard.registerPlayer("Marco").getToken();
		gameBoard.registerPlayer("Luca");
		gameBoard.start();
	}
	
	@Test
	public void testBuildActionShouldAssignTheToken() {
		Action additionalMainAction = new AdditionalMainAction(15);
		
		assertEquals(15, additionalMainAction.getToken());
	}
	
	@Test (expected = NoRemainingActionsException.class)
	public void testApplyActionToAplayerWithoutRemainingQuickActionsShouldThrowAnException() throws Exception {
		gameBoard.getCurrentPlayer().performQuickAction();
		AdditionalMainAction action = new AdditionalMainAction(token);
		action.apply(gameBoard);
	}
	
	@Test
	public void testApplyAction() throws Exception {
		AdditionalMainAction action = new AdditionalMainAction(token);
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
