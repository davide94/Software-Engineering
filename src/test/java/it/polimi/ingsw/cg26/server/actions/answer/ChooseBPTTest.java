package it.polimi.ingsw.cg26.server.actions.answer;

import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.model.board.*;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.bonus.CoinBonus;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.bonus.TakeBPTBonus;
import it.polimi.ingsw.cg26.server.model.cards.*;
import it.polimi.ingsw.cg26.server.model.market.Market;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ChooseBPTTest {

	private Region region;
	
	private GameBoard gameBoard;

	private long token;
	
	private BusinessPermissionTile tile; 
	
	private Region createRegion(){
		tile = new BusinessPermissionTile(new ArrayList<City>(), new CoinBonus(new EmptyBonus(), 5));
		List<BusinessPermissionTile> tiles = new ArrayList<>();
		tiles.add(new BusinessPermissionTile(new ArrayList<City>(), new EmptyBonus()));
		tiles.add(tile);
		BusinessPermissionTileDeck bPTDeck = new BusinessPermissionTileDeck(tiles);
		
		Balcony balcony = Balcony.createBalcony(4);
		balcony.elect(Councillor.createCouncillor(new PoliticColor("bianco")));
		balcony.elect(Councillor.createCouncillor(new PoliticColor("verde")));
		balcony.elect(Councillor.createCouncillor(new PoliticColor("blu")));
		balcony.elect(Councillor.createCouncillor(new PoliticColor("nero")));
		return Region.createRegion("hills", new ArrayList<City>(), bPTDeck, balcony, new EmptyBonus());
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
		politicCards.add(new PoliticCard(new PoliticColor("blu")));
		politicCards.add(new PoliticCard(new PoliticColor("blu")));
		politicCards.add(new PoliticCard(new PoliticColor("blu")));
		politicCards.add(new PoliticCard(new PoliticColor("blu")));
		politicCards.add(new PoliticCard(new PoliticColor("blu")));
		politicCards.add(new PoliticCard(new PoliticColor("blu")));
		politicCards.add(new PoliticCard(new PoliticColor("blu")));
		politicCards.add(new PoliticCard(new PoliticColor("blu")));
		politicCards.add(new PoliticCard(new PoliticColor("blu")));
		politicCards.add(new PoliticCard(new PoliticColor("blu")));
		politicCards.add(new PoliticCard(new PoliticColor("blu")));
		politicCards.add(new PoliticCard(new PoliticColor("blu")));
		politicCards.add(new PoliticCard(new PoliticColor("blu")));
		politicCards.add(new PoliticCard(new PoliticColor("blu")));
		PoliticDeck politicDeck = new PoliticDeck(politicCards);
		List<Councillor> pool = new ArrayList<>();
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

		token = gameBoard.registerPlayer("Marco").getToken(); //create player with 10 coins, 0 nobility, 1 assistant, 6 politic cards
		gameBoard.registerPlayer("Luca");
		gameBoard.registerPlayer("Davide");
		gameBoard.start();
		Bonus takeBPTBonus = new TakeBPTBonus(new EmptyBonus(), 1);
		takeBPTBonus.apply(gameBoard.getCurrentPlayer());
	}
	
	@Test
	public void testBuildActionShouldAssignTheToken() {
		Action action = new ChooseBPT(region.getState(), 1, token);
		
		assertEquals(token, action.getToken());
	}
	
	@Test (expected = NoRemainingActionsException.class)
	public void testApplyActionWithoutRemainingChooseActionShouldThrowException() throws Exception {
		gameBoard.getCurrentPlayer().performChooseAction();
		Action action = new ChooseBPT(region.getState(), 0, token);
		
		action.apply(gameBoard);
	}
	
	@Test
	public void testApplyActionCheckChanges() throws Exception {
		Action action = new ChooseBPT(region.getState(), 1, token);
		action.apply(gameBoard);
		
		assertEquals(15, gameBoard.getCurrentPlayer().getCoinsNumber());
		assertEquals(tile, gameBoard.getCurrentPlayer().hasPermissionTile(tile.getState()));
		assertFalse(gameBoard.getCurrentPlayer().canPerformChooseAction());
		assertTrue(gameBoard.getCurrentPlayer().getPendingRequest().isEmpty());
	}

}
