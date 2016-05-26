package it.polimi.ingsw.cg26.server.model.board;

import static org.junit.Assert.*;

import java.util.LinkedList;

import it.polimi.ingsw.cg26.common.dto.EmporiumDTO;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class EmporiumTest {
	
	private Player Davide; 
	private Player Luca;
	private Emporium empDavide;
	private Player Gianni; 
	private EmporiumDTO state;
	
	 @Before
	    public void setUp() throws Exception {
		 
		 Davide=new Player(1234, "Davide", NobilityCell.createNobilityCell(10, null, new LinkedList<Bonus>()), 10, new LinkedList<>(), new LinkedList<>());
		 Luca=new Player(1235, "Luca", NobilityCell.createNobilityCell(11, null, new LinkedList<Bonus>()), 11, new LinkedList<>(), new LinkedList<>());
		 empDavide= Emporium.createEmporium(Davide);
		 Gianni=new Player(1234, "Gianni", NobilityCell.createNobilityCell(10, null, new LinkedList<Bonus>()), 10, new LinkedList<>(), new LinkedList<>());
		 state= new EmporiumDTO("Gianni");
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
		EmporiumDTO state= new EmporiumDTO("Davide");		
		
	}
	
	
	@Test
	public void testEqualsEmporium() {
		assertTrue((Emporium.createEmporium(Davide)).equals(empDavide) );
		
	}
	
	@Test
	public void testNotEqualsEmporium() {
		assertFalse((Emporium.createEmporium(Luca)).equals(empDavide) );
		//assertEquals(new EmporiumDTO(Gianni.getKind()), dto);
		
	}


}
