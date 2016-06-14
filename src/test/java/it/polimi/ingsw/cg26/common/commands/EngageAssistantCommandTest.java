package it.polimi.ingsw.cg26.common.commands;

import static org.junit.Assert.*;

import org.junit.Test;

public class EngageAssistantCommandTest {

	@Test
	public void testCreation() {
		EngageAssistantCommand command = new EngageAssistantCommand();
		
		assertNotNull(command);
	}

}
