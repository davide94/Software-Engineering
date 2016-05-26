package it.polimi.ingsw.cg26.server.model.bonus;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.BonusDTO;
import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class MainActionBonusTest {

	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithNegativeMultiplicity() {
		new MainActionBonus(-1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithZeroMultiplicity() {
		new MainActionBonus(0);
	}
	
	@Test
	public void testApplyOneMoreActionToAPlayerThatHasArleadyOneMainActionToDo(){
		NobilityCell cell = NobilityCell.createNobilityCell(1, null, new ArrayList<>());
		Player player = new Player(1, "Marco", cell, 2, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		MainActionBonus bonus = new MainActionBonus(1);
		bonus.apply(player);
		player.performMainAction();
		assertEquals(player.canPerformMainAction(), true);
	}
	
	@Test
	public void testApplyOneMoreActionToAPlayerThatHasAlreadyOneMainActionToDoAndPerform2MainActionShuldntBePossibleToPerformAnotherOne(){
		NobilityCell cell = NobilityCell.createNobilityCell(1, null, new ArrayList<>());
		Player player = new Player(1, "Marco", cell, 2, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		MainActionBonus bonus = new MainActionBonus(1);
		bonus.apply(player);
		player.performMainAction();
		player.performMainAction();
		assertEquals(false, player.canPerformMainAction());
	}

	@Test
	public void testGetState(){
		MainActionBonus bonus = new MainActionBonus(3);
		BonusDTO bonusDTO = bonus.getState();
		
		assertEquals("Additional main actions", bonusDTO.getKind());
		assertEquals(3, bonusDTO.getMultiplicity());
	}
	
	@Test
	public void testToString(){
		MainActionBonus bonus = new MainActionBonus(2);
		
		assertEquals("MainActionBonus{multiplicity=2}", bonus.toString());
	}
}
