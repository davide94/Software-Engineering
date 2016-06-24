package it.polimi.ingsw.cg26.common.update.event;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.server.exceptions.InvalidRegionException;
import it.polimi.ingsw.cg26.server.exceptions.InvalidTileException;

public class ActionFailedTest {
	
	@Test
	public void testCreationWithoutExcpetion() {
		Event actionFailed = new ActionFailed();
		
		assertNotNull(actionFailed);
	}
	
	@Test
	public void testCreationWithException() {
		Event actionFailed = new ActionFailed(new InvalidRegionException());
		
		assertNotNull(actionFailed);
	}

	@Test (expected = InvalidTileException.class)
	public void testRethrowException() throws Exception {
		ActionFailed actionFailed = new ActionFailed(new InvalidTileException());
		
		actionFailed.why();
	}
	
	@Test
	public void test() {
		Model model = new Model();
		ActionFailed actionFailed = new ActionFailed(new InvalidTileException());
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	      System.setOut(new PrintStream(outContent));
		
		actionFailed.apply(model);
		
		String stringa = "actionFailed";
		for(int i=0; i<stringa.length(); i++) {
			assertEquals(stringa.charAt(i), outContent.toString().charAt(i));
		}
	}
}
