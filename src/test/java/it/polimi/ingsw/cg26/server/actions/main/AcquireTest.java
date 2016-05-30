package it.polimi.ingsw.cg26.server.actions.main;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticColorDTO;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCardsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NotEnoughMoneyException;
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
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTileDeck;
import it.polimi.ingsw.cg26.server.model.cards.KingDeck;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import it.polimi.ingsw.cg26.server.model.market.Market;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class AcquireTest {

	private Region region;
	
	private GameBoard gameBoard;
	
	private List<PoliticCard> cards;
	
	private Region createRegion(){
		List<BusinessPermissionTile> tiles = new ArrayList<>();
		tiles.add(new BusinessPermissionTile(new ArrayList<City>(), new RewardTile(new ArrayList<Bonus>())));
		tiles.add(new BusinessPermissionTile(new ArrayList<City>(), new RewardTile(new ArrayList<Bonus>())));
		BusinessPermissionTileDeck bPTDeck = new BusinessPermissionTileDeck(tiles);
		
		Balcony balcony = Balcony.createBalcony(4);
		balcony.elect(Councillor.createCouncillor(new PoliticColor("bianco")));
		balcony.elect(Councillor.createCouncillor(new PoliticColor("verde")));
		balcony.elect(Councillor.createCouncillor(new PoliticColor("blu")));
		balcony.elect(Councillor.createCouncillor(new PoliticColor("nero")));
		return Region.createRegion("hills", new ArrayList<City>(), bPTDeck, balcony, new RewardTile(new ArrayList<Bonus>()));
	}
	
	private List<PoliticCard> createPlayersCards(){
		List<PoliticCard> cards = new ArrayList<>();
		cards.add(new PoliticCard(new PoliticColor("verde")));
		cards.add(new PoliticCard(new PoliticColor("giallo")));
		cards.add(new PoliticCard(new PoliticColor("bianco")));
		cards.add(new PoliticCard(new PoliticColor("multicolor")));
		cards.add(new PoliticCard(new PoliticColor("verde")));
		cards.add(new PoliticCard(new PoliticColor("nero")));
		cards.add(new PoliticCard(new PoliticColor("blu")));
		return cards;
	}

	@Before
	public void setUp(){
		LinkedList<PoliticCard> politicCards = new LinkedList<>();
		politicCards.add(new PoliticCard(new PoliticColor("c1")));
		PoliticDeck politicDeck = new PoliticDeck(politicCards);
		List<Councillor> pool = new ArrayList<>();
		Balcony kingBalcony = Balcony.createBalcony(4);
		List<Region> regions = new ArrayList<>();
		NobilityTrack track = NobilityTrack.createNobilityTrack(NobilityCell.createNobilityCell(1, null, new RewardTile(new ArrayList<Bonus>())));
		King king = King.createKing(City.createCity("Milano", CityColor.createCityColor("Oro"), new RewardTile(new ArrayList<Bonus>())));
		Market market = new Market();
		KingDeck kingDeck = new KingDeck(new ArrayList<RewardTile>());
		Map<CityColor, RewardTile> map = new HashMap<>();
		
		region = createRegion();
		regions.add(region);
		
		this.gameBoard = GameBoard.createGameBoard(politicDeck, pool, kingBalcony, regions, track, king, market, kingDeck, map);
		
		List<Assistant> assistants = new ArrayList<>();
		for(int i=0; i<3; i++)
			assistants.add(new Assistant());
		cards = createPlayersCards();
		Player player1 = new Player(1, "Marco", NobilityCell.createNobilityCell(1, null, new RewardTile(new ArrayList<Bonus>())), 0, cards, assistants);
		gameBoard.registerPlayer(player1);
	}
	
	@Test
	public void testBuildActionShouldAssignTheToken() {
		Action action = new Acquire(region.getState(), new ArrayList<PoliticCardDTO>(), 1, 38);
		
		assertEquals(38, action.getToken());
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
	public void testApplyActionToAPlayerWithoutRemainingMainActionsShouldThrowAnException(){
		gameBoard.getCurrentPlayer().performMainAction();
		Action action = new Acquire(region.getState(), new ArrayList<PoliticCardDTO>(), 0, 1);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = InvalidCardsException.class)
	public void testApplyActionWithPoliticCardsDTODifferentFromTheRealCardsOfThePlayerShouldThrowAnException(){
		List<PoliticCardDTO> userCards = new ArrayList<>();
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("rosso"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("arancione"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("verde"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("blu"), 0, "Marco"));
		Action action = new Acquire(region.getState(), userCards, 0, 1);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = InvalidCardsException.class)
	public void testApplyNecessaryCoinsWithMoreThanFourCardsShouldThrowAnException(){
		List<PoliticCardDTO> userCards = new ArrayList<>();
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("verde"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("bianco"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("nero"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("multicolor"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("giallo"), 0, "Marco"));
		Acquire action = new Acquire(region.getState(), userCards, 0, 1);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = NotEnoughMoneyException.class)
	public void testApplyNecessaryCoinsWithFourCardsAndOneMulticolorCardAndPlayerWith0CoinsShouldThrowException(){
		List<PoliticCardDTO> userCards = new ArrayList<>();
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("verde"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("bianco"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("nero"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("multicolor"), 0, "Marco"));
		Acquire action = new Acquire(region.getState(), userCards, 0, 1);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = NotEnoughMoneyException.class)
	public void testApplyNecessaryCoinsNumberWithThreeCardsAndOneMulticolorCardAndPlayerWithLessThan5CoinsShouldThrowException(){
		List<PoliticCardDTO> userCards = new ArrayList<>();
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("verde"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("bianco"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("multicolor"), 0, "Marco"));
		Acquire action = new Acquire(region.getState(), userCards, 0, 1);
		gameBoard.getCurrentPlayer().addCoins(4);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = NotEnoughMoneyException.class)
	public void testApplyNecessaryCoinsNumberWithTwoCardsAndPlayerWithLessThan7CoinsShouldThrowException(){
		List<PoliticCardDTO> userCards = new ArrayList<>();
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("verde"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("bianco"), 0, "Marco"));
		Acquire action = new Acquire(region.getState(), userCards, 0, 1);
		gameBoard.getCurrentPlayer().addCoins(6);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = NotEnoughMoneyException.class)
	public void testApplyNecessaryCoinsNumberWithOneCardAndPlayerWithLessThan10CoinsShouldThrowException(){
		List<PoliticCardDTO> userCards = new ArrayList<>();
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("verde"), 0, "Marco"));
		Acquire action = new Acquire(region.getState(), userCards, 0, 1);
		gameBoard.getCurrentPlayer().addCoins(9);
		
		action.apply(gameBoard);
	}
	
	@Test (expected = InvalidCardsException.class)
	public void testApplyWithCardsThatDoesntMatchTheCouncillorsOnTheBalconyShouldThrowException(){
		List<PoliticCardDTO> userCards = new ArrayList<>();
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("verde"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("bianco"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("arancione"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("multicolor"), 0, "Marco"));
		Acquire action = new Acquire(region.getState(), userCards, 0, 1);
		gameBoard.getCurrentPlayer().addCoins(10);
		
		action.apply(gameBoard);
	}
	
	@Test
	public void testApplyAction(){
		List<PoliticCardDTO> userCards = new ArrayList<>();
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("verde"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("bianco"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("nero"), 0, "Marco"));
		userCards.add(new PoliticCardDTO(new PoliticColorDTO("blu"), 0, "Marco"));
		Acquire action = new Acquire(region.getState(), userCards, 0, 1);
		gameBoard.getCurrentPlayer().addCoins(10);
		
		action.apply(gameBoard);
		
		assertEquals(10, gameBoard.getCurrentPlayer().getCoinsNumber());
		assertFalse(gameBoard.getCurrentPlayer().canPerformMainAction());
	}
}
