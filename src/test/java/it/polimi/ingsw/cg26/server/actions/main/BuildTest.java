package it.polimi.ingsw.cg26.server.actions.main;

import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCityException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.server.model.board.*;
import it.polimi.ingsw.cg26.server.model.bonus.AssistantBonus;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.bonus.CoinBonus;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.cards.*;
import it.polimi.ingsw.cg26.server.model.market.Market;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class BuildTest {

	private City chosenCity;
	
	private City otherCity;
	
	private City cityNotInBPT;
	
	private GameBoard gameBoard;
	
	private Region region;
	
	private BusinessPermissionTile tileToUse; 
	
	private long token;
	
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
		otherCity = City.createCity("KingsLanding", CityColor.createCityColor("oro"), new EmptyBonus());
		cityNotInBPT = City.createCity("Firenze", CityColor.createCityColor("blu"), new EmptyBonus());
		//kingCity = City.createCity("Roma", CityColor.createCityColor("rosso"), new RewardTile(new ArrayList<Bonus>()));
		//chosenCity.link(kingCity);
		//kingCity.link(chosenCity);
		Player player2 = new Player(2, "Ajeje", NobilityCell.createNobilityCell(1, null, new EmptyBonus()), 0, new LinkedList<PoliticCard>(), new LinkedList<Assistant>());
		chosenCity.build(player2);
		List<City> cities = new ArrayList<>();
		cities.add(chosenCity);
		//cities.add(kingCity);
		cities.add(otherCity);
		cities.add(cityNotInBPT);
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
		politicCards.add(new PoliticCard(new PoliticColor("nero")));
		politicCards.add(new PoliticCard(new PoliticColor("viola")));
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
		
		/*List<Assistant> assistants = new ArrayList<>();
		for(int i=0; i<3; i++)
			assistants.add(new Assistant());*/
		//List<PoliticCard> cards = new ArrayList<PoliticCard>();
		//Player player1 = new Player(1, "Marco", NobilityCell.createNobilityCell(1, null, new EmptyBonus()), 0, cards, new LinkedList<Assistant>());
		List<City> tileCities = new ArrayList<>();
		tileCities.add(chosenCity);
		tileCities.add(otherCity);
		tileToUse = new BusinessPermissionTile(tileCities, new EmptyBonus());
		//player1.addPermissionTile(tileToUse);
		token = gameBoard.registerPlayer("Marco").getToken();
		gameBoard.registerPlayer("Luca");
		gameBoard.start();

		gameBoard.getCurrentPlayer().addPermissionTile(tileToUse);
	}
	
	@Test
	public void testBuildActionShouldAssignTheToken() {
		Action action = new Build(chosenCity.getState(), tileToUse.getState(), 372);
		
		assertEquals(372, action.getToken());
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildActionWithCityNullShouldThrowAnException() {
		new Build(null, tileToUse.getState(), 279);
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildActionWithTileNullShouldThrowAnException() {
		new Build(chosenCity.getState(), null, 279);
	}
	
	@Test (expected = NoRemainingActionsException.class)
	public void testApplyActionToAPlayerWithoutRemainingMainActionsShouldThrowAnException() throws Exception {
		gameBoard.getCurrentPlayer().performMainAction();
		Action action = new Build(chosenCity.getState(), tileToUse.getState(), token);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = NoRemainingAssistantsException.class)
	public void testApplyTryToBuildOnACityWithOneEmporiumWithoutAssistantShouldThrowAnException() throws Exception {
		Action action = new Build(chosenCity.getState(), tileToUse.getState(), token);
		gameBoard.getCurrentPlayer().takeAssistants(1);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = InvalidCityException.class)
	public void testApplyActionToACityThatIsNotInBPTShouldThrowException() throws Exception {
		Action action = new Build(cityNotInBPT.getState(), tileToUse.getState(), token);

		action.apply(gameBoard);
	}
	
	@Test
	public void testApplyCheckChanges() throws Exception {
		Action action = new Build(chosenCity.getState(), tileToUse.getState(), token);
		
		action.apply(gameBoard);
		
		assertTrue(chosenCity.hasEmporium(gameBoard.getCurrentPlayer()));
		assertEquals(13, gameBoard.getCurrentPlayer().getCoinsNumber());
		assertEquals(2, gameBoard.getCurrentPlayer().getAssistantsNumber());
		assertFalse(gameBoard.getCurrentPlayer().canPerformMainAction());
		
	}
}
