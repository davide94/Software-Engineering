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

public class MainActionBonusTest {

	private Bonus bonus;
	
	@Before
	public void setUp(){
		this.bonus = new EmptyBonus();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithNegativeMultiplicity() {
		new MainActionBonus(bonus, -1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithZeroMultiplicity() {
		new MainActionBonus(bonus, 0);
	}
	
	@Test
	public void testApplyOneMoreActionToAPlayerThatHasArleadyOneMainActionToDo() throws Exception {
		NobilityCell cell = NobilityCell.createNobilityCell(1, null, new EmptyBonus());
		Player player = new Player(1, "Marco", cell, 2, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		MainActionBonus mainBonus = new MainActionBonus(bonus, 1);
		mainBonus.apply(player);
		assertEquals(player.canPerformMainAction(), true);
	}
	
	@Test
	public void testApplyOneMoreActionToAPlayerThatHasAlreadyOneMainActionToDoAndPerform2MainActionShuldntBePossibleToPerformAnotherOne() throws Exception {
		NobilityCell cell = NobilityCell.createNobilityCell(1, null, new EmptyBonus());
		Player player = new Player(1, "Marco", cell, 2, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		MainActionBonus mainBonus = new MainActionBonus(bonus, 1);
		mainBonus.apply(player);
		player.performMainAction();
		assertEquals(false, player.canPerformMainAction());
	}

	@Test
	public void testGetState(){
		MainActionBonus mainBonus = new MainActionBonus(bonus, 3);
		BonusDTO bonusDTO = mainBonus.getState();
		
		assertEquals("3 Main Actions more", bonusDTO.toString());
	}
	
	@Test
	public void testToString(){
		MainActionBonus mainBonus = new MainActionBonus(bonus, 2);
		
		assertEquals("\nMainActionBonus{multiplicity=2}", mainBonus.toString());
	}
	
	@Test
	public void testHashCodeEquals(){
		MainActionBonus mainBonus1 = new MainActionBonus(bonus, 2);
		MainActionBonus mainBonus2 = new MainActionBonus(bonus, 2);
		
		assertTrue(mainBonus1.equals(mainBonus2));
		assertEquals(mainBonus1.hashCode(), mainBonus2.hashCode());
	}
}
