package it.polimi.ingsw.cg26.server.actions.main;

import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticColorDTO;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCardsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.server.exceptions.NotEnoughMoneyException;
import it.polimi.ingsw.cg26.server.model.board.*;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.cards.*;
import it.polimi.ingsw.cg26.server.model.market.Market;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BuildKingTest {

	private GameBoard gameBoard;
	
	private Region region;
	
	private City chosenCity;
	
	private City kingCity;
	
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
		
		chosenCity = City.createCity("Milano", CityColor.createCityColor("grigio"), new EmptyBonus());
		kingCity = City.createCity("Roma", CityColor.createCityColor("rosso"), new EmptyBonus());
		chosenCity.link(kingCity);
		kingCity.link(chosenCity);
		Player player2 = new Player(2, "Ajeje", NobilityCell.createNobilityCell(1, null, new EmptyBonus()), 0, new LinkedList<PoliticCard>(), new LinkedList<Assistant>());
		chosenCity.build(player2);
		List<City> cities = new ArrayList<>();
		cities.add(chosenCity);
		cities.add(kingCity);
		cities.add(City.createCity("Firenze", CityColor.createCityColor("blu"), new EmptyBonus()));
		cities.add(City.createCity("Torino", CityColor.createCityColor("nero"), new EmptyBonus()));
		return Region.createRegion("hills", cities, bPTDeck, balcony, new EmptyBonus());
	}
	
	private Balcony createKingBalcony(){
		Balcony kingBalcony = Balcony.createBalcony(4);
		kingBalcony.elect(Councillor.createCouncillor(new PoliticColor("giallo")));
		kingBalcony.elect(Councillor.createCouncillor(new PoliticColor("verde")));
		kingBalcony.elect(Councillor.createCouncillor(new PoliticColor("viola")));
		kingBalcony.elect(Councillor.createCouncillor(new PoliticColor("rosso")));
		return kingBalcony;
	}
	
	@Before
	public void setUp() throws Exception {
		LinkedList<PoliticCard> politicCards = new LinkedList<>();
		politicCards.add(new PoliticCard(new PoliticColor("verde")));
		politicCards.add(new PoliticCard(new PoliticColor("giallo")));
		politicCards.add(new PoliticCard(new PoliticColor("bianco")));
		politicCards.add(new PoliticCard(new PoliticColor("multicolor")));
		politicCards.add(new PoliticCard(new PoliticColor("viola")));
		PoliticDeck politicDeck = new PoliticDeck(politicCards);
		List<Councillor> pool = new ArrayList<>();
		Balcony kingBalcony = createKingBalcony();
		List<Region> regions = new ArrayList<>();
		region = createRegion();
		regions.add(region);
		NobilityTrack track = NobilityTrack.createNobilityTrack(NobilityCell.createNobilityCell(1, null, new EmptyBonus()));
		King king = King.createKing(kingCity);
		Market market = new Market();
		KingDeck kingDeck = new KingDeck(new ArrayList<RewardTile>());
		Map<CityColor, Bonus> map = new HashMap<>();
		
		this.gameBoard = GameBoard.createGameBoard(politicDeck, pool, kingBalcony, regions, track, king, market, kingDeck, map);
		//Player player1 = new Player(1, "Marco", NobilityCell.createNobilityCell(1, null, new EmptyBonus()), 0, cards, new LinkedList<Assistant>());
		token = gameBoard.registerPlayer("Marco").getToken();
		gameBoard.getPoliticDeck().discardAll(Collections.nCopies(16, new PoliticCard(new PoliticColor("nero"))));
		gameBoard.registerPlayer("Luca");
		gameBoard.start();
	}
	
	@Test
	public void testBuildActionShouldAssignTheToken() {
		Action action = new BuildKing(chosenCity.getState(), new ArrayList<PoliticCardDTO>(), 312);
		
		assertEquals(312, action.getToken());
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildActionWithCityNullShouldThrowAnException() {
		new BuildKing(null, new ArrayList<PoliticCardDTO>(), 12);
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildActionWithCardsNullShouldThrowAnException() {
		new BuildKing(chosenCity.getState(), null, 12);
	}

	@Test (expected = NoRemainingActionsException.class)
	public void testApplyActionToAPlayerWithoutRemainingMainActionsShouldThrowAnException() throws Exception {
		gameBoard.getCurrentPlayer().performMainAction();
		Action action = new BuildKing(chosenCity.getState(), new ArrayList<PoliticCardDTO>(), token);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = InvalidCardsException.class)
	public void testApplyActionWithPoliticCardsDTODifferentFromTheRealCardsOfThePlayerShouldThrowAnException() throws Exception {
		List<PoliticCardDTO> userCards = new ArrayList<>();
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("rosso"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("arancione"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("verde"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("blu"), 0, "Marco"));
		Action action = new BuildKing(chosenCity.getState(), userCards, token);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = InvalidCardsException.class)
	public void testApplyNecessaryCoinsWithMoreThanFourCardsShouldThrowAnException() throws Exception {
		List<PoliticCardDTO> userCards = new ArrayList<>();
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("verde"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("bianco"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("nero"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("multicolor"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("giallo"), 0, "Marco"));
		Action action = new BuildKing(chosenCity.getState(), userCards, token);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = NotEnoughMoneyException.class)
	public void testApplyNecessaryCoinsWithFourCardsAndOneMulticolorCardAndOneStreetToGoAndPlayerWithLessThan3CoinsShouldThrowException() throws Exception {
		List<PoliticCardDTO> userCards = new ArrayList<>();
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("verde"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("bianco"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("nero"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("multicolor"), 0, "Marco"));
		Action action = new BuildKing(chosenCity.getState(), userCards, token);
		gameBoard.getCurrentPlayer().removeCoins(8);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = NotEnoughMoneyException.class)
	public void testApplyNecessaryCoinsNumberWithThreeCardsAndOneMulticolorCardAndPlayerWithLessThan5CoinsShouldThrowException() throws Exception {
		List<PoliticCardDTO> userCards = new ArrayList<>();
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("verde"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("bianco"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("multicolor"), 0, "Marco"));
		Action action = new BuildKing(chosenCity.getState(), userCards, token);
		gameBoard.getCurrentPlayer().removeCoins(6);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = NotEnoughMoneyException.class)
	public void testApplyNecessaryCoinsNumberWithTwoCardsAndPlayerWithLessThan7CoinsShouldThrowException() throws Exception {
		List<PoliticCardDTO> userCards = new ArrayList<>();
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("verde"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("bianco"), 0, "Marco"));
		Action action = new BuildKing(chosenCity.getState(), userCards, token);
		gameBoard.getCurrentPlayer().removeCoins(4);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = NotEnoughMoneyException.class)
	public void testApplyNecessaryCoinsNumberWithOneCardAndPlayerWithLessThan10CoinsShouldThrowException() throws Exception {
		List<PoliticCardDTO> userCards = new ArrayList<>();
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("verde"), 0, "Marco"));
		Action action = new BuildKing(chosenCity.getState(), userCards, token);
		gameBoard.getCurrentPlayer().removeCoins(1);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = InvalidCardsException.class)
	public void testApplyWithCardsThatDoesntMatchTheCouncillorsOnTheKingBalconyShouldThrowException() throws Exception {
		List<PoliticCardDTO> userCards = new ArrayList<>();
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("viola"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("verde"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("nero"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("multicolor"), 0, "Marco"));
		Action action = new BuildKing(chosenCity.getState(), userCards, token);
		gameBoard.getCurrentPlayer().addCoins(10);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = NoRemainingAssistantsException.class)
	public void testApplyTryToBuildOnACityWithOneEmporiumWithoutAssistantShouldThrowAnException() throws Exception {
		List<PoliticCardDTO> userCards = new ArrayList<>();
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("viola"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("verde"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("giallo"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("multicolor"), 0, "Marco"));
		Action action = new BuildKing(chosenCity.getState(), userCards, token);
		gameBoard.getCurrentPlayer().takeAssistants(1);
		
		action.apply(gameBoard);
	}
	
	@Test
	public void testApplyCheckChanges() throws Exception {
		List<PoliticCardDTO> userCards = new ArrayList<>();
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("viola"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("verde"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("giallo"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("multicolor"), 0, "Marco"));
		Action action = new BuildKing(chosenCity.getState(), userCards, token);
		
		action.apply(gameBoard);
		
		assertEquals(7, gameBoard.getCurrentPlayer().getCoinsNumber());
		assertEquals(0, gameBoard.getCurrentPlayer().getAssistantsNumber());
		assertEquals(2, chosenCity.getEmporiumsNumber());
		assertEquals(chosenCity, gameBoard.getKing().getCurrentCity());
		assertEquals(3, gameBoard.getCurrentPlayer().getFullState().getCards().size()); //player has 6 cards and draws 1 card, then uses 4 cards
		assertFalse(gameBoard.getCurrentPlayer().canPerformMainAction());
	}
}
