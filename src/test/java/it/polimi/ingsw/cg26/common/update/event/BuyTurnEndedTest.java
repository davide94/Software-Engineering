package it.polimi.ingsw.cg26.common.update.event;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import it.polimi.ingsw.cg26.client.model.Model;

public class BuyTurnEndedTest {

	@Test
	public void testCreationWithoutExcpetion() {
		Event buyTurnEnded = new BuyTurnEnded();
		
		assertNotNull(buyTurnEnded);
	}
	
	@Test
	public void test() throws Exception {
		Model model = new Model();
		Event buyTurnEnded = new BuyTurnEnded();
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	      System.setOut(new PrintStream(outContent));
		
		buyTurnEnded.apply(model);
		
		String stringa = "buyTurnEnded";
		for(int i=0; i<stringa.length(); i++) {
			assertTrue(outContent.toString().contains(stringa));
		}
	}
}
