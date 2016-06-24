package it.polimi.ingsw.cg26.common.update.event;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import it.polimi.ingsw.cg26.client.model.Model;

public class SellTurnStartedTest {

	@Test
	public void testCreationWithoutExcpetion() {
		Event sellTurnStarted = new SellTurnStarted();
		
		assertNotNull(sellTurnStarted);
	}
	
	@Test
	public void test() throws Exception {
		Model model = new Model();
		Event sellTurnStarted = new SellTurnStarted();
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	      System.setOut(new PrintStream(outContent));
		
		sellTurnStarted.apply(model);
		
		String stringa = "sellTurnStarted";
		for(int i=0; i<stringa.length(); i++) {
			assertEquals(stringa.charAt(i), outContent.toString().charAt(i));
		}
	}

}
