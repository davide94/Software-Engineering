package it.polimi.ingsw.cg26.server.model.bonus;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.update.request.BPTRequest;
import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class TakeBPTBonusTest {

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
	public void testCreationShouldFailWithNegativeMultiplicity() {
		new TakeBPTBonus(bonus, -3);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithMultiplicityMoreThan1() {
		new TakeBPTBonus(bonus, 6);
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithBonusToDecorateNull() {
		new TakeBPTBonus(null, 1);
	}

	@Test
	public void testApply() throws Exception {
		Bonus bonusBPT = new TakeBPTBonus(bonus, 1);
		
		assertFalse(player.canPerformChooseAction());
		assertEquals(0, player.getPendingRequest().size());
		
		bonusBPT.apply(player);
		
		assertTrue(player.canPerformChooseAction());
		assertEquals(1, player.getPendingRequest().size());
		assertEquals(new BPTRequest(), player.getPendingRequest().get(0));
	}
}
