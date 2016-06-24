package it.polimi.ingsw.cg26.common.update.event;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import it.polimi.ingsw.cg26.client.model.Model;

public class MatchEndedTest {

	@Test
	public void testCreationWithoutExcpetion() {
		Event matchEnded = new MatchEnded();
		
		assertNotNull(matchEnded);
	}
	
	@Test
	public void test() throws Exception {
		Model model = new Model();
		Event matchEnded = new MatchEnded();
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	      System.setOut(new PrintStream(outContent));
		
		matchEnded.apply(model);
		
		String stringa = "matchEnded";
		for(int i=0; i<stringa.length(); i++) {
			assertEquals(stringa.charAt(i), outContent.toString().charAt(i));
		}
	}
}
