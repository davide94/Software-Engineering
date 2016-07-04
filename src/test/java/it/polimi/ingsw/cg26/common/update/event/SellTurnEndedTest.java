package it.polimi.ingsw.cg26.common.update.event;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import it.polimi.ingsw.cg26.client.model.Model;

public class SellTurnEndedTest {

	@Test
	public void testCreationWithoutExcpetion() {
		Event sellTurnEnded = new SellTurnEnded();
		
		assertNotNull(sellTurnEnded);
	}
	
	@Test
	public void test() throws Exception {
		Model model = new Model();
		Event sellTurnEnded = new SellTurnEnded();
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	      System.setOut(new PrintStream(outContent));
		
		sellTurnEnded.apply(model);
		
		String stringa = "sellTurnEnded";
		for(int i=0; i<stringa.length(); i++) {
			assertTrue(outContent.toString().contains(stringa));
		}
	}

}
