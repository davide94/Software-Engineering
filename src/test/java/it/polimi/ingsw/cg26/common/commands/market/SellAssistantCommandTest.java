package it.polimi.ingsw.cg26.common.commands.market;

import static org.junit.Assert.*;

import org.junit.Test;

public class SellAssistantCommandTest {

	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithPriceZero() {
		new SellAssistantCommand(0);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithNegativePrice() {
		new SellAssistantCommand(-3);
	}
	
	@Test
	public void testGetPrice() {
		SellAssistantCommand command = new SellAssistantCommand(4);
		
		assertEquals(4, command.getPrice());
	}
}
