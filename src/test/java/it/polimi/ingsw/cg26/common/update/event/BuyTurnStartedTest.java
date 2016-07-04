package it.polimi.ingsw.cg26.common.update.event;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import it.polimi.ingsw.cg26.client.model.Model;

public class BuyTurnStartedTest {

	@Test
	public void testCreationWithoutExcpetion() {
		Event buyTurnStarted = new BuyTurnStarted();
		
		assertNotNull(buyTurnStarted);
	}
	
	@Test
	public void test() throws Exception {
		Model model = new Model();
		Event buyTurnStarted = new BuyTurnStarted();
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	      System.setOut(new PrintStream(outContent));
		
		buyTurnStarted.apply(model);
		
		String stringa = "buyTurnStarted";
		for(int i=0; i<stringa.length(); i++) {
			assertTrue(outContent.toString().contains(stringa));
		}
	}

}
