package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.NotEnoughMoneyException;
import it.polimi.ingsw.cg26.server.model.board.*;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.cards.*;
import it.polimi.ingsw.cg26.server.model.market.Market;
import it.polimi.ingsw.cg26.server.model.market.Sellable;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BuyTest {

private GameBoard gameBoard;
	
	private Player player1;
	
	private Player player2;
	
	private Player player3;
	
	private Sellable assistantToBuy;
	
	private BusinessPermissionTile bPTToBuy;
	
	private PoliticCard cardToBuy;
	
	private long token;
	
	private Market buildMarket(){
		Market market = new Market();
		PoliticCard card1 = new PoliticCard(new PoliticColor("verde"));
		PoliticCard card2 = new PoliticCard(new PoliticColor("rosso"));
		PoliticCard card3 = new PoliticCard(new PoliticColor("giallo"));
		cardToBuy = new PoliticCard(new PoliticColor("arancione"));
		card1.setOwner(player1);
		card1.setPrice(3);
		card2.setOwner(player2);
		card2.setPrice(5);
		card3.setOwner(player2);
		card3.setPrice(3);
		cardToBuy.setOwner(player2);
		cardToBuy.setPrice(5);
		
		List<City> tileCities = new ArrayList<>();
		tileCities.add(City.createCity("Milano", CityColor.createCityColor("grigio"), new EmptyBonus()));
		tileCities.add(City.createCity("Torino", CityColor.createCityColor("orosa"), new EmptyBonus()));
		bPTToBuy = new BusinessPermissionTile(tileCities, new EmptyBonus());
		bPTToBuy.setOwner(player3);
		bPTToBuy.setPrice(8);
		
		assistantToBuy = new Assistant();
		Assistant assistant1 = new Assistant();
		Assistant assistant2 = new Assistant();
		assistantToBuy.setOwner(player2);
		assistantToBuy.setPrice(2);
		assistant1.setOwner(player3);
		assistant1.setPrice(2);
		assistant2.setOwner(player3);
		assistant2.setPrice(3);
		
		market.addToMarket(card1);
		market.addToMarket(assistant2);
		market.addToMarket(cardToBuy);
		market.addToMarket(card2);
		market.addToMarket(card3);
		market.addToMarket(bPTToBuy);
		market.addToMarket(assistantToBuy);
		market.addToMarket(assistant1);
		
		return market;
	}
	
	@Before
	public void setUp() throws Exception {
		//this.player1 = new Player(1, "Marco", NobilityCell.createNobilityCell(1, null, new EmptyBonus()), 0, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		this.player2 = new Player(2, "Davide", NobilityCell.createNobilityCell(1, null, new EmptyBonus()), 0, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		this.player3 = new Player(2, "Luca", NobilityCell.createNobilityCell(1, null, new EmptyBonus()), 0, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());

		LinkedList<PoliticCard> politicCards = new LinkedList<>();
		politicCards.addAll(Collections.nCopies(16, new PoliticCard(new PoliticColor("nero"))));
		PoliticDeck politicDeck = new PoliticDeck(politicCards);
		List<Councillor> pool = new ArrayList<>();
		Balcony kingBalcony = Balcony.createBalcony(4);
		List<Region> regions = new ArrayList<>();
		NobilityTrack track = NobilityTrack.createNobilityTrack(NobilityCell.createNobilityCell(1, null, new EmptyBonus()));
		King king = King.createKing(City.createCity("c1", CityColor.createCityColor("aa"), new EmptyBonus()));
		Market market = buildMarket();
		KingDeck kingDeck = new KingDeck(new ArrayList<RewardTile>());
		Map<CityColor, Bonus> map = new HashMap<>();
		
		this.gameBoard = GameBoard.createGameBoard(politicDeck, pool, kingBalcony, regions, track, king, market, kingDeck, map);
		
		token = gameBoard.registerPlayer("Marco").getToken();
		gameBoard.registerPlayer("Davide");
		//gameBoard.registerPlayer("Luca");
		gameBoard.start();

        gameBoard.getScheduler().getCurrentPlayer().setRemainingMainActions(0);
        gameBoard.getScheduler().getCurrentPlayer().setRemainingQuickActions(0);
        gameBoard.getScheduler().regularActionPerformed();
        gameBoard.getScheduler().getCurrentPlayer().setRemainingMainActions(0);
        gameBoard.getScheduler().getCurrentPlayer().setRemainingQuickActions(0);
        gameBoard.getScheduler().regularActionPerformed();
        gameBoard.getScheduler().foldSell();
        gameBoard.getScheduler().foldSell();
	}
	
	@Test
	public void testBuildActionShouldAssignTheToken(){
		Action action = new Buy(assistantToBuy.getState(), 12);
		
		assertEquals(12, action.getToken());
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildActionWithAssistantDTONullShouldThrowAnException(){
		new Buy(null, 67);
	}
	
	@Test (expected = NotEnoughMoneyException.class)
	public void testApplyActionWithAPlayerThatTriesToBuyWithoutEnoughMoneyShouldThrowException() throws Exception {
		Action action = new Buy(this.cardToBuy.getState(), gameBoard.getCurrentPlayer().getToken());
		gameBoard.getCurrentPlayer().removeCoins(10);
		
		action.apply(gameBoard);
	}

	@Test
	public void testApplyActionBuyAssistantCheckChanges() throws Exception {
        Player oldOwner = assistantToBuy.getOwner();
        int coinsToHave = oldOwner.getCoinsNumber() + assistantToBuy.getPrice();
        int coinsToHaveBuyer = gameBoard.getCurrentPlayer().getCoinsNumber() - assistantToBuy.getPrice();
        int assistantsToHaveBuyer = gameBoard.getCurrentPlayer().getAssistantsNumber() + 1;
		Action action = new Buy(this.assistantToBuy.getState(), gameBoard.getCurrentPlayer().getToken());
		action.apply(gameBoard);
		
		assertEquals(gameBoard.getCurrentPlayer(), assistantToBuy.getOwner());
		assertEquals(assistantsToHaveBuyer, gameBoard.getCurrentPlayer().getAssistantsNumber());
		assertEquals(coinsToHave, oldOwner.getCoinsNumber());
		assertEquals(coinsToHaveBuyer, gameBoard.getCurrentPlayer().getCoinsNumber());
		assertFalse(gameBoard.getMarket().getOnSale().contains(assistantToBuy));
	}
	
	@Test
	public void testApplyActionBuyBPTCheckChanges() throws Exception {
        Player oldOwner = bPTToBuy.getOwner();
        int coinsToHave = oldOwner.getCoinsNumber() + bPTToBuy.getPrice();
        int coinsToHaveBuyer = gameBoard.getCurrentPlayer().getCoinsNumber() - bPTToBuy.getPrice();
        Action action = new Buy(this.bPTToBuy.getState(), gameBoard.getCurrentPlayer().getToken());
		action.apply(gameBoard);
		
		assertEquals(gameBoard.getCurrentPlayer(), bPTToBuy.getOwner());
		assertEquals(this.bPTToBuy, gameBoard.getCurrentPlayer().hasPermissionTile(bPTToBuy.getState()));
		assertEquals(coinsToHaveBuyer, gameBoard.getCurrentPlayer().getCoinsNumber());
		assertEquals(coinsToHave, oldOwner.getCoinsNumber());
		assertFalse(gameBoard.getMarket().getOnSale().contains(bPTToBuy));
	}
	
	@Test
	public void testApplyActionBuyPoliticCardCheckChanges() throws Exception {
        Player oldOwner = cardToBuy.getOwner();
        int coinsToHave = oldOwner.getCoinsNumber() + cardToBuy.getPrice();
        int coinsToHaveBuyer = gameBoard.getCurrentPlayer().getCoinsNumber() - cardToBuy.getPrice();
        Action action = new Buy(this.cardToBuy.getState(), gameBoard.getCurrentPlayer().getToken());
		action.apply(gameBoard);
		
		assertEquals(gameBoard.getCurrentPlayer(), cardToBuy.getOwner());
		assertEquals(cardToBuy, gameBoard.getCurrentPlayer().takeCard(cardToBuy.getState()));
		assertEquals(coinsToHaveBuyer, gameBoard.getCurrentPlayer().getCoinsNumber());
		assertEquals(coinsToHave, oldOwner.getCoinsNumber());
		assertFalse(gameBoard.getMarket().getOnSale().contains(cardToBuy));
	}
}
