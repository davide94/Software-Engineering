package it.polimi.ingsw.cg26.server.actions.quick;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import it.polimi.ingsw.cg26.server.model.cards.KingDeck;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import it.polimi.ingsw.cg26.server.model.market.Market;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class FoldQuickActionTest {

	private GameBoard gameBoard;
	
	@Before
	public void setUp(){
		PoliticDeck politicDeck = new PoliticDeck(new ArrayList<PoliticCard>());
		List<Councillor> pool = new ArrayList<Councillor>();
		Balcony kingBalcony = Balcony.createBalcony(4);
		List<Region> regions = new ArrayList<>();
		NobilityTrack track = NobilityTrack.createNobilityTrack(NobilityCell.createNobilityCell(1, null, new RewardTile(new ArrayList<Bonus>())));
		King king = King.createKing(City.createCity("Milano", CityColor.createCityColor("Oro"), new RewardTile(new ArrayList<Bonus>())));
		Market market = new Market();
		KingDeck kingDeck = new KingDeck(new ArrayList<RewardTile>());
		Map<CityColor, RewardTile> map = new HashMap<>();
		
		this.gameBoard = GameBoard.createGameBoard(politicDeck, pool, kingBalcony, regions, track, king, market, kingDeck, map);
		
		List<Assistant> assistants = new ArrayList<>();
		for(int i=0; i<3; i++)
			assistants.add(new Assistant());
		Player player1 = new Player(1, "Marco", NobilityCell.createNobilityCell(1, null, new RewardTile(new ArrayList<Bonus>())), 5, new ArrayList<PoliticCard>(), assistants);
		Player player2 = new Player(2, "Gianni", NobilityCell.createNobilityCell(2, null, new RewardTile(new ArrayList<Bonus>())), 8, new ArrayList<PoliticCard>(), new ArrayList<Assistant>());
		gameBoard.registerPlayer(player1);
		gameBoard.registerPlayer(player2);
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
