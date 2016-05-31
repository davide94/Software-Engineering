package it.polimi.ingsw.cg26.server.actions.market;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.InvalidSellableException;
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
import it.polimi.ingsw.cg26.server.model.cards.KingDeck;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import it.polimi.ingsw.cg26.server.model.market.Market;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class BuyAssistantTest {

	private GameBoard gameBoard;
	
	private Player player1;
	
	private Player player2;
	
	private Player player3;
	
	private Assistant assistantToBuy;
	
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
		tileCities.add(City.createCity("Milano", CityColor.createCityColor("grigio"), new RewardTile(new ArrayList<Bonus>())));
		tileCities.add(City.createCity("Torino", CityColor.createCityColor("orosa"), new RewardTile(new ArrayList<Bonus>())));
		BusinessPermissionTile tile = new BusinessPermissionTile(tileCities, new RewardTile(new ArrayList<Bonus>()));
		tile.setOwner(player3);
		tile.setPrice(8);
		
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
		market.addToMarket(card2);
		market.addToMarket(card3);
		market.addToMarket(tile);
		market.addToMarket(assistantToBuy);
		market.addToMarket(assistant1);
		
		return market;
	}
	
	@Before
	public void setUp(){
		this.player1 = new Player(1, "Marco", NobilityCell.createNobilityCell(1, null, new RewardTile(new ArrayList<Bonus>())), 0, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		this.player2 = new Player(2, "Davide", NobilityCell.createNobilityCell(1, null, new RewardTile(new ArrayList<Bonus>())), 0, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		this.player3 = new Player(2, "Luca", NobilityCell.createNobilityCell(1, null, new RewardTile(new ArrayList<Bonus>())), 0, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());

		LinkedList<PoliticCard> politicCards = new LinkedList<>();
		politicCards.add(new PoliticCard(new PoliticColor("c1")));
		PoliticDeck politicDeck = new PoliticDeck(politicCards);
		List<Councillor> pool = new ArrayList<>();
		Balcony kingBalcony = Balcony.createBalcony(4);
		List<Region> regions = new ArrayList<>();
		NobilityTrack track = NobilityTrack.createNobilityTrack(NobilityCell.createNobilityCell(1, null, new RewardTile(new ArrayList<Bonus>())));
		King king = King.createKing(City.createCity("c1", CityColor.createCityColor("aa"), new RewardTile(new ArrayList<Bonus>())));
		Market market = buildMarket();
		KingDeck kingDeck = new KingDeck(new ArrayList<RewardTile>());
		Map<CityColor, RewardTile> map = new HashMap<>();
		
		this.gameBoard = GameBoard.createGameBoard(politicDeck, pool, kingBalcony, regions, track, king, market, kingDeck, map);
		
		/*List<Assistant> assistants = new ArrayList<>();
		for(int i=0; i<3; i++)
			assistants.add(new Assistant());*/
		gameBoard.registerPlayer(player1);
		gameBoard.registerPlayer(player2);
		gameBoard.registerPlayer(player3);
	}

	@Test
	public void testBuildActionShouldAssignTheToken(){
		Action action = new BuyAssistant(12, assistantToBuy.getState());
		
		assertEquals(12, action.getToken());
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildActionWithAssistantDTONullShouldThrowAnException(){
		new BuyAssistant(67, null);
	}
	
	@Test (expected = InvalidSellableException.class)
	public void testApplyActionWithASellableNullShouldThrowException(){
		Buy action = new BuyAssistant(1, this.assistantToBuy.getState());
		
		action.buy(gameBoard, gameBoard.getCurrentPlayer(), null);
	}
	
	@Test (expected = NotEnoughMoneyException.class)
	public void testApplyActionWithAPlayerThatTriesToBuyWithoutEnoughMoneyShouldThrowException(){
		Action action = new BuyAssistant(1, this.assistantToBuy.getState());
		
		action.apply(gameBoard);
	}
	
	@Test
	public void testApplyActionCheckChanges(){
		Action action = new BuyAssistant(1, this.assistantToBuy.getState());
		gameBoard.getCurrentPlayer().addCoins(10);
		action.apply(gameBoard);
		
		assertEquals(gameBoard.getCurrentPlayer(), assistantToBuy.getOwner());
		assertEquals(1, gameBoard.getCurrentPlayer().getAssistantsNumber());
		assertEquals(8, gameBoard.getCurrentPlayer().getCoinsNumber());
		assertEquals(2, player2.getCoinsNumber());
		assertFalse(gameBoard.getMarket().getOnSale().contains(assistantToBuy));
	}
}
