package it.polimi.ingsw.cg26.server.actions.quick;

import static org.junit.Assert.*;

import java.util.*;

import it.polimi.ingsw.cg26.server.model.cards.*;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.RegionDTO;
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
import it.polimi.ingsw.cg26.server.model.market.Market;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

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
		
		regions.add(createRegion());
		
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
		RegionDTO region = createRegion().getState();
		Action changeBPT = new ChangeBPT(region , 89);
		
		assertEquals(89, changeBPT.getToken());
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildActionWithRegionNullShouldThrowNullPointerException(){
		new ChangeBPT(null, 14);
	}
	
	@Test (expected = NoRemainingActionsException.class)
	public void testApplyActionWithoutRemainingQuickActionsShouldThrowAnException(){
		gameBoard.getCurrentPlayer().performQuickAction();
		Action changeBPT = new ChangeBPT(createRegion().getState(), 1);
		changeBPT.apply(gameBoard);
	}

	@Test (expected = NoRemainingAssistantsException.class)
	public void testApplyActionWithoutAssitantsShouldThrowAnException(){
		gameBoard.getCurrentPlayer().takeAssistants(3);
		Action changeBPT = new ChangeBPT(createRegion().getState(), 1);
		changeBPT.apply(gameBoard);
	}
	
	@Test
	public void testApplyActionOnADeckWith4TilesShouldMoveTheThirdTileToFirst(){
		Action changeBPT = new ChangeBPT(createRegion().getState(), 1);
		changeBPT.apply(gameBoard);
		
		List<City> cities3 = new ArrayList<>();
		cities3.add(City.createCity("Milano", CityColor.createCityColor("giallo"), new EmptyBonus()));
		cities3.add(City.createCity("Firenze", CityColor.createCityColor("rosso"), new EmptyBonus()));
		BusinessPermissionTile tile = new BusinessPermissionTile(cities3, new EmptyBonus());
		
		assertFalse(gameBoard.getCurrentPlayer().canPerformQuickAction());
		assertEquals(2, gameBoard.getCurrentPlayer().getAssistantsNumber());
		assertEquals(tile, gameBoard.getRegion(createRegion().getState()).getBPTDeck().draw());
	}
	
	@Test
	public void testApplyActionOnADeckWith4TilesShouldMoveTheFourthTileToSecond(){
		Action changeBPT = new ChangeBPT(createRegion().getState(), 1);
		changeBPT.apply(gameBoard);
		
		List<City> cities4 = new ArrayList<>();
		cities4.add(City.createCity("Napoli", CityColor.createCityColor("blu"), new EmptyBonus()));
		cities4.add(City.createCity("Roma", CityColor.createCityColor("verde"), new EmptyBonus()));
		BusinessPermissionTile tile = new BusinessPermissionTile(cities4, new EmptyBonus());
		
		assertEquals(tile, gameBoard.getRegion(createRegion().getState()).getBPTDeck().draw(1));
	}
}
