package it.polimi.ingsw.cg26.server.model.bonus;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.update.request.PlayerBPTRequest;
import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class TakePlayerBPTBonusTest {

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
		new TakePlayerBPTBonus(bonus, 0);
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithBonusToDecorateNull() {
		new TakePlayerBPTBonus(null, 2);
	}
	
	@Test
	public void testApply() throws Exception {
		Bonus playerBPTBonus = new TakePlayerBPTBonus(bonus, 2);
		
		assertFalse(player.canPerformChooseAction());
		assertEquals(0, player.getPendingRequest().size());
		
		playerBPTBonus.apply(player);
		
		assertEquals(1, player.getPendingRequest().size());
		assertTrue(player.canPerformChooseAction());
		assertEquals(new PlayerBPTRequest(2), player.getPendingRequest().get(0));
	}

}
