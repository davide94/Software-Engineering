package it.polimi.ingsw.cg26.server.model.bonus;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.update.request.CityBonusRequest;
import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class TakeYourCityBonusTest {

	private Player player;
	
	private Bonus bonus;
	
	@Before
	public void setUp(){
		this.bonus = new EmptyBonus();
		
		NobilityCell cell = NobilityCell.createNobilityCell(1, null, new EmptyBonus());
		List<PoliticCard> playerCards = new LinkedList<>();
		player = new Player(1, "Marco", cell, 2, playerCards, new LinkedList<Assistant>());
	}

	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithMultiplicityLessThan1() {
		new TakeYourCityBonus(bonus, -2);
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithBonusToDecorateNull() {
		new TakeYourCityBonus(null, 2);
	}
	
	@Test
	public void testApply() throws Exception {
		Bonus cityBonus = new TakeYourCityBonus(bonus, 3);
		
		assertFalse(player.canPerformChooseAction());
		assertEquals(0, player.getPendingRequest().size());
		
		cityBonus.apply(player);
		
		assertEquals(1, player.getPendingRequest().size());
		assertTrue(player.canPerformChooseAction());
		assertEquals(new CityBonusRequest(3), player.getPendingRequest().get(0));
		
	}
}