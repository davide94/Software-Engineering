package it.polimi.ingsw.cg26.common.update.event;

import it.polimi.ingsw.cg26.client.model.Model;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ActionFailedTest {
	
	@Test
	public void testCreationWithoutExcpetion() {
		Event actionFailed = new ActionFailed();
		
		assertNotNull(actionFailed);
	}
	
	@Test
	public void testCreationWithException() {
		Event actionFailed = new ActionFailed("aaa");
		
		assertNotNull(actionFailed);
	}
	
	@Test
	public void test() {
		Model model = new Model();
		ActionFailed actionFailed = new ActionFailed("aaa");
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	      System.setOut(new PrintStream(outContent));
		
		actionFailed.apply(model);
		
		String stringa = "actionFailed";
		for(int i=0; i<stringa.length(); i++) {
			assertTrue(outContent.toString().contains(stringa));
		}
	}
}
