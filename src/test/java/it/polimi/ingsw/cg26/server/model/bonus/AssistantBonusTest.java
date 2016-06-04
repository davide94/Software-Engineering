package it.polimi.ingsw.cg26.server.model.bonus;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class AssistantBonusTest {

	private Bonus bonus;
	
	@Before
	public void setUp(){
		this.bonus = new EmptyBonus();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithNegativeMultiplicity() {
		new AssistantBonus(bonus, -1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithZeroMultiplicity() {
		new AssistantBonus(bonus, 0);
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldThrowAnExceptionWithDecoratedBonusNull(){
		new AssistantBonus(null, 3);
	}
	
	@Test
	public void testApply3AssistantsToAPlayerWith0AssistantsShouldHave3Assistants(){
		NobilityCell cell = NobilityCell.createNobilityCell(1, null, new EmptyBonus());
		Player player = new Player(1, "Marco", cell, 2, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		Bonus assBonus = new AssistantBonus(bonus, 3);
		assBonus.apply(player);
		
		assertEquals(3, player.getAssistantsNumber());
	}

	@Test
	public void testApplyDecoratedBonus(){
		NobilityCell cell = NobilityCell.createNobilityCell(1, null, new EmptyBonus());
		Player player = new Player(1, "Marco", cell, 2, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		Bonus assBonus = new AssistantBonus(new CoinBonus(bonus, 4), 3);
		assBonus.apply(player);
		
		assertEquals(6, player.getCoinsNumber());
		assertEquals(3, player.getAssistantsNumber());
	}
	
	@Test
	public void testApply1AssistantToAPlayerWith5AssistantsShouldHave6Assistants(){
		NobilityCell cell = NobilityCell.createNobilityCell(1, null, new EmptyBonus());
		Player player = new Player(1, "Marco", cell, 2, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		for(int i=0; i<5; i++)
			player.addAssistant(new Assistant());
		Bonus assBonus = new AssistantBonus(bonus, 1);
		assBonus.apply(player);
		
		assertEquals(6, player.getAssistantsNumber());
	}
	
	@Test
	public void testGetState(){
		Bonus assBonus = new CoinBonus(new AssistantBonus(bonus, 3), 8);
		BonusDTO bonusDTO = assBonus.getState();
		
		assertEquals("\nAssistantBonus{multiplicity=3}\nCoinBonus{multiplicity=8}", bonusDTO.toString());
	}
	
	@Test
	public void testToStringWithMultiplicity3(){
		Bonus assBonus = new CoinBonus(new AssistantBonus(bonus, 3), 5);
		assertEquals("\nAssistantBonus{multiplicity=3}\nCoinBonus{multiplicity=5}", assBonus.toString());
	}
	
	@Test
	public void testHashCodeEquals(){
		Bonus assBonus1 = new AssistantBonus(bonus, 3);
		Bonus assBonus2 = new AssistantBonus(bonus, 3);
		
		assertTrue(assBonus1.equals(assBonus2));
		assertFalse(assBonus1.equals(null));
		assertEquals(assBonus1.hashCode(), assBonus2.hashCode());
	}
}
