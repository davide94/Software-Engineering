package it.polimi.ingsw.cg26.server.actions.quick;

import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.model.board.*;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.cards.*;
import it.polimi.ingsw.cg26.server.model.market.Market;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class FoldQuickActionTest {

	private GameBoard gameBoard;
	
	@Before
	public void setUp() throws Exception {
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

		gameBoard.registerPlayer("Marco");
		gameBoard.registerPlayer("Luca");
		gameBoard.start();
	}
	
	@Test
	public void testBuildActionShouldAssignTheToken() {
		Action foldQuickAction = new FoldQuickAction(47);
		
		assertEquals(47, foldQuickAction.getToken());
	}
	
	@Test (expected = NoRemainingActionsException.class)
	public void testApplyActionToAPlayerWithoutRemainingQuickActionShoulThrowAnException() throws Exception {
		gameBoard.getCurrentPlayer().performQuickAction();
		Action foldQuickAction = new FoldQuickAction(1);
		foldQuickAction.apply(gameBoard);
	}
	
	@Test
	public void testApplyAction() throws Exception {
		Action foldQuickAction = new FoldQuickAction(1);
		foldQuickAction.apply(gameBoard);
		
		assertFalse(gameBoard.getCurrentPlayer().canPerformQuickAction());
	}

}
