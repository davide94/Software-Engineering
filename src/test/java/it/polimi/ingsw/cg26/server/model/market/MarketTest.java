package it.polimi.ingsw.cg26.server.model.market;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.AssistantDTO;
import it.polimi.ingsw.cg26.common.dto.MarketDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.server.exceptions.NotExistingSellableException;
import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class MarketTest {
	
	@Test
	public void testConstructAMarket(){
		Market market = new Market();
		
		assertEquals(new ArrayList<Sellable>(), market.getOnSale());
	}
	
	@Test
	public void testGetState(){
		Market market = new Market();
		PoliticCard card = new PoliticCard(new PoliticColor("arancione"));
		List<City> cities = new ArrayList<>();
		List<Bonus> tileBonuses = new ArrayList<>();
		BusinessPermissionTile tile = new BusinessPermissionTile(cities, new RewardTile(tileBonuses));
		Assistant assistant = new Assistant();
		market.addToMarket(tile);
		market.addToMarket(assistant);
		market.addToMarket(card);
		MarketDTO marketDTO = market.getState();
		
		assertEquals(3, marketDTO.getOnSale().size());
		assertTrue(marketDTO.getOnSale().contains(tile.getState()));
		assertTrue(marketDTO.getOnSale().contains(card.getState()));
		assertTrue(marketDTO.getOnSale().contains(assistant.getState()));
	}
	
	@Test (expected = NullPointerException.class)
	public void testAddToMarketANullShouldThrowAnException(){
		Market market = new Market();
		market.addToMarket(null);
	}
	
	@Test
	public void testAddToMarketAPoliticCard(){
		Market market = new Market();
		PoliticCard card = new PoliticCard(new PoliticColor("arancione"));
		market.addToMarket(card);
		
		assertTrue(market.getOnSale().contains(card));
	}
	
	@Test
	public void testAddToMarketABPTAndAnAssistant(){
		Market market = new Market();
		List<City> cities = new ArrayList<>();
		List<Bonus> tileBonuses = new ArrayList<>();
		BusinessPermissionTile tile = new BusinessPermissionTile(cities, new RewardTile(tileBonuses));
		Assistant assistant = new Assistant();
		
		market.addToMarket(tile);
		market.addToMarket(assistant);
		
		assertTrue(market.getOnSale().contains(tile));
		assertTrue(market.getOnSale().contains(assistant));
	}
	
	@Test
	public void testGetOnSale(){
		Market market = new Market();
		PoliticCard card = new PoliticCard(new PoliticColor("giallo"));
		market.addToMarket(card);
		market.addToMarket(new Assistant());
		
		List<Sellable> onSale = new ArrayList<>();
		onSale.add(card);
		onSale.add(new Assistant());
		
		assertEquals(onSale, market.getOnSale());
	}
	
	@Test
	public void testGetRealSellableWithAPoliticCard(){
		Market market = new Market();
		PoliticCard card = new PoliticCard(new PoliticColor("blu"));
		market.addToMarket(card);
		PoliticCardDTO cardDTO = card.getState();
		
		assertEquals(card, market.getRealSellable(cardDTO));
	}
	
	@Test
	public void testGetRealSellableWithAnAssistant(){
		Market market = new Market();
		Player player1 = new Player(1, "Marco", NobilityCell.createNobilityCell(1, null, new RewardTile(new ArrayList<Bonus>())), 5, new ArrayList<PoliticCard>(), new ArrayList<Assistant>());
		Assistant assistant = new Assistant();
		assistant.setPrice(3);
		assistant.setOwner(player1);
		market.addToMarket(assistant);
		AssistantDTO assistantDTO = assistant.getState();
		
		assertEquals(assistant, market.getRealSellable(assistantDTO));
	}
	
	@Test (expected = NotExistingSellableException.class)
	public void testGetRealSellableShouldThrowAnExceptionIfThereIsntTheRequestedSellable(){
		Market market = new Market();
		PoliticCard card = new PoliticCard(new PoliticColor("giallo"));
		market.addToMarket(card);
		PoliticCard card2 = new PoliticCard(new PoliticColor("verde"));
		PoliticCardDTO cardDTO = card2.getState();
		
		market.getRealSellable(cardDTO);
	}
	
	@Test (expected = NullPointerException.class)
	public void testGetRealSellableShouldThrowAnExceptionIfTheArgumentIsNull(){
		Market market = new Market();
		
		market.getRealSellable(null);
	}
	
	@Test (expected = NullPointerException.class)
	public void testRemoveFromMarketShouldThrowAnExceptionIfTheArgumentIsNull(){
		Market market = new Market();
		
		market.removeFromMarket(null);
	}
	
	@Test
	public void testRemoveFromMarket(){
		Market market = new Market();
		PoliticCard card = new PoliticCard(new PoliticColor("verde"));
		market.addToMarket(card);
		market.addToMarket(new Assistant());
		Sellable removedSellable = market.removeFromMarket(card);
		
		assertTrue(!market.getOnSale().contains(card));
		assertEquals(card, removedSellable);
	}
	
	@Test
	public void testEndMarket(){
		NobilityCell cell = NobilityCell.createNobilityCell(1, null, new RewardTile(new ArrayList<Bonus>()));
		Player player1 = new Player(1, "Marco", cell, 4, new LinkedList<PoliticCard>(), new LinkedList<Assistant>());
		//created player with 0 assistants and 0 politic cards
		
		Market market = new Market();
		PoliticCard card = new PoliticCard(new PoliticColor("arancione"));
		card.setPrice(5);
		card.setOwner(player1);
		Assistant assistant = new Assistant();
		assistant.setOwner(player1);
		assistant.setPrice(5);
		market.addToMarket(card);
		market.addToMarket(assistant);
		
		market.endMarket();
		
		PoliticCard playerCard = player1.takeCard(card.getState());
		assertEquals(1, player1.getAssistantsNumber());
		assertEquals(card.getColor(), playerCard.getColor());
		assertEquals(0, playerCard.getPrice());
		assertTrue(market.getOnSale().isEmpty());
		//TODO aggiungere getter a player per controlli migliori
	}

}
