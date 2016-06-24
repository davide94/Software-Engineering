package it.polimi.ingsw.cg26.common.update.event;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import it.polimi.ingsw.cg26.client.model.Model;

public class MarketStartedTest {

	@Test
	public void testCreationWithoutExcpetion() {
		Event marketStarted = new MarketStarted();
		
		assertNotNull(marketStarted);
	}
	
	@Test
	public void test() throws Exception {
		Model model = new Model();
		Event marketStarted = new MarketStarted();
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	      System.setOut(new PrintStream(outContent));
		
		marketStarted.apply(model);
		
		String stringa = "marketStarted";
		for(int i=0; i<stringa.length(); i++) {
			assertEquals(stringa.charAt(i), outContent.toString().charAt(i));
		}
	}

}
