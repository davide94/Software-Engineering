package it.polimi.ingsw.cg26.common.commands;

import static org.junit.Assert.*;

import org.junit.Test;

public class FoldQuickActionCommandTest {

	@Test
	public void testCreation() {
		FoldQuickActionCommand command = new FoldQuickActionCommand();
		
		assertNotNull(command);
	}

}
