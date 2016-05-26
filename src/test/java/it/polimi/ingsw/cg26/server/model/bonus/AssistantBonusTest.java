package it.polimi.ingsw.cg26.server.model.bonus;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Test;

import it.polimi.ingsw.cg26.common.state.BonusState;
import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class AssistantBonusTest {

	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithNegativeMultiplicity() {
		new AssistantBonus(-1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithZeroMultiplicity() {
		new AssistantBonus(0);
	}
	
	@Test
	public void testApply3AssistantsToAPlayerWith0AssistantsShouldHave3Assistants(){
		NobilityCell cell = NobilityCell.createNobilityCell(1, null, new ArrayList<>());
		Player player = new Player(1, "Marco", cell, 2, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		AssistantBonus bonus = new AssistantBonus(3);
		bonus.apply(player);
		
		assertEquals(3, player.getAssistantsNumber());
	}

	@Test
	public void testApply1AssistantToAPlayerWith5AssistantsShouldHave6Assistants(){
		NobilityCell cell = NobilityCell.createNobilityCell(1, null, new ArrayList<>());
		Player player = new Player(1, "Marco", cell, 2, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		for(int i=0; i<5; i++)
			player.addAssistant(new Assistant());
		AssistantBonus bonus = new AssistantBonus(1);
		bonus.apply(player);
		
		assertEquals(6, player.getAssistantsNumber());
	}
	
	@Test
	public void testGetState(){
		AssistantBonus bonus = new AssistantBonus(4);
		BonusState bonusState = bonus.getState();
		
		assertEquals(bonus.getMultiplicity(), bonusState.getMultiplicity());
		assertEquals("Assistants", bonusState.getName());
	}
	
	@Test
	public void testToStringWithMultiplicity3(){
		AssistantBonus bonus = new AssistantBonus(3);
		assertEquals("AssistantBonus{multiplicity=3}", bonus.toString());
	}
	
}
