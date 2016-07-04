package it.polimi.ingsw.cg26.common.update.event;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import it.polimi.ingsw.cg26.client.model.Model;

public class RegularTurnStartedTest {

	@Test
	public void testCreationWithoutExcpetion() {
		Event regularTurnStarted = new RegularTurnStarted();
		
		assertNotNull(regularTurnStarted);
	}
	
	@Test
	public void test() throws Exception {
		Model model = new Model();
		Event regularTurnStarted = new RegularTurnStarted();
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	      System.setOut(new PrintStream(outContent));
		
		regularTurnStarted.apply(model);
		
		String stringa = "turnStarted";
		for(int i=0; i<stringa.length(); i++) {
			assertTrue(outContent.toString().contains(stringa));
		}
	}
}
