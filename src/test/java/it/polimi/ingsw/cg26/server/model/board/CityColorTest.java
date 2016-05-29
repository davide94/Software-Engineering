package it.polimi.ingsw.cg26.server.model.board;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.CityColorDTO;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;

public class CityColorTest {
	
	private CityColor cityBlu;
	private CityColor cityViola;
	
	
	
	@Before
    public void setUp() throws Exception {
		
		cityBlu= CityColor.createCityColor("blu");
		cityViola= CityColor.createCityColor("viola");
		
		
	}

	

	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateCityColor() {
		CityColor blu= CityColor.createCityColor(null);
	}
	
	
	@Test 
	public void testShouldCreateCityColorBlu() {
		CityColor blu= CityColor.createCityColor("blu");
		assertNotNull(blu);
		assertEquals(blu, cityBlu);
		
		CityColor arancione= CityColor.createCityColor("arancione");
		assertNotNull(arancione);
	}
	
	


	@Test
	public void testGetColor() {
		CityColor blu= CityColor.createCityColor("blu");
		CityColor arancione= CityColor.createCityColor("arancione");
		CityColor viola= CityColor.createCityColor("viola");
		CityColor rosa= CityColor.createCityColor("rosa");
		
		assertEquals(blu.getColor(), "blu");
		assertEquals(arancione.getColor(), "arancione");
		assertEquals(viola.getColor(), "viola");
		assertEquals(rosa.getColor(), "rosa");
			
	}

	
		

	@Test
	public void testToString() {
		CityColor viola= CityColor.createCityColor("viola");
		
		assertEquals(viola.toString(), "CityColor='" + "viola" + '\'');
		assertEquals(cityBlu.toString(), "CityColor='" + "blu" + '\'');
		
	}
	
	
	@Test
	public void testHashCode() {
		assertEquals(cityBlu.hashCode(), cityBlu.hashCode());
		assertNotEquals(cityBlu.hashCode(), cityViola.hashCode());
		
		
	}
	
	@Test
	public void testGetState() {
		CityColorDTO colorDTO= cityBlu.getState();
		
		assertEquals(cityBlu.getColor(), colorDTO.getColor());
				
		
	}
	

	@Test
	public void testEqualsCityColor() {
		CityColor blu= CityColor.createCityColor("blu");
		CityColor viola= CityColor.createCityColor("viola");
		CityColor verde=null;
		CityColor giallo2=null;
		List<Bonus> bonuses= new LinkedList<>();
		City rosa=City.createCity("Torino", CityColor.createCityColor("rosa"), new RewardTile(bonuses));
		
		
		
		assertTrue(blu.equals(blu));
		assertFalse(blu.equals(verde));
		assertFalse(blu.equals(rosa));
		assertTrue(blu.equals(cityBlu));
		assertFalse(blu.equals(cityViola));
		assertFalse(blu.equals(viola));
		
		
		
				
		
		
		
		
	}
	

}
