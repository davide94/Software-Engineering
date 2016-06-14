package it.polimi.ingsw.cg26.common.commands;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.server.model.board.Councillor;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;

public class ElectKingAsQuickActionCommandTest {

	private Councillor councillor;
	
	@Before
	public void setUp() {
		councillor = Councillor.createCouncillor(new PoliticColor("arancione"));
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithCouncillorNull() {
		new ElectKingAsQuickActionCommand(null);
	}
	
	@Test
	public void testGetters() {
		ElectKingAsQuickActionCommand command = new ElectKingAsQuickActionCommand(councillor.getState());
		CouncillorDTO councillor = Councillor.createCouncillor(new PoliticColor("arancione")).getState();
		CouncillorDTO councillor2 = Councillor.createCouncillor(new PoliticColor("verde")).getState();
		
		assertEquals(councillor, command.getCouncillor());
		assertNotEquals(councillor2, command.getCouncillor());
	}

}
