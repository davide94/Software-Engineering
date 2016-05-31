package it.polimi.ingsw.cg26.server.model.board;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.CityColorDTO;
import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.server.exceptions.ExistingEmporiumException;
import it.polimi.ingsw.cg26.server.model.bonus.AssistantBonus;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.bonus.CardBonus;
import it.polimi.ingsw.cg26.server.model.bonus.CoinBonus;
import it.polimi.ingsw.cg26.server.model.bonus.MainActionBonus;
import it.polimi.ingsw.cg26.server.model.bonus.NobilityBonus;
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
	private List<Emporium> emporiums3;
	private Player Davide;
	private Player Luca;
	private Player Marco;
	private Emporium empDavide;
	private Emporium empLuca;
	private Emporium empMarco;
	private List<City> linkedCities;
	private NobilityCell next;
	private NobilityCell next2;
	
	
	
	 @Before
	    public void setUp() throws Exception {
		 
		    next2= NobilityCell.createNobilityCell(12, null, new RewardTile(new LinkedList<Bonus>()));		 
		    next= NobilityCell.createNobilityCell(11, next2, new RewardTile(new LinkedList<Bonus>()));
		    Davide=new Player(1234, "Davide", NobilityCell.createNobilityCell(10, next, new RewardTile(new LinkedList<Bonus>())), 5, new LinkedList<>(), new LinkedList<>());
		    Luca=new Player(1235, "Luca", NobilityCell.createNobilityCell(11, null, new RewardTile(new LinkedList<Bonus>())), 11, new LinkedList<>(), new LinkedList<>());
		    Marco=new Player(1236, "Marco", NobilityCell.createNobilityCell(12, null, new RewardTile(new LinkedList<Bonus>())), 12, new LinkedList<>(), new LinkedList<>());
		    emporiums1 = new ArrayList<>();
		    emporiums2 = new ArrayList<>();
		    emporiums3 = new ArrayList<>();
		    empDavide=Emporium.createEmporium(Davide);
		    empLuca=Emporium.createEmporium(Luca);
		    empMarco=Emporium.createEmporium(Marco);
		    emporiums2.add(empDavide);
		    emporiums3.add(empDavide);
		    emporiums3.add(empLuca);
		    emporiums3.add(empMarco);		
		    bonuses1 = new LinkedList<>();
		    bonuses2 = new LinkedList<>();
		    bonuses3 = new LinkedList<>();
		    bonuses3.add(new VictoryBonus(4));
		    bonuses3.add(new MainActionBonus(2));
	        color1= CityColor.createCityColor("blu");
	        color2= CityColor.createCityColor("verde");
	        city1 = City.createCity("Milano", CityColor.createCityColor("blu"), new RewardTile(bonuses1));
	        city2= City.createCity("Roma", color2, new RewardTile(bonuses1));
	        city3= City.createCity("Firenze", color1, new RewardTile(bonuses2));
	        linkedCities=new LinkedList<>();
	        linkedCities.add(city2);
	        
	    }
	 
	 
	 
	 public void testTakeBonusesOfCityC() {
		 List<Bonus> bonusesA=new LinkedList<>();
		 bonusesA.add(new CoinBonus(5));
		 bonusesA.add(new NobilityBonus(1));		 
		 City cityA= City.createCity("Napoli", color1, new RewardTile(bonusesA));
		
		 
		 List<Bonus> bonusesB=new LinkedList<>();
		 bonusesB.add(new AssistantBonus(3));
		 bonusesB.add(new MainActionBonus(2));
		 City cityB= City.createCity("Roma", color2, new RewardTile(bonusesB));
		 
		 List<Bonus> bonusesC=new LinkedList<>();
		 bonusesC.add(new VictoryBonus(10));
		
		 City cityC= City.createCity("Firenze", color1, new RewardTile(bonusesC));
		 
		 cityA.link(cityB);
		 cityA.link(cityC);
		 
		 
		 
		 cityC.build(Davide);
		 
		 
		 
		 
		 assertEquals(Davide.getVictoryPoints(), 10);   
		 	 
		 
		 
	 }
	 
	 
	 public void testTakeBonusesOfCityBandCityCLinkedWithB() {
		 List<Bonus> bonusesA=new LinkedList<>();
		 bonusesA.add(new CoinBonus(5));
		 bonusesA.add(new NobilityBonus(1));		 
		 City cityA= City.createCity("Napoli", color1, new RewardTile(bonusesA));
		
		 
		 List<Bonus> bonusesB=new LinkedList<>();
		 bonusesB.add(new AssistantBonus(3));
		 bonusesB.add(new MainActionBonus(2));
		 City cityB= City.createCity("Roma", color2, new RewardTile(bonusesB));
		 
		 List<Bonus> bonusesC=new LinkedList<>();
		 bonusesC.add(new VictoryBonus(10));
		 
		 City cityC= City.createCity("Firenze", color1, new RewardTile(bonusesC));
		 
		 
		 cityB.link(cityC);
		 cityA.link(cityB);
		 
		 
		 
		 cityC.build(Davide);
		 cityB.build(Davide);
		 
		 
		 
		 
		 assertEquals(Davide.getVictoryPoints(), 20);   
		 assertEquals(Davide.getAssistantsNumber(), 3); 
		 assertTrue(Davide.canPerformMainAction());
		 	 
		 
		 
	 }
	 
	 @Test
		public void testTakeBonusesOfAllCitiesLinkedToCityA() {
		 List<Bonus> bonusesA=new LinkedList<>();
		 bonusesA.add(new CoinBonus(5));
		 bonusesA.add(new NobilityBonus(1));		 
		 City cityA= City.createCity("Napoli", color1, new RewardTile(bonusesA));
		
		 
		 List<Bonus> bonusesB=new LinkedList<>();
		 bonusesB.add(new AssistantBonus(3));
		 bonusesB.add(new MainActionBonus(1));
		 City cityB= City.createCity("Roma", color2, new RewardTile(bonusesB));
		 
		 List<Bonus> bonusesC=new LinkedList<>();
		 bonusesC.add(new VictoryBonus(10));
		
		 City cityC= City.createCity("Firenze", color1, new RewardTile(bonusesC));
		 
		 cityB.link(cityC);
		 cityA.link(cityB);
		 
		 
		 
		 cityC.build(Davide);
		 cityB.build(Davide);
		 cityA.build(Davide);
		 
		 
		 assertEquals(Davide.getAssistantsNumber(), 6); 
		 assertEquals(Davide.getCoinsNumber(), 10);
		 assertEquals(Davide.getVictoryPoints(), 30);   
		 assertEquals(Davide.getNobilityCell(), next);
		 assertTrue(Davide.canPerformMainAction());
		 
		 
		 
	 }
			
	 
	 
	 @Test
		public void testCity1DistanceFromCity3() {
		 city2.link(city3);
		 city1.link(city2);
		 
		 assertEquals(city1.distanceFrom(city3),2);
		 
		 
		 
		}
	 
	 @Test
		public void testGetDistanceOfCityFromCity2AndThereIsNotALink() {
		 
		 int maxDistance= 2147483647;
		 assertEquals(city1.distanceFrom(city2), maxDistance );
		 
		
		 
		}
	 
	 @Test
		public void testGetDistance1() {
		 
		 city1.link(city2);
		 city2.link(city1);
		 
		 int maxDistance= 2147483647;
		 assertEquals(city1.distanceFrom(city2), 1 );
		 assertEquals(city3.distanceFrom(city2), maxDistance );
		 
		
		 
		}
	 
	 
	 @Test
		public void testCity1DistanceFromCity4() {
		 
		 City city4= City.createCity("Napoli", CityColor.createCityColor("Viola"), new RewardTile(bonuses2));
		 city3.link(city4);
		 city2.link(city3);
		 city1.link(city2);
		 
		 assertEquals(city1.distanceFrom(city4),3);
		 
		
		 
		}
	 
	 
	 
	 
	 @Test
		public void testGetDavideEmporiumsofCity1() {
			city1.build(Davide);
			assertEquals(city1.getEmporiums(),emporiums2);
			
		}
	 
	 
	 @Test
		public void testGetAllEmporiumsofCity1() {
			city1.build(Davide);
			city1.build(Luca);
			city1.build(Marco);
			
			assertEquals(city1.getEmporiums(),emporiums3);
			
		}
		
	 
	 
	 
		
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
	 
	 
    @Test (expected=NullPointerException.class)
		public void testShouldNotLinkCity1withNullCity() {
	 
	 	 city1.link(null);
		
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
		public void testNotEqualsNameofCities() {
		City city4= City.createCity("Genova", color2, new RewardTile(bonuses1));
		assertNotEquals(city4, city2);
		
			
		}
	 
	 @Test
		public void testNotEqualsColorofCities() {
		City city4= City.createCity("Roma", color1, new RewardTile(bonuses1));
		assertNotEquals(city4, city2);
		
			
		}
	 
	 
	 
	 
	 @Test
		public void testGetAssistantBonuses() {
			Bonus bonus = new AssistantBonus(4);
	        bonuses2.add(bonus);
	        City city4= City.createCity("Genova", color1, new RewardTile(bonuses2));
	        assertEquals(city4.getReward().getBonuses(), bonuses2);
	        
	        
			
		}
	 
	 
	 @Test
		public void testGetVictoryBonuses() {
			Bonus bonus = new VictoryBonus(4);
	        bonuses2.add(bonus);
	        City city4= City.createCity("Genova", color1, new RewardTile(bonuses2));
	        assertEquals(city4.getReward().getBonuses(), bonuses2);
	        
	        
			
		}
	 
	 
	 
	 @Test
		public void testGetCollectionOfBonuses() {
		        
			Bonus bonus1 = new VictoryBonus(4);
			Bonus bonus2 = new MainActionBonus(2);
	        bonuses2.add(bonus1);
	        bonuses2.add(bonus2);
	        
	        City city4= City.createCity("Genova", color1, new RewardTile(bonuses2));
	        assertEquals(city4.getReward().getBonuses(), bonuses3);
	        
	        
			
		}
		
	 
	 


	
	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateCity() {
	         City.createCity(null,null,null);
			
			}
	
	
	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateCityWithoutaValidName() {
			City.createCity(null,color1, new RewardTile(bonuses1));
			
			}
	
	
	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateCityWithoutaColor() {
			City.createCity("Firenze",null, new RewardTile(bonuses1));
			
			}
	
	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateCityWithNullBonus() {
			City.createCity("Firenze",color2,null);
			
			}
	
	
	@Test 
	public void testShouldCreateCity() {
		
		assertNotNull(City.createCity("Firenze",color1, new RewardTile(bonuses1)));
		assertEquals(City.createCity("Firenze",color1, new RewardTile(bonuses1)), city3);
		
		
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
		System.out.println(city1);
		assertEquals(city1.toString(), "City{" +
				"distance=" + "Infinity" +
				", name='" + "Milano'" +
				", color=" + "CityColor='blu'" +
				", emporiums=" + "[]" +
				", reward=" + "RewardTile{bonuses=[]}" +
				//", nearCities=" + "[]" +
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
		
		
		City city4= City.createCity("Napoli", color1, new RewardTile(bonuses3));
		city4.build(Davide);
		city4.link(city1);
		CityDTO cityDTO= city4.getState();
		
		assertEquals(city4.getName(), cityDTO.getName());
		assertEquals(city4.getColor().getState(), cityDTO.getColor());
	
		
				
	}
	
	
	@Test
	public void testHashCode() {
		assertEquals(city1.hashCode(), city1.hashCode());
		assertNotEquals(city1.hashCode(), city2.hashCode());
		
		
	}
	
	
	@Test
	public void testEqualsObject() {
	City city4= City.createCity("Roma", color2, new RewardTile(bonuses1));
	City city5=null;
	CityColor blu= CityColor.createCityColor("blu");
	
	assertEquals(city4, city2);
	assertFalse(city4.equals(city5));
	assertFalse((city4.getClass()).equals(blu.getClass()));
	assertFalse(city4.equals(blu));
	
	
		
	}
	
	


}
