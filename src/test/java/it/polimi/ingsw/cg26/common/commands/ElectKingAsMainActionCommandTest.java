package it.polimi.ingsw.cg26.common.commands;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.server.model.board.Councillor;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;

public class ElectKingAsMainActionCommandTest {

	private Councillor councillor;
	
	@Before
	public void setUp() {
		councillor = Councillor.createCouncillor(new PoliticColor("rosso"));
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithCouncillorNull() {
		new ElectKingAsMainActionCommand(null);
	}
	
	@Test
	public void testGetters() {
		ElectKingAsMainActionCommand command = new ElectKingAsMainActionCommand(councillor.getState());
		CouncillorDTO councillor = Councillor.createCouncillor(new PoliticColor("rosso")).getState();
		
		assertEquals(councillor, command.getCouncillor());
	}

}
