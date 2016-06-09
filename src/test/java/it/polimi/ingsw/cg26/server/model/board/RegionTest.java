package it.polimi.ingsw.cg26.server.model.board;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.CityColorDTO;
import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.dto.EmporiumDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.EmptyBonusDTO;
import it.polimi.ingsw.cg26.server.model.bonus.*;
import it.polimi.ingsw.cg26.server.model.cards.*;
import it.polimi.ingsw.cg26.server.model.player.Player;


public class RegionTest {
	
	private City foggia;
	private City bari;
	private City brindisi;
	private City taranto;
	private City lecce;
	private Collection<City> cities;
	private Balcony balcony;
	
	private Bonus bonuses1;
	private Bonus bonuses2;
	private Bonus bonuses3;
	private Bonus bonuses4;
	private Bonus bonuses5;
	private Bonus bonusesRegion;
	
	private Collection<BusinessPermissionTile> cards;
	private BusinessPermissionTileDeck deck;
	private Player Luca;
		
	@Before
	public void setUp() throws Exception {
		
		 bonuses1=new EmptyBonus();
		 bonuses2=new EmptyBonus();
		 bonuses3=new EmptyBonus();
		 bonuses4=new EmptyBonus();
		 bonuses5=new EmptyBonus();
		 bonusesRegion=new VictoryBonus(new EmptyBonus(), 5);
		
		cards= new LinkedList<BusinessPermissionTile>();
		deck= new BusinessPermissionTileDeck(cards);
		
		balcony= Balcony.createBalcony(4);
		
		foggia = City.createCity("Foggia", CityColor.createCityColor("nero"), bonuses1);
		bari = City.createCity("Bari", CityColor.createCityColor("bianco"), bonuses2);
		brindisi = City.createCity("Brindisi", CityColor.createCityColor("blu"), bonuses3);
		taranto = City.createCity("Taranto", CityColor.createCityColor("rosso"), bonuses4);
		lecce = City.createCity("Lecce", CityColor.createCityColor("giallo"), bonuses5);
		
		cities= new LinkedList<>();
		cities.add(foggia);
		cities.add(bari);
		cities.add(brindisi);
		cities.add(taranto);
		cities.add(lecce);
		
		Luca=new Player(1235, "Luca", NobilityCell.createNobilityCell(11, null, new EmptyBonus()), 10, new LinkedList<>(), new LinkedList<>());
		
		
	}

	

	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateRegionIfNameIsNull() {
		
		Region.createRegion(null, cities, deck, balcony, bonusesRegion);
		
	}
	

	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateRegionIfTheListOfCitiesIsNull() {
		
		Region.createRegion("Puglia", null, deck, balcony, bonusesRegion);
		
	}
	
	

	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateRegionIfBPTDeckIsNull() {
		
		Region.createRegion("Puglia", cities, null, balcony, bonusesRegion);
	}
	
	

	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateRegionIfBalconyIsNull() {
		
		Region.createRegion("Puglia", cities, deck, null, bonusesRegion);
	}
	
	

	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateRegionIfRewardBonusRegionIsNull() {
		
		Region.createRegion("Puglia", cities, deck, balcony, null);
		
	}
	

	@Test
	public void testShouldCreateRegion() {
		
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, bonusesRegion);
		
		assertNotNull(puglia);
		
		
	}
	


	@Test
	public void testCheckRegionBonusesIsFalseBecauseThereAreNotEmporiumInCities() {
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, bonusesRegion);
		
		assertFalse(puglia.checkRegionBonuses(Luca));
		
		
		
	}


	
	@Test
	public void testCheckRegionBonusesIsFalseBecauseThereAreNoEnoughEmporiumsInCities() throws Exception {
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, bonusesRegion);
		foggia.build(Luca);
		lecce.build(Luca);
		
		assertFalse(puglia.checkRegionBonuses(Luca));
		
	}


	@Test
	public void testCheckRegionBonusesIsTrueBecauseThereAreAllTheEmporiums() throws Exception {
		
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, bonusesRegion);
		
		foggia.build(Luca);
		lecce.build(Luca);
		bari.build(Luca);
		brindisi.build(Luca);
		taranto.build(Luca);
		
		assertTrue(puglia.checkRegionBonuses(Luca));

		
		
		
	}


	

	@Test
	public void testGetRegionBonus() { 
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, bonusesRegion);
		
		assertEquals(puglia.getRegionBonus(), bonusesRegion);
		
	}
		
	
	

	@Test
	public void testGetCities() {
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, bonusesRegion);
		
		assertEquals(puglia.getCities(), cities);
		
	}
	
	

	@Test
	public void testGetCity() {
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, bonusesRegion);

		Collection<EmporiumDTO> emporiums= new ArrayList<>();
		Collection<String> strings= new LinkedList<>();
		BonusDTO bonuses= new EmptyBonusDTO();

		CityDTO requiredCity= new CityDTO("Lecce", new CityColorDTO("marrone"), bonuses, emporiums, strings);

		assertEquals(puglia.getCity(requiredCity), lecce);

	}

	@Test
	public void testGetBalcony() {
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, bonusesRegion);

		assertEquals(puglia.getBalcony(), balcony);



	}

	@Test
	public void testGetBPTDeck() {
		
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, bonusesRegion);

		assertEquals(puglia.getBPTDeck(), deck);

	}

	@Test
	public void testGetName() {
		
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, bonusesRegion);

		assertEquals(puglia.getName(), "Puglia");

	}


	
	@Test
	public void testHashCode() {
		
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, bonusesRegion);



		assertNotEquals(puglia.hashCode(),0);


	}




	@Test
	public void testToString() {
		
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, bonusesRegion);

		assertEquals(puglia.toString(), "Region{" +
				"name='" + "Puglia" + '\'' +
				", cities=" + cities +
				", balcony=" + balcony +
				", deck=" + deck +
				", bonus=" + bonusesRegion +
				'}');

	}




	@Test
	public void testGetState() {
		
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, bonusesRegion);

		RegionDTO pugliaDTO= puglia.getState();
		LinkedList<CityDTO> citiesState = new LinkedList<>();
		for (City c: cities)
			citiesState.add(c.getState());

		RegionDTO regionTest = new RegionDTO("Puglia", citiesState, deck.getState(), balcony.getState(), bonusesRegion.getState());
		System.out.println(pugliaDTO);
		System.out.println(regionTest);
		assertEquals(pugliaDTO, regionTest);

	}




	@Test
	public void testEqualsObject() {

		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, bonusesRegion);
		Region puglia2=Region.createRegion("Puglia", cities, deck, balcony, bonusesRegion);
		Region Molise=null;

		assertEquals(puglia, puglia2);
		assertFalse(puglia.equals(Molise));



	}

}
