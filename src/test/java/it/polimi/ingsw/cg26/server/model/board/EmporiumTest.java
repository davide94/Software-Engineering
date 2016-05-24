package it.polimi.ingsw.cg26.server.model.board;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.state.EmporiumState;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class EmporiumTest {
	
	private Player Gianni; 
	private EmporiumState state;
	
	 @Before
	    public void setUp() throws Exception {
		 
		 Gianni=new Player(1234, "Gianni", NobilityCell.createNobilityCell(10, null, new LinkedList<Bonus>()), 10, new LinkedList<>(), new LinkedList<>());
		 state= new EmporiumState("Gianni");
	 }
	
	 
	@Test(expected=NullPointerException.class)
	public void testCreateEmporium() {
		assertNull(Emporium.createEmporium(null));
		assertNotNull(Emporium.createEmporium(Gianni));
	}

	

	@Test
	public void testGetPlayer() {
		assertEquals(Emporium.createEmporium(Gianni).getPlayer(), Gianni);
	}

	@Test
	public void testToString() {
		Emporium emporio1=Emporium.createEmporium(Gianni);
		assertEquals(emporio1.toString(), "Emporium{" +
				"player=" + "Gianni" +
				'}');
		
	}
	
	@Test
	public void testGetState() {
		//assertEquals(new EmporiumState(Gianni.getName()), state);		
		
	}

}
