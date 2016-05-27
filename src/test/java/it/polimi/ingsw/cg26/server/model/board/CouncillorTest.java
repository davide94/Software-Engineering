package it.polimi.ingsw.cg26.server.model.board;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;

public class CouncillorTest {
	
	private PoliticColor blu;
	
	
	@Before
    public void setUp() throws Exception {
		
		blu=new PoliticColor("blu");
		
	}
	
	

	@Test (expected=NullPointerException.class)
	public void testshouldNotCreateCouncillor() {
		Councillor.createCouncillor(null);
	}
	
	
	@Test
	public void testShouldCreateCouncillor() {
		Councillor c1= Councillor.createCouncillor(new PoliticColor("blu"));
		assertNotNull(c1);
	}
	
	
	@Test
	public void testGetColor() {
		Councillor c1= Councillor.createCouncillor(new PoliticColor("blu"));
		
		assertEquals(c1.getColor(), blu);
		
	}
	

}
