package it.polimi.ingsw.cg26.common.update;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.event.SellTurnStarted;

public class PrivateUpdateTest {

	@Test (expected = NullPointerException.class)
	public void testCreationShouldFailWithUpdateNull() {
		new PrivateUpdate(null, 1956);
	}
	
	@Test
	public void testCreation1() {
		Update update = new PrivateUpdate(new BasicChange(), 1334);
		
		assertNotNull(update);
	}
	
	@Test
	public void testCreation2() {
		Update update = new PrivateUpdate(new SellTurnStarted(), 1284);
		
		assertNotNull(update);
	}

}
