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
import it.polimi.ingsw.cg26.server.model.player.Player;

public class FoldQuickActionTest {

	private GameBoard gameBoard;
	
	@Before
	public void setUp(){
		LinkedList<PoliticCard> politicCards = new LinkedList<>();
		politicCards.add(new PoliticCard(new PoliticColor("c1")));
		politicCards.add(new PoliticCard(new PoliticColor("c2")));
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
		
		List<Assistant> assistants = new ArrayList<>();
		for(int i=0; i<3; i++)
			assistants.add(new Assistant());
		//Player player1 = new Player(1, "Marco", NobilityCell.createNobilityCell(1, null, new EmptyBonus()), 5, new ArrayList<PoliticCard>(), assistants);
		//Player player2 = new Player(2, "Gianni", NobilityCell.createNobilityCell(2, null, new EmptyBonus()), 8, new ArrayList<PoliticCard>(), new ArrayList<Assistant>());
		gameBoard.registerPlayer("Marco");
		gameBoard.registerPlayer("Gianni");
	}
	
	@Test
	public void testBuildActionShouldAssignTheToken() {
		Action foldQuickAction = new FoldQuickAction(47);
		
		assertEquals(47, foldQuickAction.getToken());
	}
	
	@Test (expected = NoRemainingActionsException.class)
	public void testApplyActionToAPlayerWithoutRemainingQuickActionShoulThrowAnException(){
		gameBoard.getCurrentPlayer().performQuickAction();
		Action foldQuickAction = new FoldQuickAction(1);
		foldQuickAction.apply(gameBoard);
	}
	
	@Test
	public void testApplyAction(){
		Action foldQuickAction = new FoldQuickAction(1);
		foldQuickAction.apply(gameBoard);
		
		assertFalse(gameBoard.getCurrentPlayer().canPerformQuickAction());
	}

}
