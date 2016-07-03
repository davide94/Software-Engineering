package it.polimi.ingsw.cg26.server.actions.answer;

import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.update.request.CityBonusRequest;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.actions.main.Build;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCityException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.model.board.*;
import it.polimi.ingsw.cg26.server.model.bonus.*;
import it.polimi.ingsw.cg26.server.model.cards.*;
import it.polimi.ingsw.cg26.server.model.market.Market;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ChooseCityTest {
	
	private GameBoard gameBoard;
	
	private City chosenCity;
	
	private Region region;
	
	private long token;
	
	private List<CityDTO> chosenCities;
	
	private City otherCity;
	
	private City cityWithNobilityBonus;

	private Region createRegion() throws Exception {
		List<BusinessPermissionTile> tiles = new ArrayList<>();
		tiles.add(new BusinessPermissionTile(new ArrayList<City>(), new EmptyBonus()));
		tiles.add(new BusinessPermissionTile(new ArrayList<City>(), new EmptyBonus()));
		BusinessPermissionTileDeck bPTDeck = new BusinessPermissionTileDeck(tiles);
		
		Balcony balcony = Balcony.createBalcony(4);
		balcony.elect(Councillor.createCouncillor(new PoliticColor("bianco")));
		balcony.elect(Councillor.createCouncillor(new PoliticColor("verde")));
		balcony.elect(Councillor.createCouncillor(new PoliticColor("blu")));
		balcony.elect(Councillor.createCouncillor(new PoliticColor("nero")));

		Bonus bonuses = new CoinBonus(new AssistantBonus(new EmptyBonus(), 2), 3);
		
		chosenCity = City.createCity("Milano", CityColor.createCityColor("grigio"), bonuses);
		otherCity = City.createCity("KingsLanding", CityColor.createCityColor("oro"), new VictoryBonus(new EmptyBonus(),4));
		cityWithNobilityBonus = City.createCity("London", CityColor.createCityColor("verde"), new NobilityBonus(new EmptyBonus(), 3));
		//kingCity = City.createCity("Roma", CityColor.createCityColor("rosso"), new RewardTile(new ArrayList<Bonus>()));
		//chosenCity.link(kingCity);
		//kingCity.link(chosenCity);
		Player player2 = new Player(2, "Ajeje", NobilityCell.createNobilityCell(1, null, new EmptyBonus()), 0, new LinkedList<PoliticCard>(), new LinkedList<Assistant>());
		chosenCity.build(player2);
		List<City> cities = new ArrayList<>();
		cities.add(chosenCity);
		cities.add(cityWithNobilityBonus);
		cities.add(otherCity);
		cities.add(City.createCity("Firenze", CityColor.createCityColor("blu"), new EmptyBonus()));
		cities.add(City.createCity("Torino", CityColor.createCityColor("nero"), new EmptyBonus()));
		return Region.createRegion("hills", cities, bPTDeck, balcony, new EmptyBonus());
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
		politicCards.add(new PoliticCard(new PoliticColor("viola")));
		politicCards.add(new PoliticCard(new PoliticColor("verde")));
		politicCards.add(new PoliticCard(new PoliticColor("giallo")));
		politicCards.add(new PoliticCard(new PoliticColor("bianco")));
		politicCards.add(new PoliticCard(new PoliticColor("multicolor")));
		politicCards.add(new PoliticCard(new PoliticColor("verde")));
		PoliticDeck politicDeck = new PoliticDeck(politicCards);
		List<Councillor> pool = new ArrayList<>();
		Balcony kingBalcony = Balcony.createBalcony(4);
		List<Region> regions = new ArrayList<>();
		region = createRegion();
		regions.add(region);
		NobilityTrack track = NobilityTrack.createNobilityTrack(NobilityCell.createNobilityCell(1, null, new EmptyBonus()));
		King king = King.createKing(City.createCity("c1", CityColor.createCityColor("aa"), new EmptyBonus()));
		Market market = new Market();
		KingDeck kingDeck = new KingDeck(new ArrayList<RewardTile>());
		Map<CityColor, Bonus> map = new HashMap<>();
		
		this.gameBoard = GameBoard.createGameBoard(politicDeck, pool, kingBalcony, regions, track, king, market, kingDeck, map);
		
		List<City> tileCities = new ArrayList<>();
		tileCities.add(chosenCity);
		BusinessPermissionTile tileToUse = new BusinessPermissionTile(tileCities, new EmptyBonus());
		token = gameBoard.registerPlayer("Marco").getToken();
		gameBoard.registerPlayer("Luca");
		gameBoard.start();

		gameBoard.getCurrentPlayer().addPermissionTile(tileToUse);
		Action action = new Build(chosenCity.getState(), tileToUse.getState(), token);
		action.apply(gameBoard);
		Bonus cityBonus = new TakeCityBonus(new EmptyBonus(), 1);
		cityBonus.apply(gameBoard.getCurrentPlayer());
		chosenCities = new ArrayList<>();
		chosenCities.add(chosenCity.getState());
	}
	
	@Test
	public void testBuildActionShouldAssignTheToken() {
		Action action = new ChooseCity(chosenCities, token);
		
		assertEquals(token, action.getToken());
	}

	@Test (expected = NullPointerException.class)
	public void testBuildActionWithCitiesNullShouldThrowException() {
		new ChooseCity(null, token);
	}
	
	@Test (expected = InvalidCityException.class)
	public void testApplyActionWithTwoEqualCitiesShouldThrowException() throws Exception {
		chosenCities.add(chosenCity.getState());
		Action action = new ChooseCity(chosenCities, token);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = NoRemainingActionsException.class)
	public void testApplyToAPlayerWithoutRemainingChooseActionShouldThrowException() throws Exception {
		gameBoard.getCurrentPlayer().performChooseAction();
		Action action = new ChooseCity(chosenCities, token);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = InvalidCityException.class)
	public void testApplyWithACityWithoutTheEmporiumOfThePlayerShouldThrowException() throws Exception {
		List<CityDTO> cities = new ArrayList<>();
		cities.add(otherCity.getState());
		Action action = new ChooseCity(cities, token);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = InvalidCityException.class)
	public void testApplyActionToACityWithNobilityBonusShouldThrowException() throws Exception {
		List<City> tileCities = new ArrayList<>();
		tileCities.add(cityWithNobilityBonus);
		BusinessPermissionTile tileToUse = new BusinessPermissionTile(tileCities, new EmptyBonus());
		gameBoard.getCurrentPlayer().addRemainingMainActions(1);
		gameBoard.getCurrentPlayer().addPermissionTile(tileToUse);
		Action buildAction = new Build(cityWithNobilityBonus.getState(), tileToUse.getState(), token);
		buildAction.apply(gameBoard);
		List<CityDTO> cities = new ArrayList<>();
		cities.add(cityWithNobilityBonus.getState());
		Action action = new ChooseCity(cities, token);
		
		action.apply(gameBoard);
	}
	
	@Test
	public void testApplyActionCheckChanges() throws Exception {
		Action action = new ChooseCity(chosenCities, token);
		action.apply(gameBoard);
		
		assertEquals(16, gameBoard.getCurrentPlayer().getCoinsNumber());
		assertEquals(4, gameBoard.getCurrentPlayer().getAssistantsNumber());
		assertFalse(gameBoard.getCurrentPlayer().canPerformChooseAction());
		assertTrue(gameBoard.getCurrentPlayer().getPendingRequest().isEmpty());
	}
	
	@Test
	public void testApplyActionWithMultiplicity2CheckChanges() throws Exception {
		gameBoard.getCurrentPlayer().performChooseAction();
		gameBoard.getCurrentPlayer().removePendingRequest(new CityBonusRequest(1));
		Bonus cityBonus = new TakeCityBonus(new EmptyBonus(), 2);
		cityBonus.apply(gameBoard.getCurrentPlayer());
		List<City> tileCities = new ArrayList<>();
		tileCities.add(otherCity);
		BusinessPermissionTile tileToUse = new BusinessPermissionTile(tileCities, new EmptyBonus());
		gameBoard.getCurrentPlayer().addRemainingMainActions(1);
		gameBoard.getCurrentPlayer().addPermissionTile(tileToUse);
		Action buildAction = new Build(otherCity.getState(), tileToUse.getState(), token);
		buildAction.apply(gameBoard);
		chosenCities.add(otherCity.getState());
		Action action = new ChooseCity(chosenCities, token);
		action.apply(gameBoard);
		
		assertEquals(8, gameBoard.getCurrentPlayer().getVictoryPoints());
		assertEquals(16, gameBoard.getCurrentPlayer().getCoinsNumber());
		assertEquals(4, gameBoard.getCurrentPlayer().getAssistantsNumber());
		assertFalse(gameBoard.getCurrentPlayer().canPerformChooseAction());
		assertTrue(gameBoard.getCurrentPlayer().getPendingRequest().isEmpty());
	}
}
