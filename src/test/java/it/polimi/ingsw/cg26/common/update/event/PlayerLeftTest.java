package it.polimi.ingsw.cg26.common.update.event;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.update.Update;

public class PlayerLeftTest {

	@Test
	public void testCreationWithoutExcpetion() {
		Update playerLeft = new PlayerLeft();
		
		assertNotNull(playerLeft);
	}
	
	@Test
	public void test() throws Exception {
		Model model = new Model();
		Update playerLeft = new PlayerLeft();
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	      System.setOut(new PrintStream(outContent));
		
		playerLeft.apply(model);
		
		String stringa = "a Player Left";
		for(int i=0; i<stringa.length(); i++) {
			assertEquals(stringa.charAt(i), outContent.toString().charAt(i));
		}
	}

}
