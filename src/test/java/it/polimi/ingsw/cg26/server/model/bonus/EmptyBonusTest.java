package it.polimi.ingsw.cg26.server.model.bonus;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EmptyBonusTest {

	private Bonus bonus;
	
	@Before
	public void setUp(){
		bonus = new EmptyBonus();
	}
	
	@Test
	public void testToString(){
		assertEquals("", bonus.toString());
	}
	
	@Test
	public void testEquals(){
		
		assertTrue(bonus.equals(bonus));
		assertTrue(bonus.equals(new EmptyBonus()));
		assertFalse(bonus.equals(null));
		assertFalse(bonus.equals(new AssistantBonus(bonus, 3)));
	}

}
