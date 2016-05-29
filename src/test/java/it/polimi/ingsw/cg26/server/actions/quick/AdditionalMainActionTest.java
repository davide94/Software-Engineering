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
import it.polimi.ingsw.cg26.server.model.market.Market;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class AdditionalMainActionTest {

	private GameBoard gameBoard;
	
	/*private PoliticDeck createPoliticDeck(){
		List<PoliticCard> politicCards = new ArrayList<>();
		politicCards.add(new PoliticCard(new PoliticColor("verde")));
		politicCards.add(new PoliticCard(new PoliticColor("arancione")));
		politicCards.add(new PoliticCard(new PoliticColor("giallo")));
		politicCards.add(new PoliticCard(new PoliticColor("blu")));
		politicCards.add(new PoliticCard(new PoliticColor("rosa")));
		
		return new PoliticDeck(politicCards);
	}
	
	private List<Councillor> createCouncillorsPool(){
		List<Councillor> pool = new ArrayList<>();
		pool.add(Councillor.createCouncillor(new PoliticColor("verde")));
		pool.add(Councillor.createCouncillor(new PoliticColor("arancione")));
		pool.add(Councillor.createCouncillor(new PoliticColor("giallo")));
		pool.add(Councillor.createCouncillor(new PoliticColor("blu")));
		pool.add(Councillor.createCouncillor(new PoliticColor("rosso")));
		return pool;
	}*/
	
	@Before
	public void setUp(){
		LinkedList<PoliticCard> politicCards = new LinkedList<>();
		politicCards.add(new PoliticCard(new PoliticColor("c1")));
		PoliticDeck politicDeck = new PoliticDeck(politicCards);
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
		Action additionalMainAction = new AdditionalMainAction(15);
		
		assertEquals(15, additionalMainAction.getToken());
	}
	
	@Test (expected = NoRemainingActionsException.class)
	public void testApplyActionToAplayerWithoutRemainingQuickActionsShouldThrowAnException(){
		gameBoard.getCurrentPlayer().performQuickAction();
		AdditionalMainAction action = new AdditionalMainAction(1);
		action.apply(gameBoard);
	}
	
	@Test
	public void testApplyAction(){
		AdditionalMainAction action = new AdditionalMainAction(1);
		action.apply(gameBoard);
		gameBoard.getCurrentPlayer().performMainAction();
		gameBoard.getCurrentPlayer().performMainAction();
		
		assertFalse(gameBoard.getCurrentPlayer().canPerformQuickAction());
		assertEquals(0, gameBoard.getCurrentPlayer().getAssistantsNumber());
		assertFalse(gameBoard.getCurrentPlayer().canPerformMainAction());
	}

}
