package it.polimi.ingsw.cg26.server.model.board;

import static org.junit.Assert.*;

import java.util.LinkedList;

import it.polimi.ingsw.cg26.common.dto.EmporiumDTO;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class EmporiumTest {
	
	private Player Davide; 
	private Player Luca;
	private Emporium empDavide;
	private Player Gianni; 
	private EmporiumDTO state;
	
	 @Before
	    public void setUp() throws Exception {
		 
		 Davide=new Player(1234, "Davide", NobilityCell.createNobilityCell(10, null, new EmptyBonus()), 10, new LinkedList<>(), new LinkedList<>());
		 Luca=new Player(1235, "Luca", NobilityCell.createNobilityCell(11, null, new EmptyBonus()), 11, new LinkedList<>(), new LinkedList<>());
		 empDavide= Emporium.createEmporium(Davide);
		 Gianni=new Player(1234, "Gianni", NobilityCell.createNobilityCell(10, null, new EmptyBonus()), 10, new LinkedList<>(), new LinkedList<>());
		 state= new EmporiumDTO("Gianni");
	 }
	
	 
	@Test
		public void testEqualsEmporium() {
		
			Emporium emp1=Emporium.createEmporium(Davide);
			Emporium emp2=null;
			CityColor blu= CityColor.createCityColor("blu");
		
			assertTrue(emp1.equals(empDavide) );
			assertTrue(emp1.equals(emp1) );
			assertFalse(emp1.equals(emp2));
			assertFalse(emp1.equals(blu));
			
		}
	 
	 
	@Test(expected=NullPointerException.class)
	public void testshouldNotCreateEmporium() {
		Emporium.createEmporium(null);
		assertNotNull(Emporium.createEmporium(Davide));
	}
	
	@Test
	public void testshouldCreateEmporium() {
		
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
		
		assertEquals(state, empDavide.getState());
		
		
	}
	
	
	
	
	@Test
	public void testNotEqualsEmporium() {
		assertFalse((Emporium.createEmporium(Luca)).equals(empDavide) );
		
		
	}
	
	@Test
	public void testHashCode() {
		
		Emporium empLuca= Emporium.createEmporium(Luca);
		assertEquals(empLuca.hashCode(), empLuca.hashCode());
		assertNotEquals(empLuca.hashCode(), empDavide.hashCode());
		
		
	}


}
