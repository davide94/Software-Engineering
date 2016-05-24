package it.polimi.ingsw.cg26.server.model.board;

import static org.junit.Assert.*;


import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.server.model.bonus.Bonus;


public class CityTest {
	
	private City city1;
	private City city2;
	private CityColor color1;
	private CityColor color2;
	private List<Bonus> bonus;
	private List<Emporium> emporiums;
	
	
	 @Before
	    public void setUp() throws Exception {
	        
	        bonus = new LinkedList<>();
	        color1= CityColor.createCityColor("blu");
	        color2= CityColor.createCityColor("verde");
	        city1 = City.createCity("Milano", CityColor.createCityColor("blu"),bonus );
	        city2= City.createCity("Roma", color2,bonus );
	    }

	
	@Test (expected=NullPointerException.class)
	public void testCreateCity() {
		assertNull(City.createCity(null,null,null));
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
		
	}

	@Test
	public void testGetBonuses() {
		
	}

	@Test
	public void testGetEmporiumsNumber() {
		
	}

	@Test
	public void testHasEmporium() {
		
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
	public void testEqualsObject1() {
		
	}

	
	
	@Test
	public void testGetState() {
		
	}
	
	@Test
	public void testBuild() {
		
	}


}
