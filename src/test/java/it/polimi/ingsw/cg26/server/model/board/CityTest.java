package it.polimi.ingsw.cg26.server.model.board;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.server.exceptions.ExistingEmporiumException;
import it.polimi.ingsw.cg26.server.model.bonus.AssistantBonus;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.bonus.MainActionBonus;
import it.polimi.ingsw.cg26.server.model.bonus.VictoryBonus;
import it.polimi.ingsw.cg26.server.model.player.Player;


public class CityTest {
	
	private City city1;
	private City city2;
	private City city3;
	private CityColor color1;
	private CityColor color2;
	private List<Bonus> bonuses1;
	private List<Bonus> bonuses2;
	private List<Bonus> bonuses3;
	private List<Emporium> emporiums1;
	private List<Emporium> emporiums2;
	private Player Davide;
	private Player Luca;
	private Player Marco;
	private Emporium empDavide;
	//private Emporium empLuca;
	//private Emporium empMarco;
	private List<City> linkedCities;
	
	
	
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
		    bonuses1 = new LinkedList<>();
		    bonuses2 = new LinkedList<>();
		    bonuses3 = new LinkedList<>();
		    bonuses3.add(new VictoryBonus(4));
		    bonuses3.add(new MainActionBonus(2));
	        color1= CityColor.createCityColor("blu");
	        color2= CityColor.createCityColor("verde");
	        city1 = City.createCity("Milano", CityColor.createCityColor("blu"),bonuses1 );
	        city2= City.createCity("Roma", color2,bonuses1 );
	        city3= City.createCity("Firenze", color1,bonuses2 );
	        linkedCities=new LinkedList<>();
	        linkedCities.add(city2);
	        
	    }
	 
	 
	 @Test
		public void testDistanceFrom() {
			
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
	 
	 
	 
		
	 @Test (expected=ExistingEmporiumException.class)
		public void testBuildFailed() {
		 city1.build(Davide);
		 city1.build(Luca);
		 city1.build(Davide);
			
		}
	 
	 

	 @Test
		public void testgetNearCities() {
		 city1.link(city2);
		 assertEquals(city1.getNearCities(),linkedCities);
			
		}

		

			 
	 @Test (expected=NullPointerException.class)
		public void testLinkIfCityIsNull() {
		 
		 City city4= City.createCity(null,null,null);
		 city1.link(city4);
			
		}
	 
	 
	 @Test 
		public void testLinkOneCitywithCity1() {
		 city1.link(city2);
		 assertEquals(city1.getNearCities(),linkedCities);
		 
		 
		 		
		}
	 
	 

	 @Test 
		public void testLinkTwoCitieswithCity1() {
		 linkedCities.add(city3);
		 city1.link(city2);
		 city1.link(city3);
		 assertEquals(city1.getNearCities(),linkedCities);
		 
		 
		 		
		}
	 
	 
	 @Test
		public void testEqualsObject() {
		City city4= City.createCity("Roma", color2, bonuses1);
		assertEquals(city4, city2);
		
			
		}
	 
	 @Test
		public void testNotEqualsNameofCities() {
		City city4= City.createCity("Genova", color2, bonuses1);
		assertNotEquals(city4, city2);
		
			
		}
	 
	 @Test
		public void testNotEqualsColorofCities() {
		City city4= City.createCity("Roma", color1, bonuses1);
		assertNotEquals(city4, city2);
		
			
		}
	 
	 /*
	 @Test
		public void testNotEqualsBonusesOfCities() {
		City city4= City.createCity("Roma", color2, bonuses3);
		assertNotEquals(city4, city2);
		
			
		}*/
	 
	 
	 @Test
		public void testGetAssistantBonuses() {
			Bonus bonus = new AssistantBonus(4);
	        bonuses2.add(bonus);
	        City city4= City.createCity("Genova", color1,bonuses2 );
	        assertEquals(city4.getBonuses(), bonuses2);
	        
	        
			
		}
	 
	 
	 @Test
		public void testGetVictoryBonuses() {
			Bonus bonus = new VictoryBonus(4);
	        bonuses2.add(bonus);
	        City city4= City.createCity("Genova", color1,bonuses2 );
	        assertEquals(city4.getBonuses(), bonuses2);
	        
	        
			
		}
	 
	 /*
	 @Test
		public void testGetCollectionOfBonuses() {
		        
			Bonus bonus1 = new VictoryBonus(4);
			Bonus bonus2 = new MainActionBonus(2);
	        bonuses2.add(bonus1);
	        bonuses2.add(bonus2);
	        
	        City city4= City.createCity("Genova", color1,bonuses2 );
	        assertEquals(city4.getBonuses(), bonuses3);
	        
	        
			
		}*/
	 
	 


	
	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateCity() {
	         City.createCity(null,null,null);
			
			}
	
	
	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateCityWithoutaValidName() {
			City.createCity(null,color1,bonuses1);
			
			}
	
	
	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateCityWithoutaColor() {
			City.createCity("Firenze",null,bonuses1);
			
			}
	
	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateCityWithNullBonus() {
			City.createCity("Firenze",color2,null);
			
			}
	
	
	@Test 
	public void testShouldCreateCity() {
		
		assertNotNull(City.createCity("Firenze",color1,bonuses1));
		assertEquals(City.createCity("Firenze",color1,bonuses1) , city3);
		
		
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
	public void testGetState() {
		
	}
	
	


}
