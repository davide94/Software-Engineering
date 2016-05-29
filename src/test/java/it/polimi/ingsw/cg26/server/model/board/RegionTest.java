package it.polimi.ingsw.cg26.server.model.board;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.server.model.bonus.*;
import it.polimi.ingsw.cg26.server.model.cards.*;
import it.polimi.ingsw.cg26.server.model.player.Player;


public class RegionTest {
	
	private City Foggia;
	private City Bari;
	private City Brindisi; 
	private City Taranto;
	private City Lecce;
	private Collection<City> cities;
	private Balcony balcony;
	private RewardTile premio;
	
	private List<Bonus> bonuses1;
	private List<Bonus> bonuses2;
	private List<Bonus> bonuses3;
	private List<Bonus> bonuses4;
	private List<Bonus> bonuses5;
	private List<Bonus> bonusesRegion;
	
	private RewardTile tile1;
	private RewardTile tile2;
	private RewardTile tile3;
	private RewardTile tile4;
	private RewardTile tile5;
	
	private Collection<BusinessPermissionTile> cards;
	private BusinessPermissionTileDeck deck;
	private Player Luca;
		
	@Before
	public void setUp() throws Exception {
		
		 bonuses1=new LinkedList<>();
		 bonuses2=new LinkedList<>();
		 bonuses3=new LinkedList<>();
		 bonuses4=new LinkedList<>();
		 bonuses5=new LinkedList<>();
		 bonusesRegion=new LinkedList<>();
		 bonusesRegion.add(new VictoryBonus(5));
		 
		 
		 tile1=new RewardTile(bonuses1);
		 tile2=new RewardTile(bonuses2);
		 tile3=new RewardTile(bonuses3);
		 tile4=new RewardTile(bonuses4);
		 tile5=new RewardTile(bonuses5);
		 
		
		
		cards= new LinkedList<BusinessPermissionTile>();
		deck= new BusinessPermissionTileDeck(cards);
		
		balcony= Balcony.createBalcony(4);
		premio= new RewardTile(bonusesRegion);
		
		Foggia = City.createCity("Foggia", CityColor.createCityColor("nero"), tile1);
		Bari = City.createCity("Bari", CityColor.createCityColor("bianco"), tile2);
		Brindisi = City.createCity("Brindisi", CityColor.createCityColor("blu"), tile3);
		Taranto = City.createCity("Taranto", CityColor.createCityColor("rosso"), tile4);
		Lecce = City.createCity("Lecce", CityColor.createCityColor("giallo"), tile5);
		
		cities= new LinkedList<>();
		cities.add(Foggia);
		cities.add(Bari);
		cities.add(Brindisi);
		cities.add(Taranto);
		cities.add(Lecce);
		
		Luca=new Player(1235, "Luca", NobilityCell.createNobilityCell(11, null, new RewardTile(new LinkedList<Bonus>())), 10, new LinkedList<>(), new LinkedList<>());
		
		
	}

	

	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateRegionIfNameIsNull() {
		
		Region.createRegion(null, cities, deck, balcony, premio);
		
	}
	

	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateRegionIfTheListOfCitiesIsNull() {
		
		Region.createRegion("Puglia", null, deck, balcony, premio);
		
	}
	
	

	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateRegionIfBPTDeckIsNull() {
		
		Region.createRegion("Puglia", cities, null, balcony, premio);
	}
	
	

	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateRegionIfBalconyIsNull() {
		
		Region.createRegion("Puglia", cities, deck, null, premio);
	}
	
	

	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateRegionIfRewardBonusRegionIsNull() {
		
		Region.createRegion("Puglia", cities, deck, balcony, null);
		
	}
	

		//DA RIVEDERE ACCADONO COSE STRANE
	public void testShouldCreateRegion() {
		
		Region Puglia=Region.createRegion("Puglia", cities, deck, balcony, premio);
		
		assertNotNull(Puglia);
		assertEquals(Puglia.getBalcony(), balcony);
		assertEquals(Puglia.getBPTDeck(), deck);
		assertEquals(Puglia.getCities(), cities);
		assertEquals(Puglia.getName(), "Puglia");
		
		
	}
	
	/*

	@Test
	public void testCheckRegionBonusesIsFalseBecauseThereAreNotEmporiumInCities() {
		Region Puglia=Region.createRegion("Puglia", cities, deck, balcony, premio);
		
		assertFalse(Puglia.checkRegionBonuses(Luca));
		
		
		
	}
	
	
	@Test
	public void testCheckRegionBonusesIsFalseBecauseThereAreNoEnoughEmporiumsInCities() {
		Region Puglia=Region.createRegion("Puglia", cities, deck, balcony, premio);
		Foggia.build(Luca);
		Lecce.build(Luca);
		
		assertFalse(Puglia.checkRegionBonuses(Luca));
		
	}
	
	@Test
	public void testCheckRegionBonusesIsTrueBecauseThereAreAllTheEmporiums() {
		
		Region Puglia=Region.createRegion("Puglia", cities, deck, balcony, premio);
		
		Foggia.build(Luca);
		Lecce.build(Luca);
		Bari.build(Luca);
		Brindisi.build(Luca);
		Taranto.build(Luca);
		
		assertTrue(Puglia.checkRegionBonuses(Luca));
		
		
		
	}
	
	

	@Test
	public void testGetRegionBonus() { 
		Region Puglia=Region.createRegion("Puglia", cities, deck, balcony, premio);
		
		assertEquals(Puglia.getRegionBonus(), premio);
		
	}*/
		
	
	

	@Test
	public void testGetCities() {
		Region Puglia=Region.createRegion("Puglia", cities, deck, balcony, premio);
		
		assertEquals(Puglia.getCities(), cities);
		
	}
	
	

	@Test
	public void testGetCity() {
		
	}

	@Test
	public void testGetBalcony() {
		
	}

	@Test
	public void testGetBPTDeck() {
		
	}

	@Test
	public void testGetName() {
		
	}

	@Test
	public void testEqualsObject() {
		
	}
	
	@Test
	public void testHashCode() {
		
	}

	@Test
	public void testToString() {
		
	}
	
	@Test
	public void testGetState() {
		
	}

}
