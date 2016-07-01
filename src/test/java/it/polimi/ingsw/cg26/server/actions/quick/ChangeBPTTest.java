package it.polimi.ingsw.cg26.server.actions.quick;

import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
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

public class ChangeBPTTest {

	private GameBoard gameBoard;
	
	private Region createRegion(){
		List<BusinessPermissionTile> tiles = new ArrayList<>();
		List<City> cities1 = new ArrayList<>();
		cities1.add(City.createCity("Milano", CityColor.createCityColor("giallo"), new EmptyBonus()));
		cities1.add(City.createCity("Roma", CityColor.createCityColor("verde"), new EmptyBonus()));
		tiles.add(new BusinessPermissionTile(cities1, new EmptyBonus()));
		
		List<City> cities2 = new ArrayList<>();
		cities2.add(City.createCity("Firenze", CityColor.createCityColor("rosso"), new EmptyBonus()));
		cities2.add(City.createCity("Napoli", CityColor.createCityColor("blu"), new EmptyBonus()));
		tiles.add(new BusinessPermissionTile(cities2, new EmptyBonus()));
		
		List<City> cities3 = new ArrayList<>();
		cities3.add(City.createCity("Milano", CityColor.createCityColor("giallo"), new EmptyBonus()));
		cities3.add(City.createCity("Firenze", CityColor.createCityColor("rosso"), new EmptyBonus()));
		tiles.add(new BusinessPermissionTile(cities3, new EmptyBonus()));
		
		List<City> cities4 = new ArrayList<>();
		cities4.add(City.createCity("Napoli", CityColor.createCityColor("blu"),new EmptyBonus()));
		cities4.add(City.createCity("Roma", CityColor.createCityColor("verde"), new EmptyBonus()));
		tiles.add(new BusinessPermissionTile(cities4, new EmptyBonus()));
		
		BusinessPermissionTileDeck bPTDeck = new BusinessPermissionTileDeck(tiles);
		return Region.createRegion("hills", new ArrayList<City>(), bPTDeck, Balcony.createBalcony(4), new EmptyBonus());
	}
	
	@Before
	public void setUp() throws Exception {
		LinkedList<PoliticCard> politicCards = new LinkedList<>();
		politicCards.addAll(Collections.nCopies(22, new PoliticCard(new PoliticColor("nero"))));
		PoliticDeck politicDeck = new PoliticDeck(politicCards);
		List<Councillor> pool = new ArrayList<Councillor>();
		Balcony kingBalcony = Balcony.createBalcony(4);
		List<Region> regions = new ArrayList<>();
		NobilityTrack track = NobilityTrack.createNobilityTrack(NobilityCell.createNobilityCell(1, null, new EmptyBonus()));
		King king = King.createKing(City.createCity("Milano", CityColor.createCityColor("Oro"), new EmptyBonus()));
		Market market = new Market();
		KingDeck kingDeck = new KingDeck(new ArrayList<RewardTile>());
		Map<CityColor, Bonus> map = new HashMap<>();
		
		regions.add(createRegion());
		
		this.gameBoard = GameBoard.createGameBoard(politicDeck, pool, kingBalcony, regions, track, king, market, kingDeck, map);

		gameBoard.registerPlayer("Marco");
		gameBoard.registerPlayer("Luca");
		gameBoard.registerPlayer("Davide");
		gameBoard.start();
	}
	
	@Test
	public void testBuildActionShouldAssignTheToken() {
		RegionDTO region = createRegion().getState();
		Action changeBPT = new ChangeBPT(region , 89);
		
		assertEquals(89, changeBPT.getToken());
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildActionWithRegionNullShouldThrowNullPointerException(){
		new ChangeBPT(null, 14);
	}
	
	@Test (expected = NoRemainingActionsException.class)
	public void testApplyActionWithoutRemainingQuickActionsShouldThrowAnException() throws Throwable{
		gameBoard.getCurrentPlayer().performQuickAction();
		Action changeBPT = new ChangeBPT(createRegion().getState(), 1);
		changeBPT.apply(gameBoard);
	}

	@Test (expected = NoRemainingAssistantsException.class)
	public void testApplyActionWithoutAssitantsShouldThrowAnException() throws Throwable{
		gameBoard.getCurrentPlayer().takeAssistants(3);
		Action changeBPT = new ChangeBPT(createRegion().getState(), 1);
		changeBPT.apply(gameBoard);
	}
	
	@Test
	public void testApplyActionOnADeckWith4TilesShouldMoveTheThirdTileToFirst() throws Throwable{
		Action changeBPT = new ChangeBPT(createRegion().getState(), 1);
		changeBPT.apply(gameBoard);
		
		List<City> cities3 = new ArrayList<>();
		cities3.add(City.createCity("Milano", CityColor.createCityColor("giallo"), new EmptyBonus()));
		cities3.add(City.createCity("Firenze", CityColor.createCityColor("rosso"), new EmptyBonus()));
		BusinessPermissionTile tile = new BusinessPermissionTile(cities3, new EmptyBonus());
		
		assertFalse(gameBoard.getCurrentPlayer().canPerformQuickAction());
		assertEquals(0, gameBoard.getCurrentPlayer().getAssistantsNumber());
		assertEquals(tile, gameBoard.getRegion(createRegion().getState()).getBPTDeck().draw(0));
	}
	
	@Test
	public void testApplyActionOnADeckWith4TilesShouldMoveTheFourthTileToSecond() throws Throwable{
		Action changeBPT = new ChangeBPT(createRegion().getState(), 1);
		changeBPT.apply(gameBoard);
		
		List<City> cities4 = new ArrayList<>();
		cities4.add(City.createCity("Napoli", CityColor.createCityColor("blu"), new EmptyBonus()));
		cities4.add(City.createCity("Roma", CityColor.createCityColor("verde"), new EmptyBonus()));
		BusinessPermissionTile tile = new BusinessPermissionTile(cities4, new EmptyBonus());
		
		assertEquals(tile, gameBoard.getRegion(createRegion().getState()).getBPTDeck().draw(1));
	}
}
