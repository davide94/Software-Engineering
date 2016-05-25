package it.polimi.ingsw.cg26.server.model.board;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.player.Player;


public class CityTest {
	
	private City city1;
	private City city2;
	private CityColor color1;
	private CityColor color2;
	private List<Bonus> bonus;
	private List<Emporium> emporiums1;
	private List<Emporium> emporiums2;
	private Player Davide;
	private Player Luca;
	private Player Marco;
	private Emporium empDavide;
	//private Emporium empLuca;
	//private Emporium empMarco;
	
	
	 @Before
	    public void setUp() throws Exception {
		 
		 
		    Davide=new Player(1234, "Davide", NobilityCell.createNobilityCell(10, null, new LinkedList<Bonus>()), 10, new LinkedList<>(), new LinkedList<>());
		    Luca=new Player(1235, "Luca", NobilityCell.createNobilityCell(11, null, new LinkedList<Bonus>()), 11, new LinkedList<>(), new LinkedList<>());
		    Marco=new Player(1236, "Marco", NobilityCell.createNobilityCell(12, null, new LinkedList<Bonus>()), 12, new LinkedList<>(), new LinkedList<>());
		    emporiums1 = new ArrayList<>();
		    emporiums2 = new ArrayList<>();
		    empDavide=Emporium.createEmporium(Davide);
		    //empLuca=Emporium.createEmporium(Luca);
		    //empMarco=Emporium.createEmporium(Marco);
		    emporiums2.add(empDavide);
		    //emporiums2.add(empLuca);
		    //emporiums2.add(empMarco);		    
		    bonus = new LinkedList<>();
	        color1= CityColor.createCityColor("blu");
	        color2= CityColor.createCityColor("verde");
	        city1 = City.createCity("Milano", CityColor.createCityColor("blu"),bonus );
	        city2= City.createCity("Roma", color2,bonus );
	        
	    }

	
	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateCity() {
		assertNull(City.createCity(null,null,null));
			
			}
	
	
	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateCityWithoutaValidName() {
		assertNull(City.createCity(null,color1,bonus));
			
			}
	
	
	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateCityWithoutaColor() {
		assertNull(City.createCity("Firenze",null,bonus));
			
			}
	
	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateCityWithNullBonus() {
		assertNull(City.createCity("Firenze",color2,null));
			
			}
	
	
	@Test 
	public void testShouldCreateCity() {		
		assertNotNull(City.createCity("Firenze",color1,bonus));		
		
			}
	

	@Test
	public void testGetName() {
		
		assertEquals(city1.getName() , "Milano");
		assertEquals(city2.getName() , "Roma");
	}

	@Test
	public void testGetColor() {
		assertEquals(city1.getColor() , color1);
		assertEquals(city2.getColor() , color2);
	}
	
	
	@Test
	public void testToString() {
		assertEquals(city1.toString(), "City{" +
                "name=" + "Milano" + '\'' +
                ", color=" + "blu" +
                ", emporiums=" + "[]" +
                ", bonuses=" + "[]" +
                '}');		
	}
	
	
	@Test
	public void testGetEmporiums() {
		assertEquals(city1.getEmporiums(),emporiums1);
		
	}
	
	/*
	@Test
	public void testGetAllEmporiumsofCity1() {
		city1.build(Davide);
		//city1.build(Luca);
		//city1.build(Marco);
		
		assertEquals(city1.getEmporiums(),emporiums2);
		
	}
	*/
	
	
	
	
	@Test
	public void testIfCity2HasNoEmporium() {
		assertEquals(city1.getEmporiumsNumber(),0);
		
		
	}
	
	@Test
	public void testIfCity2Has1Emporium() {
		city2.build(Davide);
		assertEquals(city2.getEmporiumsNumber(),1);
		
	}
	
	@Test
	public void testIfCity2Has3Emporiums() {
		city2.build(Davide);
		city2.build(Luca);
		city2.build(Marco);
		
		assertEquals(city2.getEmporiumsNumber(),3);
		
	}

	
	
	@Test
	public void testCity1HasNotDavideEmporium() {
		
		assertFalse(city1.hasEmporium(Davide));
				
	}
	
	@Test
	public void testCity1HasLucaEmporium() {
		city1.build(Luca);
		assertTrue(city1.hasEmporium(Luca));
		
	}
	
	
	@Test
	public void testCity1HasLucaAndDavideEmporium() {
		city1.build(Luca);
		city1.build(Davide);
		assertTrue(city1.hasEmporium(Luca));
		assertTrue(city1.hasEmporium(Davide));
		
	}
	
	@Test
	public void testGetBonuses() {
		
	}

	

	@Test
	public void testLink() {
		
	}

	@Test
	public void testDistanceFrom() {
		
	}

	

	@Test
	public void testEqualsObject() {
		
	}
	
	
	@Test
	public void testBuild() {
		
	}

	
	
	@Test
	public void testGetState() {
		
	}
	
	


}
