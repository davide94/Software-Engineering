package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
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
import static org.junit.Assert.assertTrue;

public class SellAssistantTest {

	private GameBoard gameBoard;
	
	private Player player1;
	
	private Player player2;
	
	private Player player3;
	
	private Assistant assistantToSell;
	
	private long token;
	
	private Market buildMarket(){
		Market market = new Market();
		PoliticCard card1 = new PoliticCard(new PoliticColor("verde"));
		PoliticCard card2 = new PoliticCard(new PoliticColor("rosso"));
        PoliticCard card3 = new PoliticCard(new PoliticColor("giallo"));
		card1.setOwner(player1);
		card1.setPrice(3);
		card2.setOwner(player2);
		card2.setPrice(5);
		card3.setOwner(player2);
		card3.setPrice(3);
		
		List<City> tileCities = new ArrayList<>();
		tileCities.add(City.createCity("Milano", CityColor.createCityColor("grigio"), new EmptyBonus()));
		tileCities.add(City.createCity("Torino", CityColor.createCityColor("orosa"), new EmptyBonus()));
		BusinessPermissionTile tile = new BusinessPermissionTile(tileCities, new EmptyBonus());
		tile.setOwner(player3);
		tile.setPrice(8);
		
		assistantToSell = new Assistant();
		Assistant assistant1 = new Assistant();
		Assistant assistant2 = new Assistant();
		assistant1.setOwner(player3);
		assistant1.setPrice(2);
		assistant2.setOwner(player3);
		assistant2.setPrice(3);
		
		market.addToMarket(card1);
		market.addToMarket(assistant2);
		market.addToMarket(card2);
		market.addToMarket(card3);
		market.addToMarket(tile);
		market.addToMarket(assistant1);
		
		return market;
	}
	
	@Before
	public void setUp() throws Exception {
		//this.player1 = new Player(1, "Marco", NobilityCell.createNobilityCell(1, null, new EmptyBonus()), 0, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		//this.player2 = new Player(2, "Davide", NobilityCell.createNobilityCell(1, null, new EmptyBonus()), 0, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		//this.player3 = new Player(2, "Luca", NobilityCell.createNobilityCell(1, null, new EmptyBonus()), 0, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());

		LinkedList<PoliticCard> politicCards = new LinkedList<>();
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
		NobilityTrack track = NobilityTrack.createNobilityTrack(NobilityCell.createNobilityCell(1, null, new EmptyBonus()));
		King king = King.createKing(City.createCity("c1", CityColor.createCityColor("aa"), new EmptyBonus()));
		Market market = buildMarket();
		KingDeck kingDeck = new KingDeck(new ArrayList<RewardTile>());
		Map<CityColor, Bonus> map = new HashMap<>();
		
		this.gameBoard = GameBoard.createGameBoard(politicDeck, pool, kingBalcony, regions, track, king, market, kingDeck, map);
		
		token = gameBoard.registerPlayer("Marco").getToken();
        gameBoard.start();
		gameBoard.getCurrentPlayer().addAssistant(assistantToSell);
        gameBoard.getScheduler().getCurrentPlayer().setRemainingMainActions(0);
        gameBoard.getScheduler().getCurrentPlayer().setRemainingQuickActions(0);
		gameBoard.getScheduler().regularActionPerformed();
		//gameBoard.registerPlayer("Davide");
		//gameBoard.registerPlayer("Luca");
	}
	
	@Test
	public void testBuildActionShouldAssignTheToken(){
		Action action = new SellAssistant(3, 15);
		
		assertEquals(15, action.getToken());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testBuildActionWithPriceLessThan1ShouldThrowException(){
		new SellAssistant(-3, 19);
	}
	
	@Test (expected = NoRemainingAssistantsException.class)
	public void testTryToSellAssistantWithoutHavingItShouldThrowException() throws Exception {
		gameBoard.getCurrentPlayer().takeAssistants(2);
		Action action = new SellAssistant(5, token);
		
		action.apply(gameBoard);
	}

	@Test
	public void testApplyActionCheckChanges() throws Exception {
		Action action = new SellAssistant(4, token);
		action.apply(gameBoard);
		
		assistantToSell.setPrice(4);
		assertTrue(gameBoard.getMarket().getOnSale().contains(assistantToSell));
		assertEquals(1, gameBoard.getCurrentPlayer().getAssistantsNumber());
	}
}
