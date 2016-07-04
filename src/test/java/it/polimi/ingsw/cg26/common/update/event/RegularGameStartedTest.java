package it.polimi.ingsw.cg26.common.update.event;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import it.polimi.ingsw.cg26.client.model.Model;

public class RegularGameStartedTest {

	@Test
	public void testCreationWithoutExcpetion() {
		Event regularGameStarted = new RegularGameStarted();
		
		assertNotNull(regularGameStarted);
	}
	
	@Test
	public void test() throws Exception {
		Model model = new Model();
		Event regularGameStarted = new RegularGameStarted();
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	      System.setOut(new PrintStream(outContent));
		
		regularGameStarted.apply(model);
		
		String stringa = "regularGameStarted";
		for(int i=0; i<stringa.length(); i++) {
			assertTrue(outContent.toString().contains(stringa));
		}
	}

}
