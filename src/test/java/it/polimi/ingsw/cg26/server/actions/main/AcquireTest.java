package it.polimi.ingsw.cg26.server.actions.main;

import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticColorDTO;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCardsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NotEnoughMoneyException;
import it.polimi.ingsw.cg26.server.model.board.*;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.cards.*;
import it.polimi.ingsw.cg26.server.model.market.Market;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class AcquireTest {

	private Region region;
	
	private GameBoard gameBoard;

	private long token;
	
	private Region createRegion(){
		List<BusinessPermissionTile> tiles = new ArrayList<>();
		tiles.add(new BusinessPermissionTile(new ArrayList<City>(), new EmptyBonus()));
		tiles.add(new BusinessPermissionTile(new ArrayList<City>(), new EmptyBonus()));
		BusinessPermissionTileDeck bPTDeck = new BusinessPermissionTileDeck(tiles);
		
		Balcony balcony = Balcony.createBalcony(4);
		balcony.elect(Councillor.createCouncillor(new PoliticColor("bianco")));
		balcony.elect(Councillor.createCouncillor(new PoliticColor("verde")));
		balcony.elect(Councillor.createCouncillor(new PoliticColor("giallo")));
		balcony.elect(Councillor.createCouncillor(new PoliticColor("nero")));
		return Region.createRegion("hills", new ArrayList<City>(), bPTDeck, balcony, new EmptyBonus());
	}

	@Before
	public void setUp() throws Exception {
		LinkedList<PoliticCard> politicCards = new LinkedList<>();
		politicCards.add(new PoliticCard(new PoliticColor("rosso")));
		politicCards.add(new PoliticCard(new PoliticColor("giallo")));
		politicCards.add(new PoliticCard(new PoliticColor("bianco")));
		politicCards.add(new PoliticCard(new PoliticColor("multicolor")));
		politicCards.add(new PoliticCard(new PoliticColor("verde")));
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
		List<Assistant> assistants = new ArrayList<>();
		for(int i=0; i<3; i++)
			assistants.add(new Assistant());
		//Player player1 = new Player(1, "Marco", NobilityCell.createNobilityCell(1, null, new EmptyBonus()), 0, cards, assistants);
		token = gameBoard.registerPlayer("Marco").getToken(); //create player with 10 coins, 0 nobility, 1 assistant, 6 politic cards
		gameBoard.getPoliticDeck().discardAll(Collections.nCopies(16, new PoliticCard(new PoliticColor("nero"))));
		gameBoard.registerPlayer("Luca");
		gameBoard.start();
	}
	
	@Test
	public void testBuildActionShouldAssignTheToken() {
		Action action = new Acquire(region.getState(), new ArrayList<PoliticCardDTO>(), 1, 15);
		
		assertEquals(15, action.getToken());
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildActionWithRegionNullShouldThrowAnException() {
		new Acquire(null, new ArrayList<PoliticCardDTO>(), 1, 19);
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildActionWithPoliticCardsDTONullShouldThrowAnException() {
		new Acquire(region.getState(), null, 1, 26);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testBuildActionWithNegativePositionShouldThrowAnException() {
		new Acquire(region.getState(), new ArrayList<PoliticCardDTO>(), -3, 32);
	}
	
	@Test (expected = NoRemainingActionsException.class)
	public void testApplyActionToAPlayerWithoutRemainingMainActionsShouldThrowAnException() throws Exception {
		gameBoard.getCurrentPlayer().performMainAction();
		Action action = new Acquire(region.getState(), new ArrayList<PoliticCardDTO>(), 0, token);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = InvalidCardsException.class)
	public void testApplyActionWithPoliticCardsDTODifferentFromTheRealCardsOfThePlayerShouldThrowAnException() throws Exception {
		List<PoliticCardDTO> userCards = new ArrayList<>();
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("rosso"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("arancione"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("verde"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("blu"), 0, "Marco"));
		Action action = new Acquire(region.getState(), userCards, 0, token);
		
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
		Acquire action = new Acquire(region.getState(), userCards, 0, token);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = NotEnoughMoneyException.class)
	public void testApplyNecessaryCoinsWithFourCardsAndOneMulticolorCardAndPlayerWith0CoinsShouldThrowException() throws Exception {
		List<PoliticCardDTO> userCards = new ArrayList<>();
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("verde"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("bianco"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("nero"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("multicolor"), 0, "Marco"));
		Acquire action = new Acquire(region.getState(), userCards, 0, token);
		gameBoard.getCurrentPlayer().removeCoins(10);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = NotEnoughMoneyException.class)
	public void testApplyNecessaryCoinsNumberWithThreeCardsAndOneMulticolorCardAndPlayerWithLessThan5CoinsShouldThrowException() throws Exception {
		List<PoliticCardDTO> userCards = new ArrayList<>();
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("verde"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("bianco"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("multicolor"), 0, "Marco"));
		Acquire action = new Acquire(region.getState(), userCards, 0, token);
		gameBoard.getCurrentPlayer().removeCoins(6);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = NotEnoughMoneyException.class)
	public void testApplyNecessaryCoinsNumberWithTwoCardsAndPlayerWithLessThan7CoinsShouldThrowException() throws Exception {
		List<PoliticCardDTO> userCards = new ArrayList<>();
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("verde"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("bianco"), 0, "Marco"));
		Acquire action = new Acquire(region.getState(), userCards, 0, token);
		gameBoard.getCurrentPlayer().removeCoins(4);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = NotEnoughMoneyException.class)
	public void testApplyNecessaryCoinsNumberWithOneCardAndPlayerWithLessThan10CoinsShouldThrowException() throws Exception {
		List<PoliticCardDTO> userCards = new ArrayList<>();
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("verde"), 0, "Marco"));
		Acquire action = new Acquire(region.getState(), userCards, 0, token);
		gameBoard.getCurrentPlayer().removeCoins(1);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = InvalidCardsException.class)
	public void testApplyWithCardsThatDoesntMatchTheCouncillorsOnTheBalconyShouldThrowException() throws Exception {
		List<PoliticCardDTO> userCards = new ArrayList<>();
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("verde"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("bianco"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("giallo"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("rosso"), 0, "Marco"));
		Acquire action = new Acquire(region.getState(), userCards, 0, token);
		gameBoard.getCurrentPlayer().addCoins(10);
		
		action.apply(gameBoard);
	}
	
	@Test
	public void testApplyAction() throws Exception {
		List<PoliticCardDTO> userCards = new ArrayList<>();
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("verde"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("bianco"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("nero"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("giallo"), 0, "Marco"));
		Acquire action = new Acquire(region.getState(), userCards, 0, token);
		
		action.apply(gameBoard);
		
		assertEquals(10, gameBoard.getCurrentPlayer().getCoinsNumber());
		assertFalse(gameBoard.getCurrentPlayer().canPerformMainAction());
	}
}
