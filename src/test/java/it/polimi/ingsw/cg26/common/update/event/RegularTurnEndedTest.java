package it.polimi.ingsw.cg26.common.update.event;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import it.polimi.ingsw.cg26.client.model.Model;

public class RegularTurnEndedTest {

	@Test
	public void testCreationWithoutExcpetion() {
		Event regularTurnEnded = new RegularTurnEnded();
		
		assertNotNull(regularTurnEnded);
	}
	
	@Test
	public void test() throws Exception {
		Model model = new Model();
		Event regularTurnEnded = new RegularTurnEnded();
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	      System.setOut(new PrintStream(outContent));
		
		regularTurnEnded.apply(model);
		
		String stringa = "turnEnded";
		for(int i=0; i<stringa.length(); i++) {
			assertTrue(outContent.toString().contains(stringa));
		}
	}

}
