package it.polimi.ingsw.cg26.common.update.event;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import it.polimi.ingsw.cg26.client.model.Model;

public class ActionSuccessFulTest {

	@Test
	public void testCreationWithoutExcpetion() {
		Event actionSuccessful = new ActionSuccessFul();
		
		assertNotNull(actionSuccessful);
	}
	
	@Test
	public void test() throws Exception {
		Model model = new Model();
		Event actionSuccessful = new ActionSuccessFul();
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	      System.setOut(new PrintStream(outContent));
		
		actionSuccessful.apply(model);
		
		String stringa = "actionSuccessful";
		for(int i=0; i<stringa.length(); i++) {
			assertEquals(stringa.charAt(i), outContent.toString().charAt(i));
		}
	}

}
