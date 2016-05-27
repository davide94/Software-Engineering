package it.polimi.ingsw.cg26.server.model.board;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.KingDTO;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.bonus.CoinBonus;
import it.polimi.ingsw.cg26.server.model.bonus.VictoryBonus;

public class KingTest {
	
	private City city1;
	private City city2;
	private List<Bonus> bonuses1;
	private List<Bonus> bonuses2;
	

	
	 @Before
	    public void setUp() throws Exception {
		 
		 bonuses1 = new LinkedList<>();
		 bonuses2 = new LinkedList<>();
		 bonuses1.add(new VictoryBonus(4));
		 bonuses2.add(new CoinBonus(5));
		 city1 = City.createCity("Milano", CityColor.createCityColor("blu"),bonuses1);
		 city2 = City.createCity("Torino", CityColor.createCityColor("rosa"),bonuses2);
		 
	 }
	
	
	
	
	
	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateKing() {
		King king= King.createKing(null);
		
	}
	
	
	@Test 
	public void testShouldCreateKing() {
		King king= King.createKing(city1);
		assertTrue((king.getCurrentCity()).equals(city1));
		
	}
	
	

	@Test
	public void testGetCurrentCity() {
		King king= King.createKing(city1);
		assertEquals(king.getCurrentCity(), city1);
		
		king.move(city2);
		assertNotEquals(king.getCurrentCity(), city1);
		assertEquals(king.getCurrentCity(), city2);
		
		
		
	}
	
	
	
	@Test (expected=NullPointerException.class)
	public void testShouldNotMoveTheKing() {
		
		King king= King.createKing(city1);
		king.move(null);
				
		
	}
	
	
	
		
	
	@Test 
	public void testShouldMoveTheKing() {
		
		King king= King.createKing(city1);
		king.move(city2);
		assertEquals(king.getCurrentCity(), city2);
		
		City city3 = City.createCity("Pisa", CityColor.createCityColor("verde"),bonuses1);
		king.move(city3);
		assertEquals(king.getCurrentCity(), city3);
		
			
	}
	
	
	@Test (expected=NullPointerException.class)
	public void testShouldNotSetANullCity() {
		
		King king= King.createKing(city1);
		king.setCurrentCity(null);
					
		
	}
	
	
	@Test 
	public void testShouldSetCurrentCity() {
		
		King king= King.createKing(city1);
		king.setCurrentCity(city2);
		assertTrue(king.getCurrentCity().equals(city2));
		
		City city3 = City.createCity("Pisa", CityColor.createCityColor("verde"),bonuses1);
		king.setCurrentCity(city3);
		assertTrue(king.getCurrentCity().equals(city3));
					
		
	}
	

	@Test
	public void testPriceToMoveKingFromCity1ToCity2ShouldBe2() {
		
		King king= King.createKing(city1);
		city1.link(city2);
		assertEquals(king.priceToMove(city2), 2);
			
		
	}
	
	//NON SI DOVREBBE MUOVERE IL RE IN UNA CITTA' ISOLATA (NON COLLEGATA A NULLA)
	@Test
	public void testPriceToMoveKingFromCity1ToDifferentCity() {
		
		King king= King.createKing(city1);
		City city3 = City.createCity("Pisa", CityColor.createCityColor("verde"),bonuses1);
		City city4 = City.createCity("Bari", CityColor.createCityColor("giallo"),bonuses2);

		city3.link(city4);
		city2.link(city4);
		city2.link(city3);
		city1.link(city2);
		
		
		assertEquals(king.priceToMove(city2), 2);
		assertEquals(king.priceToMove(city3), 4);
		assertEquals(king.priceToMove(city4), 4);
		assertNotEquals(king.priceToMove(city4), 6);
			
		
	}
	
	
	@Test
	public void testPriceToMoveKingFromCity1ToDifferentCityPartTwo() {
		
		King king= King.createKing(city2);
		City city3 = City.createCity("Pisa", CityColor.createCityColor("verde"),bonuses1);
		City city4 = City.createCity("Bari", CityColor.createCityColor("giallo"),bonuses2);
		
		city1.link(city4);
		city2.link(city1);
		city2.link(city4);
		city2.link(city3);
				
		
		assertEquals(king.priceToMove(city1), 2);
		assertEquals(king.priceToMove(city3), 2);
		assertEquals(king.priceToMove(city4), 2);
				
	}
	

	@Test
	public void testToString() {
		King king= King.createKing(city1);
		
		assertEquals(king.toString(), "King{" +
                "currentCity='" + "Milano" + "\'" +
                '}');
		
	}
	
	
	@Test
	public void testGetState() {
		King king= King.createKing(city1);
		//KingDTO kingDTO=king.getState();
		KingDTO kingDTO= new KingDTO("Milano");
		
		assertEquals(king.getState(), kingDTO);
		
	}
	

}
