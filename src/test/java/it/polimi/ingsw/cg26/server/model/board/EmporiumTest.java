package it.polimi.ingsw.cg26.server.model.board;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.state.EmporiumState;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class EmporiumTest {
	
	private Player Davide; 
	private Player Luca;
	private Emporium empDavide;
	
	 @Before
	    public void setUp() throws Exception {
		 
		 Davide=new Player(1234, "Davide", NobilityCell.createNobilityCell(10, null, new LinkedList<Bonus>()), 10, new LinkedList<>(), new LinkedList<>());
		 Luca=new Player(1235, "Luca", NobilityCell.createNobilityCell(11, null, new LinkedList<Bonus>()), 11, new LinkedList<>(), new LinkedList<>());
		 empDavide= Emporium.createEmporium(Davide);
	 }
	
	 
	@Test(expected=NullPointerException.class)
	public void testCreateEmporium() {
		assertNull(Emporium.createEmporium(null));
		assertNotNull(Emporium.createEmporium(Davide));
	}

	

	@Test
	public void testGetPlayer() {
		assertEquals(Emporium.createEmporium(Davide).getPlayer(), Davide);
	}

	@Test
	public void testToString() {
		Emporium emporio1=Emporium.createEmporium(Davide);
		assertEquals(emporio1.toString(), "Emporium{" +
				"player=" + "Davide" +
				'}');
		
	}
	
	@Test
	public void testGetState() {
		EmporiumState state= new EmporiumState("Davide");		
		
	}
	
	
	@Test
	public void testEqualsEmporium() {
		assertTrue((Emporium.createEmporium(Davide)).equals(empDavide) );
		
	}
	
	@Test
	public void testNotEqualsEmporium() {
		assertFalse((Emporium.createEmporium(Luca)).equals(empDavide) );
		
	}


}
